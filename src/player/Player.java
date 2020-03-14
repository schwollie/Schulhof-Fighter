package player;

import game.Consts;
import game.GameObject;
import game.GameWorld;
import logic.PlayerTypes;
import logic.Transform;
import logic.Vector2;
import physics.Collider;
import physics.CollissionListener;
import physics.PhysicsObject;
import physics.RectCollider;


public abstract class Player extends GameObject implements CollissionListener {

    protected VisualPlayer visualPlayer;
    protected PlayerTypes type;
    protected PlayerState playerState = PlayerState.Default;
    protected boolean isOnGround = false;

    protected double Health = 10.0;
    protected AttackManager attackManager;


    public Player(Vector2 pos, PlayerTypes type, String tag) {
        super(tag);
        this.transform = new Transform(pos);
        this.physicsObject = new PhysicsObject(this);
        this.physicsObject.setCollider(new RectCollider(this, new Vector2(0.3,0.1),physicsObject, new Vector2(0.4, 0.9)));
        this.physicsObject.getCollider().addListener(this);

        this.type = type;
        this.visualPlayer = new VisualPlayer(type, this);
        this.attackManager = new AttackManager(this);
    }

    public void tick(GameWorld w, double deltaTime) {
        this.setPlayerState();
        visualPlayer.updatePlayer(w, deltaTime, playerState);
    }

    public PhysicsObject getPlayerPhysics() { return this.physicsObject; }

    public Transform getTransform() { return this.transform; }

    protected void Jump() {
        if (isOnGround) {
            physicsObject.setVelocityY(-3);
            isOnGround = false;
        }
    }

    protected void walkRight() {
        physicsObject.setVelocityX(1);
    }

    protected void walkLeft() {
        physicsObject.setVelocityX(-1);
    }

    protected void setPlayerState() {
        if (!isOnGround) {
            this.playerState = PlayerState.Jump;
            return;
        }

        if (Math.abs(this.physicsObject.getVelocity().getX()) > 0.2) {
            this.playerState = PlayerState.WalkRight;
            return;
        }

        this.playerState = PlayerState.Default;
    }

    @Override
    public void onCollision(Collider c1, Collider c2) {
        //test for collision with ground
        String tag1 = c1.getGameObject().getTag();
        String tag2 = c2.getGameObject().getTag();

        if ((tag1.equals(this.tag) || tag2.equals(this.tag)) && (tag1.equals("Ground") || tag2.equals("Ground"))) {
            isOnGround = true;
        }

    }
}
