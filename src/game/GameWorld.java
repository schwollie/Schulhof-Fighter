package game;

import display.Canvas;
import graphics.Sprite;
import physics.Collider;
import physics.PhysicsObject;
import player.Player;

import java.util.ArrayList;

public class GameWorld {

    public Player player1;
    public Player player2;

    public ArrayList<PhysicsObject> physicsObjects = new ArrayList<>();

    // all sprites that can be rendered
    private ArrayList<Sprite> sprites = new ArrayList<>();

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public void tick(double deltaTime) {

        player1.tick(this, deltaTime, this);
        player2.tick(this, deltaTime, this);

        for (PhysicsObject p: physicsObjects) {
            p.calcForces(deltaTime, this);
        }

        for (PhysicsObject p: physicsObjects) {
            p.updatePos(deltaTime);
            p.getCollider().updateSprite(this);
        }
    }

}
