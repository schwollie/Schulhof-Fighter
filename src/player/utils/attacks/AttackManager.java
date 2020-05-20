package player.utils.attacks;

import gameobjects.ComponentType;
import gameobjects.GameComponent;
import gameobjects.GameObject;
import logic.Dimension2D;
import logic.Vector2;
import physics.Collider;
import physics.RectCollider;
import player.Player;
import time.TimeEventListener;
import time.Timer;

import java.util.ArrayList;

public class AttackManager extends GameComponent implements TimeEventListener {

    // flags:
    private final boolean isBlocking = false;

    // timers
    private final Timer hitComboTimer = new Timer(this.reference, "hitCombo", 1.5);

    // Basic attacks
    private ArrayList<Attack> attacks = new ArrayList<>();

    private Attack kick = new Attack(reference, this, new Vector2(2, 1), new Vector2(0.5, 2), 1,
            .5, 0.25, true);

    private Attack punch = new Attack(reference, this, new Vector2(2, 1), new Vector2(0.5, 2), 1,
            .5, 0.25, true);

    private ProjectileAttack shoot = new ProjectileAttack(reference, this, new Vector2(2, 1), new Vector2(0.5, 2), 1,
            .5, 0.3, true);

    private int hitCombo = 0;

    public AttackManager(GameObject ref) {
        super(ref, ComponentType.Logic);
        assert (ref instanceof Player) : "Reference has to be of type Player!";

        initTimers();
        init();
    }

    private void init() {
        attacks.add(kick);
        attacks.add(punch);
        attacks.add(shoot);
    }

    private void initTimers() {
        hitComboTimer.addListener(this);
        hitComboTimer.pause();
    }

    public void block() {

    }

    public void shoot() {

        shoot.attack();

    }

    public void doKick() {
        kick.attack();
    }

    public void doPunch() {
        punch.attack();
    }

    public void doAttack(Vector2 range, Vector2 offset, double damage, Vector2 force) {
        Collider[] cs = getCollidersInRange(range, offset);
        force = testForComboHits(force);
        Player player = ((Player) reference);
        dealDamageToColliders(cs, damage, force, player);

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

    public void receiveHit(double damage, Vector2 force, GameObject sender) {
        ((Player) reference).takeDamage(damage, new Vector2(sender.getTransform().getXScale() * force.getX(), force.getY()), sender);
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

        return Collider.doesCollide(col, reference.getScene().getPhysicsComponentsTypePlayer());
    }

    public double getDirection() {
        return reference.getTransform().getXScale();
    }

    @Override
    public void tick() {
        updateTimers();
        attacks.forEach(attack -> attack.tick());
    }

    public void updateTimers() {
        hitComboTimer.tick();
    }

    public boolean canAttack() {
        for (Attack t : attacks) {
            if (!t.cooledDown()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void onTimerStops(String timerName) {
        if (timerName.equals("hitCombo")) {
            hitCombo = 0;
        }
    }
}
