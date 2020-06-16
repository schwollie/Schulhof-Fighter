package scenes;

import components.GuiCanvas;
import display.Camera;
import gameobjects.GameObject;
import graphics.RenderManager;
import loading.OnLoadFinishedListener;
import physics.PhysicsGameComponent;
import player.Player;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;

public class Scene implements Serializable, OnLoadFinishedListener {

    private final RenderManager renderManager;
    private GuiCanvas guiCanvas;

    public Scene() {
        renderManager = new RenderManager();
        //guiCanvas = new GameHUD(new Dimension2D(Consts.windowWidth, Consts.windowHeight));
    }


    public synchronized void tick() {
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

    public ArrayList<PhysicsGameComponent> getPhysicsComponents() {
        physicsComponents.clear();

        for (GameObject g : gameObjects) {
            if (g.getPhysicsComponent() != null) {
                physicsComponents.add(g.getPhysicsComponent());
            }
        }

        return physicsComponents;
    }

    public ArrayList<PhysicsGameComponent> getPhysicsComponentsTypePlayer() {
        physicsComponents.clear();

        for (GameObject g : gameObjects) {
            if (g.getPhysicsComponent() != null && g instanceof Player) {
                physicsComponents.add(g.getPhysicsComponent());
            }
        }

        return physicsComponents;
    }

    public GameObject getCam() {
        for (GameObject g : gameObjects) {
            if (g.getTag().equals("Camera")) {
                return g;
            }
        }

        System.err.println("No cam found");
        return null;
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

    @Override
    public void onLoadFinish() {
    }
}
