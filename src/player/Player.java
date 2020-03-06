package player;

import display.Canvas;
import game.Consts;
import game.GameObject;
import game.GameWorld;
import logic.PlayerTypes;
import logic.Transform;
import logic.Vector2;
import physics.PhysicsObject;


public abstract class Player extends GameObject {

    protected VisualPlayer visualPlayer;
    protected PlayerTypes type;


    public Player(Vector2 pos, PlayerTypes type, String tag) {
        super(tag);
        this.transform = new Transform(pos);
        this.physicsObject = new PhysicsObject((GameObject)this);

        this.type = type;
        this.visualPlayer = new VisualPlayer(type, this);
    }

    public void tick(GameWorld w, double deltaTime) {
        visualPlayer.updatePlayer(w, deltaTime);
    }

    protected void setPlayerMaxPos() {
        Vector2 pos = transform.getPosition();
        this.transform.setPosition(new Vector2(pos.getX(), Math.min(pos.getY(), Consts.windowHeight-900)));
    }

    public PhysicsObject getPlayerPhysics() { return this.physicsObject; }

    public Transform getTransform() { return this.transform; }

    protected void Jump() {
        if (true) {
            physicsObject.setVelocityY(-2);
        }
    }

    protected void walkRight() {
        physicsObject.setVelocityX(1);
    }

    protected void walkLeft() {
        physicsObject.setVelocityX(-1);
    }
}
