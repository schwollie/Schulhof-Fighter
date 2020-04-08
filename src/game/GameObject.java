package game;

import logic.Transform;
import physics.PhysicsObject;

public class GameObject {

    protected GameWorld gameWorld;

    protected String tag;
    protected Transform transform;
    protected PhysicsObject physicsObject;

    public GameObject(String tag, GameWorld world) {
        this.tag = tag;
        this.gameWorld = world;
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

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public static GameObject getPlaceHolder(Transform trans, GameWorld g) {
        GameObject p = new GameObject("placeholder", g);
        p.transform = trans;
        return p;
    }
}
