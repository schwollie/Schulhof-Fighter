package player;

import gameobjects.GameObject;
import graphics.RenderManager;
import logic.*;
import physics.Collider;
import physics.CollissionListener;
import physics.PhysicsGameComponent;
import physics.RectCollider;
import player.controller.ControllerType;
import player.controller.HumanController;
import player.controller.PlayerController;
import player.utils.AttackManager;
import player.utils.HealthManager;
import player.utils.VisualPlayer;
import scenes.Scene;


public class Player extends GameObject implements CollissionListener {

    public static final double maxHealth = 100;

    private PlayerSide side;
    private PlayerController controller;
    private VisualPlayer visualPlayer;
    private PlayerType type;
    private PlayerState playerState = PlayerState.Default;

    private boolean isOnGround = false;
    private boolean canMove = true;

    private HealthManager healthManager;
    private AttackManager attackManager;

    //region Constructor and init methods:
    public Player(Scene world, Vector2 pos, PlayerType type, String tag, ControllerType cType, PlayerSide side, GameObject gameHandler) {
        super(tag, world, 5);

        this.side = side;
        this.transform = new Transform(pos);
        setupController(cType, gameHandler);
        setupPhysics();
        setupStats(type);
    }

    private void setupController(ControllerType controllerType, GameObject gameHandler) {
        switch (controllerType) {
            case HumanController -> this.controller = new HumanController(this, side, gameHandler);
            default -> throw new Error("Controller has not been implemented yet!");
        }
    }

    private void setupPhysics() {
        this.physicsComponent = new PhysicsGameComponent(this);
        this.physicsComponent.setCollider(new RectCollider(this, new Vector2(-0.2, -0.5), new Dimension2D(0.4, 0.9)));
        this.physicsComponent.getCollider().addListener(this);
    }

    private void setupStats(PlayerType type) {
        this.type = type;
        this.visualPlayer = new VisualPlayer(type, this);
        this.attackManager = new AttackManager(this);
        this.healthManager = new HealthManager(this, maxHealth);
    }

    //endregion

    // region tick + update Sprites:
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
    //endregion

    // region damage Handling:
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
    // endregion

    // region  action Handling
    public void Jump() {

        if (isOnGround && canMove()) {
            physicsComponent.setVelocityY(3);
            isOnGround = false;
            //if (!hasComponent(Shaker.class))
            //    scene.getCam().addComponent(new Shaker(scene.getCam(), new Vector2(0.02, 0.02), new Vector2(1, 1), 1));
        }
    }

    // -1 = left, 1 = right
    public void walk(int dir) {
        if (canMove()) {
            physicsComponent.addForce(new Vector2(dir * 10, 0));
            setDir(dir);
            setState2Walk();
        }
    }

    public void kick() {
        changePlayerState(PlayerState.Kick);
        attackManager.doKick();
        //System.out.println(this.healthManager.getHealth());
    }

    public void punch() {
        changePlayerState(PlayerState.Punch);
        attackManager.doPunch();
    }

    public void block() {
    }

    public void shootProjectile() {
        changePlayerState(PlayerState.SpecialAttack);
        attackManager.shoot();
    }

    // endregion

    // region state Handling:
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

    // endregion


    @Override
    public void onCollision(Collider c1, Collider c2) {
        if (c1.getGameObject().getTag().equals("ground") || c2.getGameObject().getTag().equals("ground")) {
            isOnGround = true;
        }
    }

    public void onDeath() {
        this.destroy(1);
    }

    // region getters + setters
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

    public PlayerController getController() {
        return controller;
    }

    public PlayerType getType() {
        return type;
    }

    // endregion
}
