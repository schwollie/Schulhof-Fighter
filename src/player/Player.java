package player;

import display.Canvas;
import game.Consts;
import game.GameObject;
import logic.PlayerTypes;
import logic.Transform;
import logic.Vector2;
import physics.PhysicsObject;


public abstract class Player extends GameObject {

    protected VisualPlayer visualPlayer;
    protected PlayerTypes type;

    public Player(Vector2 pos, PlayerTypes type) {
        this.transform = new Transform(pos);
        this.physicsObject = new PhysicsObject((GameObject)this);

        this.type = type;
        this.visualPlayer = new VisualPlayer(type, this);
    }

    public void tick(Canvas c, double deltaTime) {
        visualPlayer.updatePlayer(c, deltaTime);
    }

    protected void setPlayerMaxPos() {
        Vector2 pos = transform.getPosition();
        this.transform.setPosition(new Vector2(pos.getX(), Math.min(pos.getY(), Consts.windowHeight-900)));
    }

    public PhysicsObject getPlayerPhysics() { return this.physicsObject; }

    public Transform getTransform() { return this.transform; }
}
