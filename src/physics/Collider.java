package physics;

import gameobjects.ComponentType;
import gameobjects.GameComponent;
import gameobjects.GameObject;
import graphics.RenderManager;
import graphics.Sprite;
import logic.Transform;
import logic.Vector2;

import java.util.ArrayList;


public abstract class Collider extends GameComponent {

    protected Vector2 offset;
    //protected PhysicsObject physicsObject;
    protected boolean isStatic = false;
    protected boolean isTrigger = false;
    protected double restitution = 0.2f;
    protected ArrayList<CollissionListener> collisionListeners = new ArrayList<>();

    protected Sprite debugSprite;

    public Collider(GameObject reference, Vector2 offset) {
        super(reference, ComponentType.Collider);
        this.offset = offset;
    }

    public abstract void manageCollision(PhysicsGameComponent self, PhysicsGameComponent other);

    public abstract boolean doesCollide(Collider c);

    @Override
    public abstract void UpdateDrawables(RenderManager renderManager);

    public Vector2 getPosition() {
        return new Vector2(this.reference.getTransform().getPosition()).add(offset);
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public void setRestitution(double restitution) {
        this.restitution = restitution;
    }

    public GameObject getGameObject() {
        return reference;
    }

    public void addListener(CollissionListener c) {
        this.collisionListeners.add(c);
    }

    public void removeListener(CollissionListener c) {
        this.collisionListeners.remove(c);
    }

    public void setTrigger(boolean trigger) {
        isTrigger = trigger;
    }

    protected boolean CircleVsCircle(CircleCollider a, CircleCollider b) {
        double r = a.getRadius() + b.getRadius();
        double distance = a.getPosition().subtract(b.getPosition()).getLength();
        return distance < r;
    }

    protected boolean CircleVsRect(CircleCollider c, RectCollider r) {
        double c_x = c.getPosition().getX();
        double c_y = c.getPosition().getY();
        Vector2 dim = new Vector2(r.getDimensions().getWidth(), r.getDimensions().getHeight());
        double r_x = r.getPosition().getX();
        double r_y = r.getPosition().getY();

        double cx = Math.abs(c_x - r_x - dim.getX() * 0.5);
        double xDist = dim.getX() * 0.5 + c.getRadius();
        if (cx > xDist)
            return false;
        double cy = Math.abs(c_y - r_y - dim.getY() * 0.5);
        double yDist = dim.getY() * 0.5 + c.getRadius();
        if (cy > yDist)
            return false;
        if (cx <= dim.getX() * 0.5 || cy <= dim.getY() * 0.5)
            return true;
        double xCornerDist = cx - dim.getX() * 0.5;
        double yCornerDist = cy - dim.getY() * 0.5;
        double xCornerDistSq = xCornerDist * xCornerDist;
        double yCornerDistSq = yCornerDist * yCornerDist;
        double maxCornerDistSq = c.getRadius() * c.getRadius();
        return xCornerDistSq + yCornerDistSq <= maxCornerDistSq;
    }

    protected boolean RectVsRect(RectCollider a, RectCollider b) {
        return !(a.getX() + a.getWidth() < b.getX() || a.getY() + a.getHeight() < b.getY() ||
                a.getX() > b.getX() + b.getWidth() || a.getY() > b.getY() + b.getHeight());
    }

    protected void resolveCircleVsRect(RectCollider a, CircleCollider b, PhysicsGameComponent pa, PhysicsGameComponent pb) {
        Vector2 normal;
        double penetration;

        // get all corners from the rect
        Vector2 TopLeft = a.getPosition();
        Vector2 BottomLeft = a.getPosition().add(new Vector2(0, a.getDimensions().getY()));
        Vector2 BottomRight = a.getPosition().add(new Vector2(a.getDimensions().getX(), a.getDimensions().getY()));
        Vector2 TopRight = a.getPosition().add(new Vector2(a.getDimensions().getX(), 0));

        ArrayList<Vector2> corners = new ArrayList<>();
        corners.add(TopLeft);
        corners.add(BottomLeft);
        corners.add(BottomRight);
        corners.add(TopRight);

        // Get closest corner
        // only compare squared distances because it needs less resources
        Vector2 closest = Vector2.zero;
        double closestDistance = Double.POSITIVE_INFINITY;

        for (Vector2 corner : corners) {
            double d = b.getPosition().subtract(corner).getLengthSquared();
            if (d < closestDistance) {
                closest = corner;
                closestDistance = d;
            }
        }

        // now Check if circle overlaps with closest corner

        closestDistance = Math.sqrt(closestDistance);  // real distance

        if (closestDistance < b.getRadius()) { // if circle overlaps with corner

            normal = closest.subtract(b.getPosition()).getNormalized();
            //normal = normal.scalarMult(-1);
            penetration = b.getRadius() - closestDistance;

            // check if circle lies inside rect then normal has to be inverted
            Vector2 posC = b.getPosition(); // pos of circle

            if ((posC.getX() > TopLeft.getX() && posC.getX() < TopRight.getX()) && (posC.getY() > TopLeft.getY() && posC.getY() < BottomLeft.getY())) {
                // circle lies inside rect:
                normal = normal.scalarMult(-1);
            }


        } else {
            // TODO: Circle does not overlap with a corner so check if circle is overlapping with rect
            if (!CircleVsRect(b, a)) {
                return;
            }

            // get x, y distance
            double x_distance = closest.getX() - b.getPosition().getX();
            double y_distance = closest.getY() - b.getPosition().getY();

            // search where least penetration is needed
            if (Math.abs(x_distance) < Math.abs(y_distance)) {  // move object into x-Direction

                penetration = b.getRadius() - Math.abs(x_distance);
                if (x_distance < 0) { // if its negative
                    normal = new Vector2(-1, 0);
                } else {
                    normal = new Vector2(1, 0);
                }

            } else {
                penetration = b.getRadius() - Math.abs(y_distance);
                if (y_distance < 0) { // if its negative
                    normal = new Vector2(0, 1);
                } else {
                    normal = new Vector2(0, -1);
                }
            }

        }

        resolveCollision(a, b, penetration, normal, pa, pb);
    }

    protected void resolveRectVsRect(RectCollider a, RectCollider b, PhysicsGameComponent pa, PhysicsGameComponent pb) {
        // Vector from A to B from midpoint to midpoint
        Vector2 aMid = a.getPosition().add(a.getDimensions().asVector().scalarMult(0.5));
        Vector2 bMid = b.getPosition().add(b.getDimensions().asVector().scalarMult(0.5));
        Vector2 n = bMid.subtract(aMid);

        //TOdo: some kind of error

        // Calculate half extents along x axis for each object
        double a_extentX = (a.getDimensions().getX()) * 0.5; // width / 2
        double b_extentX = (b.getDimensions().getX()) * 0.5; // width / 2

        // Calculate overlap on x axis
        double x_overlap = a_extentX + b_extentX - Math.abs(n.getX());

        // SAT test on x axis
        if (x_overlap > 0) {
            // Calculate half extents along x axis for each object
            double a_extentY = (a.getDimensions().getY()) * 0.5; // height / 2
            double b_extentY = (b.getDimensions().getY()) * 0.5;

            // Calculate overlap on y axis
            double y_overlap = a_extentY + b_extentY - Math.abs(n.getY());

            Vector2 normal;
            double penetration;

            // SAT test on y axis
            if (y_overlap > 0) {
                // Find out which axis is axis of least penetration
                if (y_overlap < x_overlap) // Y axis has least penetration
                {
                    // Point towards B knowing that n points from A to B
                    if (n.getY() < 0)
                        normal = new Vector2(0, -1);
                    else
                        normal = new Vector2(0, 1);
                    penetration = y_overlap;
                    this.resolveCollision(a, b, penetration, normal, pa, pb);
                } else {
                    // Point toward B knowing that n points from A to B
                    if (n.getX() < 0)
                        normal = new Vector2(-1, 0);
                    else
                        normal = new Vector2(1, 0);
                    penetration = x_overlap;
                    this.resolveCollision(a, b, penetration, normal, pa, pb);
                }
            }
        }
    }

    protected void resolveCircleVsCircle(CircleCollider a, CircleCollider b, PhysicsGameComponent pa, PhysicsGameComponent pb) {

        // Vector from a to b
        Vector2 n = b.getPosition().subtract(a.getPosition());

        if (!CircleVsCircle(a, b)) {
            return;
        }  // they do not collide
        // if they do collide:

        double d = n.getLength();

        if (d != 0) { // if distance is not 0

            double penetration = (a.getRadius() + b.getRadius()) - d;
            Vector2 normal = n.getNormalized();
            resolveCollision(a, b, penetration, normal, pa, pb);
        } else {
            double penetration = a.getRadius();
            Vector2 normal = new Vector2(0, 1);
            resolveCollision(a, b, penetration, normal, pa, pb);
        }


    }

    protected void resolveCollision(Collider a, Collider b, double penetration, Vector2 normal, PhysicsGameComponent pa, PhysicsGameComponent pb) {
        //relative Velocity:
        Vector2 rv = pb.getVelocity().subtract(pa.getVelocity());

        // calc relative Velocity along normal
        double velAlongNormal = Vector2.dotProduct(rv, normal);

        // no resolving if objects are separating
        if (velAlongNormal > 0) {
            return;
        }

        // calculate restitution
        double e = Math.min(a.restitution, b.restitution);

        //calc impulse scalar
        double j = -(1 + e) * velAlongNormal;
        j /= pa.getMassInverse() + pb.getMassInverse();

        //apply impulse if non of them is a trigger
        if (!a.isTrigger && !b.isTrigger) {
            Vector2 impulse = normal.scalarMult(j);
            pa.addVelocity(impulse.scalarMult(pa.getMassInverse() * -1));
            pb.addVelocity(impulse.scalarMult(pb.getMassInverse()));

            this.positionalCorrection(a, b, penetration, normal, pa, pb);
        }

        for (CollissionListener c : collisionListeners) {
            c.onCollision(a, b);
        }
    }

    protected void positionalCorrection(Collider a, Collider b, double penetration, Vector2 normal, PhysicsGameComponent pa, PhysicsGameComponent pb) {
        double percentage = 0.5; // 0.2  - 0.8
        Vector2 correction = normal.scalarMult(penetration / (pa.getMassInverse() + pb.getMassInverse()) * percentage);
        a.reference.getPhysicsComponent().addPosition(correction.scalarMult(-pa.getMassInverse()));
        b.reference.getPhysicsComponent().addPosition(correction.scalarMult(pb.getMassInverse()));
    }

    public static Collider[] doesCollide(Collider collider, ArrayList<PhysicsGameComponent> physicsComponents) {
        ArrayList<Collider> allCollisions = new ArrayList<>();

        for (PhysicsGameComponent p : physicsComponents) {
            Collider c = p.getCollider();
            if (c == null) {
                continue;
            }

            // point collision logic
            if (c.doesCollide(collider)) {
                allCollisions.add(c);
            }
        }

        Collider[] collisions = new Collider[allCollisions.size()];
        return allCollisions.toArray(collisions);
    }

    public static Collider getPointCollider(Vector2 point) {
        return new CircleCollider(GameObject.getPlaceHolder(Transform.getIdentity(), null), point, 0.01);
    }

}
