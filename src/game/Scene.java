package game;

import display.Camera;
import input.InputManager;
import physics.PhysicsGameComponent;
import player.Player;
import time.TimeManager;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class Scene implements Serializable {

    public InputManager inputManager;

    private LinkedList<GameObject> gameObjects2add = new LinkedList<>();
    private LinkedList<GameObject> gameObjects2remove = new LinkedList<>();

    public ArrayList<GameObject> gameObjects = new ArrayList<>();

    private final ArrayList<PhysicsGameComponent> physicsComponents = new ArrayList<>();

    public TimeManager timeManager;

    public void addGameObject(GameObject gameObject) {
        gameObjects2add.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects2remove.add(gameObject);
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Scene() {
        this.inputManager = new InputManager();
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public GameObject getCam() {
        for (GameObject g : gameObjects) {
            if (g.tag.equals("Camera")) {
                return g;
            }
        }
        throw new Error("No Camera Found!");
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

    public synchronized void tick() {
        inputManager.sendKeyStates();

        for (GameObject g : gameObjects) {
            g.tick();
        }

        //add and remove all gameObject
        gameObjects.addAll(gameObjects2add);
        gameObjects2add.clear();

        //add and remove all gameObject
        gameObjects.removeAll(gameObjects2remove);
        gameObjects2remove.clear();

    }

    public synchronized void Render(Graphics2D g, Camera cam) {
        for (GameObject gm : gameObjects) {
            gm.Render(g, cam);
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

}
