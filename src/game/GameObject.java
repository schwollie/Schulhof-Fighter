package game;

import logic.Transform;
import physics.PhysicsObject;

public class GameObject {

    protected Transform transform;
    protected PhysicsObject physicsObject;

    public GameObject() {}

    public void setPhysicsObject(PhysicsObject physicsObject) {
        this.physicsObject = physicsObject;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public PhysicsObject getPhysicsObject() {
        return physicsObject;
    }

    public Transform getTransform() {
        return transform;
    }
}
