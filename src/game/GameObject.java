package game;

import display.Camera;
import logic.Transform;
import physics.PhysicsGameComponent;

import java.awt.*;
import java.util.ArrayList;

public class GameObject {

    protected Scene scene;

    protected String tag;
    protected Transform transform;
    protected PhysicsGameComponent physicsComponent;

    protected ArrayList<GameComponent> gameComponents = new ArrayList<>();

    public GameObject(String tag, Scene world) {
        this.tag = tag;
        this.scene = world;
    }

    public void Render(Graphics2D g, Camera cam) {
        for (GameComponent c : gameComponents) {
            c.Render(g, cam);
        }
    }

    public void tick(double deltaTime) {
        if (physicsComponent != null) {
            physicsComponent.tick(deltaTime);
        }

        for (GameComponent c : gameComponents) {
            c.tick(deltaTime);
        }
    }

    public void addComponent(GameComponent c) {
        gameComponents.add(c);
    }

    public void removeComponent(GameComponent c) {
        gameComponents.remove(c);
    }

    // region getters and setters:

    public void setPhysicsComponent(PhysicsGameComponent physicsComponent) {
        this.physicsComponent = physicsComponent;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public PhysicsGameComponent getPhysicsComponent() {
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
