package player;

import game.GameObject;
import game.GameWorld;
import logic.Dimension2D;
import logic.PlayerTypes;
import logic.Transform;
import logic.Vector2;
import physics.Collider;
import physics.CollissionListener;
import physics.PhysicsObject;
import physics.RectCollider;


public abstract class Player extends GameObject implements CollissionListener {

    public static final double maxHealth = 10;

    protected VisualPlayer visualPlayer;
    protected PlayerTypes type;
    protected PlayerState playerState = PlayerState.Default;
    protected boolean isOnGround = false;


    protected HealthManager healthManager;
    protected AttackManager attackManager;


    public Player(GameWorld world, Vector2 pos, PlayerTypes type, String tag) {
        super(tag, world);
        this.transform = new Transform(pos);
        this.physicsObject = new PhysicsObject(this);
        this.physicsObject.setCollider(new RectCollider(this, new Vector2(-0.2,-0.4), new Dimension2D(0.4, 0.9)));
        this.physicsObject.getCollider().addListener(this);

        this.type = type;
        this.visualPlayer = new VisualPlayer(type, this);
        this.attackManager = new AttackManager(this);
        this.healthManager = new HealthManager(this, maxHealth);
    }

    public void tick(GameWorld w, double deltaTime) {
        this.setPlayerState();
        visualPlayer.updatePlayer(w, deltaTime);
        attackManager.tick(w, deltaTime);
        healthManager.tick(deltaTime);
    }

    public void takeDamage(double damage, Vector2 force) {
        this.healthManager.takeDamage(damage);
        this.physicsObject.addForce(force);
    }

    protected void Jump() {
        if (isOnGround) {
            physicsObject.setVelocityY(-3);
            isOnGround = false;
        }
    }

    // -1 = left, 1 = right
    protected void walk(int dir) {
        physicsObject.addForce(new Vector2(dir*10, 0));
        setDir(dir);
        setState2Walk();
    }

    protected void kick() {
        this.playerState = PlayerState.Kick;
        visualPlayer.setState(playerState);
        attackManager.doKick();
        System.out.println(this.healthManager.getHealth());
    }

    protected void punch() {}

    protected void block() {}

    protected void setPlayerState() {
        if (!isOnGround) {
            this.playerState = PlayerState.Jump;
            visualPlayer.setState(playerState);
        }
    }

    public void setDir(int dir) {
        this.transform.setScale(new Vector2(Math.abs(transform.getXScale())*dir, transform.getYScale()));
    }

    protected void setState2Walk() {
        if (isOnGround && this.physicsObject.getVelocity().getLengthSquared() > 0.2) {
            this.playerState = PlayerState.Walk;
            visualPlayer.setState(playerState);
        }
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

    public PhysicsObject getPlayerPhysics() { return this.physicsObject; }

    public Transform getTransform() { return this.transform; }
}
