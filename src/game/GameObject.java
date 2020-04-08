package game;

import gui.display.Camera;
import logic.Transform;
import physics.PhysicsComponent;

import java.awt.*;
import java.util.ArrayList;

public class GameObject {

    protected Scene scene;

    protected String tag;
    protected Transform transform;
    protected PhysicsComponent physicsComponent;

    protected ArrayList<Component> components = new ArrayList<>();

    public GameObject(String tag, Scene world) {
        this.tag = tag;
        this.scene = world;
    }

    public void Render(Graphics2D g, Camera cam) {
        for (Component c : components) {
            c.Render(g, cam);
        }
    }

    public void tick(double deltaTime) {
        if (physicsComponent != null) {
            physicsComponent.tick(deltaTime);
        }

        for (Component c : components) {
            c.tick(deltaTime);
        }
    }

    public void addComponent(Component c) {
        components.add(c);
    }

    public void removeComponent(Component c) {
        components.remove(c);
    }

    // region getters and setters:

    public void setPhysicsComponent(PhysicsComponent physicsComponent) {
        this.physicsComponent = physicsComponent;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }

    public Transform getTransform() {
        return transform;
    }

    public String getTag() {
        return tag;
    }

    public Scene getScene() {
        return scene;
    }

    public static GameObject getPlaceHolder(Transform trans, Scene g) {
        GameObject p = new GameObject("placeholder", g);
        p.transform = trans;
        return p;
    }

    //endregion


}
