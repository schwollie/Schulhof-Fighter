package player;

import game.GameObject;
import game.Scene;
import graphics.RenderManager;
import logic.*;
import physics.Collider;
import physics.CollissionListener;
import physics.PhysicsGameComponent;
import physics.RectCollider;

import java.util.Random;


public abstract class Player extends GameObject implements CollissionListener {

    public static final double maxHealth = 10;

    protected VisualPlayer visualPlayer;
    protected PlayerType type;
    protected PlayerState playerState = PlayerState.Default;
    protected boolean isOnGround = false;


    protected HealthManager healthManager;
    protected AttackManager attackManager;


    public Player(Scene world, Vector2 pos, PlayerType type, String tag) {
        super(tag, world);
        this.transform = new Transform(pos);
        this.physicsComponent = new PhysicsGameComponent(this);
        this.physicsComponent.setCollider(new RectCollider(this, new Vector2(-0.2, -0.5), new Dimension2D(0.4, 0.9)));
        this.physicsComponent.getCollider().addListener(this);

        this.type = type;
        this.visualPlayer = new VisualPlayer(type, this);
        this.attackManager = new AttackManager(this);
        this.healthManager = new HealthManager(this, maxHealth);
    }

    @Override
    public void Tick() {
        super.Tick();

        this.setPlayerState();
        visualPlayer.tick();
        attackManager.tick();
        healthManager.tick();
    }

    @Override
    public void UpdateSprites(RenderManager renderManager) {
        super.UpdateSprites(renderManager);

        this.visualPlayer.UpdateDrawables(renderManager);
    }

    // region action Handling:
    public void takeDamage(double damage, Vector2 force) {
        this.healthManager.takeDamage(damage);
        this.physicsComponent.addForce(force);

        if (RandomClass.chance(0.5)) {
            this.playerState = PlayerState.GotHit1;
        } else {
            this.playerState = PlayerState.GotHit2;
        }

        this.visualPlayer.setState(playerState);
    }

    protected void Jump() {

        if (isOnGround) {
            physicsComponent.setVelocityY(3);
            isOnGround = false;
            //if (!hasComponent(Shaker.class))
            //    scene.getCam().addComponent(new Shaker(scene.getCam(), new Vector2(0.02, 0.02), new Vector2(1, 1), 1));
        }
    }

    // -1 = left, 1 = right
    protected void walk(int dir) {
        physicsComponent.addForce(new Vector2(dir * 10, 0));
        setDir(dir);
        setState2Walk();
    }

    protected void kick() {
        this.playerState = PlayerState.Kick;
        visualPlayer.setState(playerState);
        attackManager.doKick();
        //System.out.println(this.healthManager.getHealth());
    }

    protected void punch() {
        this.playerState = PlayerState.Punch;
        visualPlayer.setState(playerState);
        attackManager.doPunch();
    }

    protected void block() {
    }

    protected void setPlayerState() {
        if (!isOnGround) {
            this.playerState = PlayerState.Jump;
            visualPlayer.setState(playerState);
        }
    }

    public void setDir(int dir) {
        this.transform.setScale(new Vector2(Math.abs(transform.getXScale()) * dir, transform.getYScale()));
    }

    protected void setState2Walk() {
        if (isOnGround && this.physicsComponent.getVelocity().getLengthSquared() > 0.2) {
            this.playerState = PlayerState.Walk;
            visualPlayer.setState(playerState);
        }
    }


    @Override
    public void onCollision(Collider c1, Collider c2) {
        if (c1.getGameObject().getTag().equals("ground") || c2.getGameObject().getTag().equals("ground")) {
            isOnGround = true;
        }
    }

    public void onDeath() {
        this.destroy(1);
    }

    // endregion

    public PhysicsGameComponent getPlayerPhysics() {
        return this.physicsComponent;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public AttackManager getAttackManager() {
        return attackManager;
    }

    public HealthManager getHealthManager() {
        return healthManager;
    }
}
