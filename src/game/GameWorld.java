package game;

import display.Canvas;
import physics.PhysicsObject;
import player.Player;

import java.util.ArrayList;

public class GameWorld {

    public Player player1;
    public Player player2;

    public ArrayList<PhysicsObject> physicsObjects = new ArrayList<>();

    public void tick(double deltaTime, Canvas mainCanvas) {

        player1.tick(mainCanvas, deltaTime);
        player2.tick(mainCanvas, deltaTime);

        for (PhysicsObject p: physicsObjects) {
            p.calcForces(deltaTime, this);
        }

        for (PhysicsObject p: physicsObjects) {
            p.updatePos(deltaTime);

            p.getCollider().updateSprite(mainCanvas);
        }
    }

}
