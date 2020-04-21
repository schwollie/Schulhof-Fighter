package game;

import components.GuiCanvas;
import display.Camera;
import graphics.RenderManager;
import input.InputManager;
import logic.Dimension2D;
import physics.Collider;
import physics.PhysicsGameComponent;
import player.Player;
import prefabs.HUD.GameHUD;
import time.TimeManager;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;

public class Scene implements Serializable {

    private final InputManager inputManager;
    private final TimeManager timeManager;
    private final RenderManager renderManager;
    private GuiCanvas guiCanvas = new GameHUD(new Dimension2D(Consts.windowWidth, Consts.windowHeight));

    public Scene() {
        inputManager = new InputManager();
        timeManager = new TimeManager(1000);
        renderManager = new RenderManager();
    }

    private final LinkedList<GameObject> gameObjects2add = new LinkedList<>();
    private final LinkedList<GameObject> gameObjects2remove = new LinkedList<>();
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private final ArrayList<PhysicsGameComponent> physicsComponents = new ArrayList<>();


    public void addGameObject(GameObject gameObject) {
        gameObjects2add.add(gameObject);
    }

    public void addGameObjectNow(GameObject gameObject) throws ConcurrentModificationException {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects2remove.add(gameObject);
    }

    public void removeGameObjectNow(GameObject gameObject) throws ConcurrentModificationException {
        gameObjects.remove(gameObject);
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public ArrayList<PhysicsGameComponent> getPhysicsComponents() {
        physicsComponents.clear();

        for (GameObject g : gameObjects) {
            if (g.physicsComponent != null) {
                physicsComponents.add(g.physicsComponent);
            }
        }

        return physicsComponents;
    }

    public ArrayList<PhysicsGameComponent> getPhysicsComponentsTypePlayer() {
        physicsComponents.clear();

        for (GameObject g : gameObjects) {
            if (g.physicsComponent != null && g instanceof Player) {
                physicsComponents.add(g.physicsComponent);
            }
        }

        return physicsComponents;
    }

    public GameObject getCam() {
        for (GameObject g : gameObjects) {
            if (g.tag.equals("Camera")) {
                return g;
            }
        }
        throw new Error("No Camera Found!");
    }

    public TimeManager getTimeManager() { return timeManager; }

    public synchronized void tick() {
        inputManager.sendKeyStates();

        for (GameObject g : gameObjects) {
            g.Tick();
        }

        //add and remove all gameObject
        gameObjects.addAll(gameObjects2add);
        gameObjects2add.clear();

        //add and remove all gameObject
        gameObjects.removeAll(gameObjects2remove);
        gameObjects2remove.clear();

    }

    public synchronized void Render(Graphics2D g, Camera cam) {
        // first update all Sprites and set their currentPositions
        updateSprites();

        // then go through all Sprites and Render them accordingly to their layer
        renderManager.RenderSprites(g, cam);

        // then render the hud on top of the rendered scene
        guiCanvas.Render(g, cam);
    }

    public synchronized void updateSprites() {
        renderManager.clearBuffer();
        for (GameObject gm : gameObjects) {
            gm.UpdateSprites(renderManager);
        }
    }

    public void unloadImages() {
        for (GameObject c : gameObjects) {
            c.unloadImage();
        }
        for (GameObject c : gameObjects2add) {
            c.unloadImage();
        }
        for (GameObject c : gameObjects2remove) {
            c.unloadImage();
        }
    }

    public void loadImages() {
        for (GameObject c : gameObjects) {
            c.loadImage();
        }
        for (GameObject c : gameObjects2add) {
            c.loadImage();
        }
    }

    public GuiCanvas getGuiCanvas() {
        return guiCanvas;
    }

    public void setGuiCanvas(GuiCanvas guiCanvas) {
        this.guiCanvas = guiCanvas;
    }
}
