package physics;

import display.Canvas;
import graphics.Sprite;
import logic.Transform;
import logic.Vector2;

import java.awt.*;


public abstract class Collider {

    protected Transform transform;  // only the position of transform is important
    protected Vector2 offset;
    protected PhysicsObject physicsObject;
    protected boolean isStatic = false;
    protected double restitution = 0.2f;
    protected int layer = 1;

    protected Sprite debugSprite;

    public Collider(Transform transform, Vector2 offset, PhysicsObject physicsObject) {
        this.transform = transform;
        this.offset = offset;
        this.physicsObject = physicsObject;
    }

    public abstract void calcForce(PhysicsObject other);

    public Vector2 getPosition() {
        return this.transform.getPosition().add(offset);
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public void setLayer(int l) { this.layer = l; }

    public abstract void updateSprite(Canvas c);

    public void setRestitution(double restitution) {
        this.restitution = restitution;
    }

    public static boolean CircleVsRect(RectCollider a, CircleCollider b) { return false; }

    public static boolean CircleVsCircle(CircleCollider a, CircleCollider b) {
        double r = a.getRadius() + b.getRadius();
        double distance = a.getPosition().subtract(b.getPosition()).getLength();
        return distance < r;
    }

    public static boolean RectVsRect(RectCollider a, RectCollider b) {
        double ax = a.getPosition().getX(); double ay = a.getPosition().getY();
        double aWidth = a.getDimensions().getX(); double aHeight = a.getDimensions().getY();

        double bx = b.getPosition().getX(); double by = b.getPosition().getY();
        double bWidth = b.getDimensions().getX(); double bHeight = b.getDimensions().getY();

        // Exit with no intersection if found separated along an axis
        if (ax + aWidth < bx || ax > bx + bWidth) { return false; }
        if (ay + aHeight < by || ay > by + bHeight) { return false; }

        // No separating axis found, therefor there is at least one overlapping axis
        return true;
    }



    public static void resolveRectVsRect(RectCollider a, RectCollider b) {
        // Vector from A to B
        Vector2 n = b.getPosition().subtract(a.getPosition());

        // Calculate half extents along x axis for each object
        double a_extent = (a.getDimensions().getX()) / 2; // width / 2
        double b_extent = (b.getDimensions().getX()) / 2; // width / 2

        // Calculate overlap on x axis
        double x_overlap = a_extent + b_extent - Math.abs(n.getX());

        // SAT test on x axis
        if(x_overlap > 0)
        {
            // Calculate half extents along x axis for each object
            a_extent = (a.getDimensions().getY()) / 2; // height / 2
            b_extent = (b.getDimensions().getY()) / 2;

            // Calculate overlap on y axis
            double y_overlap = a_extent + b_extent - Math.abs( n.getY());

            Vector2 normal;
            double penetration;

            // SAT test on y axis
            if (y_overlap > 0)
            {
                // Find out which axis is axis of least penetration
                if (x_overlap > y_overlap)
                {
                    // Point towards B knowing that n points from A to B
                    if(n.getX() < 0)
                        normal = new Vector2( 0, 1 );
                    else
                        normal = new Vector2( 0, -1 );
                    penetration = y_overlap;
                    Collider.resolveCollision(a, b, penetration, normal);
                }
                else
                {
                    // Point toward B knowing that n points from A to B
                    if(n.getY() < 0)
                        normal = new Vector2( 1, 0 );
                    else
                        normal = new Vector2( -1, 0 );
                    penetration = x_overlap;
                    Collider.resolveCollision(a, b, penetration, normal);
                }
            }
        }
    }

    public static void resolveCircleVsCircle(CircleCollider a, CircleCollider b) {

        // Vector from a to b
        Vector2 n = b.transform.getPosition().subtract(a.transform.getPosition());

        if (!CircleVsCircle(a, b)) { return; }  // they do not collide
        // if they do collide:

        double d = n.getLength();

        if (d != 0) { // if distance is not 0

            double penetration = (a.getRadius() + b.getRadius()) - d;
            Vector2 normal = n.getNormalized();
            resolveCollision(a, b, penetration, normal);
        } else {
            double penetration = a.getRadius();
            Vector2 normal = new Vector2(0, 1);
            resolveCollision(a, b, penetration, normal);
        }


    }

    public static void resolveCollision(Collider a, Collider b, double penetration, Vector2 normal) {
        //relative Velocity:
        Vector2 rv = b.physicsObject.getVelocity().subtract(a.physicsObject.getVelocity());

        // calc relative Velocity along normal
        double velAlongNormal = Vector2.dotProduct(rv, normal);

        // no resolving if objects are separating
        if (velAlongNormal > 0) { return; }

        // calculate restitution
        double e = Math.min(a.restitution, b.restitution);

        //calc impulse scalar
        double j = -(1 + e) * velAlongNormal;
        j /= a.physicsObject.getMassInverse() + b.physicsObject.getMassInverse();

        //apply impulse
        Vector2 impulse = normal.scalarMult(j);
        a.physicsObject.addVelocity( impulse.scalarMult(a.physicsObject.getMassInverse() *-1) );
        b.physicsObject.addVelocity( impulse.scalarMult(b.physicsObject.getMassInverse()) );

        Collider.positionalCorrection(a, b, penetration, normal);
    }

    public static void positionalCorrection(Collider a, Collider b, double penetration, Vector2 normal) {
        double percentage = 0.8; // 0.2  - 0.8
        Vector2 correction = normal.scalarMult( penetration / (a.physicsObject.getMassInverse() + b.physicsObject.getMassInverse()) * percentage);
        a.transform.addPosition(correction.scalarMult(-a.physicsObject.getMassInverse()));
        b.transform.addPosition(correction.scalarMult(b.physicsObject.getMassInverse()));
    }

}
