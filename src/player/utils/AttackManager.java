package player.utils;

import display.Camera;
import gameobjects.ComponentType;
import gameobjects.GameComponent;
import gameobjects.GameObject;
import logic.Dimension2D;
import logic.Vector2;
import physics.Collider;
import physics.CollissionListener;
import physics.RectCollider;
import player.Player;
import time.TimeEventListener;
import time.Timer;

public class AttackManager extends GameComponent implements CollissionListener, TimeEventListener {

    // flags:
    private final boolean isBlocking = false;

    // timers
    private final Timer coolDownTimer = new Timer(this.reference, "coolDownTimer", 1);
    private final Timer shootTimer = new Timer(this.reference, "shootTimer", 0.3);
    private final Timer punchTimer = new Timer(this.reference, "punchTimer", 0.25);
    private final Timer kickTimer = new Timer(this.reference, "kickTimer", 0.25);
    private final Timer hitComboTimer = new Timer(this.reference, "hitCombo", 1.5);

    private boolean hasReloaded = true;
    private int hitCombo = 0;

    public AttackManager(GameObject ref) {
        super(ref, ComponentType.Logic);

        if (!(ref instanceof Player)) {
            throw new Error("GameObject has to be of type Player");
        }

        initTimers();
    }

    private void initTimers() {
        punchTimer.addListener(this);
        kickTimer.addListener(this);
        coolDownTimer.addListener(this);
        hitComboTimer.addListener(this);
        shootTimer.addListener(this);
        punchTimer.pause();
        kickTimer.pause();
        coolDownTimer.pause();
        hitComboTimer.pause();
        shootTimer.pause();
    }

    public void block() {

    }

    public void receiveHit(double damage, Vector2 force, GameObject sender) {
        ((Player) reference).takeDamage(damage, new Vector2(sender.getTransform().getXScale() * force.getX(), force.getY()), sender);
    }

    public void shoot() {
        if (hasReloaded) {
            hasReloaded = false;
            coolDownTimer.resume();
            shootTimer.resume();
            ((Player) reference).setCanMove(false);
        }
    }

    public void shootProjectile() {
        Projectile p = new Projectile(reference.getScene(), reference, reference.getTransform().getPosition(), getDirection() * 1.5, getDirection());
        reference.getScene().addGameObject(p);
        ((Player) reference).setCanMove(true);
    }

    public void doKick() {
        kickTimer.resume();
        ((Player) reference).setCanMove(false);
    }

    public void doKickHit() {
        doAttack(new Vector2(0.5, 0.2), Vector2.zero, 1, new Vector2(2, 1));
    }

    public void doPunch() {
        punchTimer.resume();
        ((Player) reference).setCanMove(false);
    }

    public void doPunchHit() {
        doAttack(new Vector2(0.5, 0.2), Vector2.zero, 1, new Vector2(2, 1));
    }

    private void doAttack(Vector2 range, Vector2 offset, double damage, Vector2 force) {
        ((Camera) reference.getScene().getCam()).shake(new Vector2(0.02, 0.02), new Vector2(2 * damage, 2), .4);

        Collider[] cs = getCollidersInRange(range, offset);
        force = testForComboHits(force);
        Player player = ((Player) reference);
        dealDamageToColliders(cs, damage, force, player);
        player.setCanMove(true);


        /*ParticleSystem p = new ParticleSystem(reference, new XRange(0.5, 2), 1, new XRange(100, 100));
        p.setRelativeTransform(new Transform(new Vector2(.5*getDirection(),0)));
        p.setRelativeTransform(p.getRelativeTransform().setGetScale(new Vector2(0.5, 0.5)));
        p.setLiveTime(new XRange(0.1, 1));
        p.setStartForce(new XRange(1, 5));
        p.setGravityFactor(new XRange(0.1, 0.2));
        p.setLocalSpace(false);
        p.start();
        reference.addComponent(p);*/
    }

    private void dealDamageToColliders(Collider[] cs, double damage, Vector2 force, Player attacker) {
        for (Collider c : cs) {
            if (c.getGameObject() instanceof Player && !c.getGameObject().equals(reference)) {
                Player other = (Player) c.getGameObject();
                other.takeDamage(damage, new Vector2(getDirection() * force.getX(), force.getY()), reference);
                hitCombo++;
                attacker.getHealthManager().increaseStamina(attacker.getHealthManager().getAttackStamina());
            }
        }
    }

    private Vector2 testForComboHits(Vector2 force) {
        if (hitCombo >= 2) { //3 hits
            hitCombo = 0;
            force = force.scalarMult(4);
        } else {
            hitComboTimer.resetTimer();
        }
        return force;
    }

    private Collider[] getCollidersInRange(Vector2 range, Vector2 _offset) {
        double directionFactor = getDirection();
        Vector2 offset = new Vector2(_offset.getX() * directionFactor, _offset.getY());

        GameObject p = GameObject.getPlaceHolder(reference.getTransform(), reference.getScene());
        RectCollider col = new RectCollider(p, offset, new Dimension2D(range.getX() * directionFactor, range.getY()));

        //PhysicsGameComponent pc = new PhysicsGameComponent(p);
        //pc.setCollider(col);
        //p.setPhysicsComponent(pc);

        //reference.getScene().addGameObject(p);

        return Collider.doesCollide(col, reference.getScene().getPhysicsComponentsTypePlayer());
    }

    private double getDirection() {
        return reference.getTransform().getXScale();
    }

    @Override
    public void tick() {
        updateTimers();
    }

    public void updateTimers() {
        punchTimer.tick();
        kickTimer.tick();
        coolDownTimer.tick();
        hitComboTimer.tick();
        shootTimer.tick();
    }

    @Override
    public void onCollision(Collider c1, Collider c2) {

    }

    @Override
    public void onTimerStops(String timerName) {
        switch (timerName) {
            case "punchTimer":
                doPunchHit();
                handleTimer(punchTimer);
                break;
            case "kickTimer":
                doKickHit();
                handleTimer(kickTimer);
                break;
            case "coolDownTimer":
                handleTimer(coolDownTimer);
                hasReloaded = true;
                break;
            case "shootTimer":
                shootProjectile();
                handleTimer(shootTimer);
                break;
            case "hitCombo":
                hitCombo = 0;
                break;
        }
    }

    private void handleTimer(Timer timer) {
        timer.resetTimer();
        timer.pause();
    }

    public boolean hasReloaded() {
        return hasReloaded;
    }
}
