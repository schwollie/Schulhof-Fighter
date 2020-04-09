package game;

import display.Camera;
import physics.PhysicsGameComponent;
import player.Player;
import time.TimeManager;

import java.awt.*;
import java.util.ArrayList;

public class Scene {

    public TimeManager timeManager;

    public ArrayList<GameObject> gameObjects = new ArrayList<>();

    private ArrayList<PhysicsGameComponent> physicsComponents = new ArrayList<>();

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public GameObject getCam() {
        for (GameObject g: gameObjects) {
            if (g.tag.equals("Camera")) {
                return g;
            }
        }
        throw new Error("No Camera Found!");
    }

    public ArrayList<PhysicsGameComponent> getPhysicsComponents() {
        physicsComponents.clear();

        for (GameObject g: gameObjects) {
            if (g.physicsComponent != null) {
                physicsComponents.add(g.physicsComponent);
            }
        }

        return physicsComponents;
    }

    public ArrayList<PhysicsGameComponent> getPhysicsComponentsTypePlayer() {
        physicsComponents.clear();

        for (GameObject g: gameObjects) {
            if (g.physicsComponent != null && g instanceof Player) {
                physicsComponents.add(g.physicsComponent);
            }
        }

        return physicsComponents;
    }

    public void tick() {

        for (GameObject g: gameObjects) {
            g.tick();
        }

    }

    public void Render(Graphics2D g, Camera cam) {
        for (GameObject gm: gameObjects) {
            gm.Render(g, cam);
        }
    }

}
