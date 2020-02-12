package physics;

import game.Consts;
import logic.Transform;
import logic.Vector2;

public class CircleCollider extends Collider {

    private double radius;
    private int layer = 1;

    public CircleCollider(Transform transform, PhysicsObject physicsObject, double radius) {
        super(transform, physicsObject);
        this.radius = radius;
    }


    @Override
    public Vector2 calcForce(PhysicsObject other, double dt) {
        if (isStatic) { return Vector2.zero; }
        // if other is a circle Collider
        if (other.getCollider() instanceof CircleCollider) { return calcForceCircleCollider((CircleCollider) other.getCollider(), dt); }
        // if other is a line Collider
        if (other.getCollider() instanceof LineCollider) { return calcForceLineCollider((LineCollider) other.getCollider(), dt); }
        return Vector2.zero;
    }

    private Vector2 calcForceCircleCollider(CircleCollider other, double dt) {
        Vector2 deltaPos = this.transform.getPosition().subtract(other.transform.getPosition());
        double distance = deltaPos.getLength();

        if (distance > radius + other.getRadius()) { return Vector2.zero; }  // no collision

        // F*dt = m1*v1 - m2*v2
        double m1 = this.physicsObject.getMass(); double m2 = other.physicsObject.getMass();
        Vector2 v1 = this.physicsObject.getVelocity(); Vector2 v2 = other.physicsObject.getVelocity();
        double force = (v1.scalarMult(m1).subtract( v2.scalarMult(m2) ) ).scalarMult(-1/dt).getLength();

        Vector2 direction = deltaPos.getNormalized();
        return direction.scalarMult(force);
    }

    private Vector2 calcForceLineCollider(LineCollider other, double dt) {

        // only works with straight lines
        if (other.getGradient() != 0) { throw new Error("Gradient is not 0");}

        Vector2 deltaPos = other.transform.getPosition().subtract(this.transform.getPosition());
        deltaPos.setX(0);
        double distance = deltaPos.getLength();
        //System.out.println(distance);

        if (distance > radius) { return Vector2.zero; }  // no collision

        //this.physicsObject.setVelocityY(0);
        //this.physicsObject.addForce(new Vector2(0, -Consts.gravity));


        // F*dt = m1*v1  // because Line is static
        double m1 = this.physicsObject.getMass();
        Vector2 v1 = this.physicsObject.getVelocity();

        double force = Math.abs(v1.getY()*m1/dt) + this.physicsObject.getForce().getY();

        //deltaPos.print();
        Vector2 direction = deltaPos.getNormalized();
        //direction.setY((Math.round(direction.getY() * 10) / 10));

        //direction.scalarMult(force).print();
        //direction.print();
        //System.out.println(force);

        return direction.scalarMult(-force);
        //return force;



        //return Vector2.zero;

    }

    public double getRadius() {
        return radius;
    }

    public void setLayer(int newLayer) { this.layer = newLayer; }
}
