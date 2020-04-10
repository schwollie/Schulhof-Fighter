package game;

import display.Camera;
import logic.Transform;
import physics.PhysicsGameComponent;
import time.TimeManager;

import java.awt.*;
import java.util.ArrayList;

public class GameObject {

    protected Scene scene;

    protected String tag;
    protected Transform transform = new Transform();
    protected PhysicsGameComponent physicsComponent;

    protected ArrayList<GameComponent> gameComponents = new ArrayList<>();

    private final ArrayList<GameComponent> components2Add = new ArrayList<>();
    private final ArrayList<GameComponent> components2Remove = new ArrayList<>();

    public GameObject(String tag, Scene world) {
        this.tag = tag;
        this.scene = world;
    }

    public void Render(Graphics2D g, Camera cam) {
        if (physicsComponent!=null)
            physicsComponent.Render(g, cam);

        for (GameComponent c : gameComponents) {
            c.Render(g, cam);
        }
    }

    public void tick() {
        if (physicsComponent != null) {
            physicsComponent.tick();
        }

        for (GameComponent c : gameComponents) {
            c.tick();
        }

        manageComponentChanges();
    }

    private void manageComponentChanges() {
        gameComponents.addAll(components2Add);
        components2Add.clear();

        gameComponents.removeAll(components2Remove);
        components2Remove.clear();
    }

    public void addComponent(GameComponent c) {
        // concurrentModificationError could occur if we call this inside tick:
        //gameComponents.add(c);
        components2Add.add(c);
    }

    public void removeComponent(GameComponent c) {
        // concurrentModificationError could occur if we call this inside tick:
        // gameComponents.remove(c);
        components2Remove.add(c);
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

    public static GameObject getPlaceHolderEmpty(Scene g) {
        GameObject p = new GameObject("placeholder", g);
        p.transform = Transform.getEmpty();
        return p;
    }

    public TimeManager getTime() {
        return this.scene.timeManager;
    }

    public boolean hasComponent(Class<? extends GameComponent> component) {
        // return gameComponents.stream().anyMatch(c -> c.getClass()==component);
        for (GameComponent c : gameComponents) {
            if (c.getClass() == component) {
                return true;
            }
        }

        return false;
    }

    public GameComponent getComponent(Class<GameComponent> component) {
        for (GameComponent c : gameComponents) {
            if (c.getClass() == component) {
                return c;
            }
        }

        throw new Error("GameObject has no Component : " + component + " -> Check this with hasComponent()!");
    }

    //endregion


}
