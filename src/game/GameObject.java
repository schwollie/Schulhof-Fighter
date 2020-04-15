package game;

import graphics.SpriteManager;
import logic.Transform;
import physics.PhysicsGameComponent;
import time.TimeEventListener;
import time.TimeManager;
import time.Timer;

import java.io.Serializable;
import java.util.ArrayList;

public class GameObject implements Serializable, TimeEventListener {

    protected Scene scene;

    protected String tag;
    protected Transform transform = new Transform();
    protected PhysicsGameComponent physicsComponent;

    protected int layer = 1; // 1 = default

    protected ArrayList<GameComponent> gameComponents = new ArrayList<>();

    private final ArrayList<GameComponent> components2Add = new ArrayList<>();
    private final ArrayList<GameComponent> components2Remove = new ArrayList<>();

    public GameObject(String tag, Scene world) {
        this.tag = tag;
        this.scene = world;
    }

    public void UpdateSprites(SpriteManager spriteManager) {
        //if (physicsComponent!=null)
        //    physicsComponent.Render(g, cam);

        for (GameComponent c : gameComponents) {
            c.UpdateSprites(spriteManager);
        }
    }

    public void Tick() {
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
        p.transform = Transform.getIdentity();
        return p;
    }

    public TimeManager getTime() {
        return this.scene.timeManager;
    }

    public int getLayer() {
        return layer;
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

    public void destroy(double time) {
        this.addComponent(new Timer(this,"_DestroyObject_", time, this));
    }

    @Override
    public void onTimerStops(String timerName) {
        if (timerName.equals("_DestroyObject_")) {
            scene.removeGameObject(this);
        }
    }

    public void unloadImage() {
        for (GameComponent c : gameComponents) {
            c.unloadImage();
        }
        for (GameComponent c : components2Add) {
            c.unloadImage();
        }
        for (GameComponent c : components2Remove) {
            c.unloadImage();
        }
    }

    public void loadImage() {
        for (GameComponent c : gameComponents) {
            c.loadImage();
        }
        for (GameComponent c : components2Add) {
            c.loadImage();
        }
    }

    //endregion


}
