package physics;

import logic.Transform;
import logic.Vector2;


public abstract class Collider {
    protected Transform transform;  // only the position of transform is important
    protected PhysicsObject physicsObject;
    protected boolean isStatic = false;

    public Collider(Transform transform, PhysicsObject physicsObject) {
        this.transform = transform;
        this.physicsObject = physicsObject;
    }

    public abstract Vector2 calcForce(PhysicsObject other, double dt);

    public Transform getTransform() { return this.transform; }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }
}
