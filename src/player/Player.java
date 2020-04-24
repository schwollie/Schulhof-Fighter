package player;

import gameobjects.GameObject;
import graphics.RenderManager;
import logic.Dimension2D;
import logic.PlayerType;
import logic.Transform;
import logic.Vector2;
import physics.Collider;
import physics.CollissionListener;
import physics.PhysicsGameComponent;
import physics.RectCollider;
import scenes.Scene;


public abstract class Player extends GameObject implements CollissionListener {

    public static final double maxHealth = 100;

    protected VisualPlayer visualPlayer;
    protected PlayerType type;
    protected PlayerState playerState = PlayerState.Default;
    protected boolean isOnGround = false;
    protected boolean canMove = true;


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
    public void takeDamage(double damage, Vector2 force, GameObject damager) {
        this.healthManager.takeDamage(damage);
        this.physicsComponent.addForce(force);

        if (RandomClass.chance(0.5)) {
            this.playerState = PlayerState.GotHit1;
        } else {
            this.playerState = PlayerState.GotHit2;
        }
        this.visualPlayer.setState(playerState);
        if (damager instanceof Player) {
            attackManager.gotHit();
        }
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
        changePlayerState(PlayerState.Kick);
        attackManager.doKick();
        //System.out.println(this.healthManager.getHealth());
    }

    protected void punch() {
        changePlayerState(PlayerState.Punch);
        attackManager.doPunch();
    }

    protected void block() {
    }

    protected void shootProjectile() {
        changePlayerState(PlayerState.SpecialAttack);
        attackManager.shoot();
    }

    private void changePlayerState(PlayerState state) {
        this.playerState = state;
        visualPlayer.setState(state);
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

    public boolean canMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
}
