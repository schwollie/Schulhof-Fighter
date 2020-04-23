package player;

import game.ComponentType;
import game.GameComponent;
import game.GameObject;
import logic.Dimension2D;
import logic.Transform;
import logic.Vector2;
import logic.XRange;
import particle.ParticleSystem;
import physics.Collider;
import physics.CollissionListener;
import physics.RectCollider;
import time.TimeEventListener;
import time.Timer;

public class AttackManager extends GameComponent implements CollissionListener, TimeEventListener {

    // flags:
    private final boolean isBlocking = false;
    private final Timer coolDownTimer = new Timer(this.reference, "coolDownTimer", 0.2);

    // timers
    private final Timer punchTimer = new Timer(this.reference, "punchTimer", 0.25);
    private final Timer kickTimer = new Timer(this.reference, "kickTimer", 0.25);
    private boolean hasReloaded = true;


    public AttackManager(GameObject ref) {
        super(ref, ComponentType.Logic);

        if (!(ref instanceof Player)) {
            throw new Error("GameObject has to be of type Player");
        }

        punchTimer.addListener(this);
        kickTimer.addListener(this);
        coolDownTimer.addListener(this);
        punchTimer.pause();
        kickTimer.pause();
        coolDownTimer.pause();
    }

    public void block() {

    }

    public void shootProjectile() {
        if (hasReloaded) {
            Projectile p = new Projectile("p1", reference.getScene(), reference.getTransform().getPosition(), getDirection() * 1.5);
            reference.getScene().addGameObjectNow(p);
            hasReloaded = false;
            coolDownTimer.resume();
        }
    }

    public void doKick() {
        kickTimer.resume();
    }

    public void doKickHit() {
        doAttack(new Vector2(0.5, 0.2), Vector2.zero, 1, new Vector2(200, 100));
    }

    public void doPunch() {
        punchTimer.resume();
    }

    public void doPunchHit() {
        doAttack(new Vector2(0.5, 0.2), Vector2.zero, 1, new Vector2(200, 100));
    }

    private void doAttack(Vector2 range, Vector2 offset, double damage, Vector2 force) {
        Collider[] cs = getCollidersInRange(range, offset);

        for (Collider c : cs) {
            if (c.getGameObject() instanceof Player && !c.getGameObject().equals(reference)) {
                Player other = (Player) c.getGameObject();
                other.takeDamage(damage, new Vector2(getDirection() * force.getX(), force.getY()));
            }
        }

        /*ParticleSystem p = new ParticleSystem(reference, new XRange(0.5, 2), 1, new XRange(100, 100));
        p.setRelativeTransform(new Transform(new Vector2(.5*getDirection(),0)));
        p.setRelativeTransform(p.getRelativeTransform().setGetScale(new Vector2(0.5, 0.5)));
        p.setLiveTime(new XRange(0.1, 1));
        p.setStartForce(new XRange(1, 5));
        p.setGravityFactor(new XRange(0.1, 0.2));
        p.start();
        reference.addComponent(p);*/
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
        punchTimer.tick();
        kickTimer.tick();
    }

    @Override
    public void onCollision(Collider c1, Collider c2) {

    }

    @Override
    public void onTimerStops(String timerName) {
        if (timerName.equals("punchTimer")) {
            doPunchHit();
            punchTimer.resetTimer();
            punchTimer.pause();
        } else if (timerName.equals("kickTimer")) {
            doKickHit();
            kickTimer.resetTimer();
            kickTimer.pause();
        } else if (timerName.equals("coolDownTimer")) {
            hasReloaded = true;
            coolDownTimer.resetTimer();
            coolDownTimer.pause();
        }
    }
}
