package game;

import logic.Transform;
import physics.PhysicsObject;

public class GameObject {

    protected String tag;
    protected Transform transform;
    protected PhysicsObject physicsObject;

    public GameObject(String tag) {
        this.tag = tag;
    }

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

    public String getTag() {
        return tag;
    }

    public static GameObject getPlaceHolder(Transform trans) {
        GameObject p = new GameObject("placeholder");
        p.transform = trans;
        return p;
    }
}
