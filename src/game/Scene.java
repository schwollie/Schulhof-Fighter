package game;

import display.Camera;
import physics.PhysicsComponent;

import java.awt.*;
import java.util.ArrayList;

public class Scene {

    public ArrayList<GameObject> gameObjects = new ArrayList<>();

    private ArrayList<PhysicsComponent> physicsComponents = new ArrayList<>();

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public ArrayList<PhysicsComponent> getPhysicsComponents() {
        physicsComponents.clear();

        for (GameObject g: gameObjects) {
            if (g.physicsComponent != null) {
                physicsComponents.add(g.physicsComponent);
            }
        }

        return physicsComponents;
    }

    public void tick(double deltaTime) {

        for (GameObject g: gameObjects) {
            g.tick(deltaTime);
        }

    }

    public void Render(Graphics2D g, Camera cam) {
        for (GameObject gm: gameObjects) {
            gm.Render(g, cam);
        }
    }

}
