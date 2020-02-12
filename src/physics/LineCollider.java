package physics;

import logic.Transform;
import logic.Vector2;

public class LineCollider extends Collider {

    private double gradient;

    public LineCollider(Transform transform, PhysicsObject physicsObject, double gradient) {
        super(transform, physicsObject);
        this.gradient = gradient;
    }


    @Override
    public Vector2 calcForce(PhysicsObject other, double dt) {
        if (isStatic) { return Vector2.zero; }
        // if other is a circle Collider
        if (other.getCollider() instanceof CircleCollider) { return calcForceCircleCollider((CircleCollider) other.getCollider(), dt); }

        return Vector2.zero;
    }

    private Vector2 calcForceCircleCollider(CircleCollider other, double dt) {
        return Vector2.zero;
    }

    public double getGradient() {
        return gradient;
    }
}

