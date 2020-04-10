package player;

import game.ComponentType;
import game.GameComponent;
import game.GameObject;
import logic.Dimension2D;
import logic.Shaker;
import logic.Vector2;
import physics.Collider;
import physics.CollissionListener;
import physics.PhysicsGameComponent;
import physics.RectCollider;
import time.TimeEventListener;
import time.Timer;

public class AttackManager extends GameComponent implements CollissionListener, TimeEventListener {

    private Collider atBoxNormal;
    private Collider atBoxLow;

    // flags:
    private final boolean isBlocking = false;

    // timers
    private final Timer punchTimer = new Timer("punchTimer", 0.25);
    private final Timer kickTimer = new Timer("kickTimer", 0.25);


    public AttackManager(GameObject g) {
        super(g, ComponentType.Logic);
        punchTimer.addListener(this);
        kickTimer.addListener(this);
        punchTimer.pause();
        kickTimer.pause();
    }

    public void doKick() {
        kickTimer.resume();
    }

    public void doKickHit() {
        doAttack(new Vector2(0.5, 0.2), Vector2.zero, 1, new Vector2(200, -40));
    }

    public void doPunch() {
        punchTimer.resume();
    }

    public void doPunchHit() {
        doAttack(new Vector2(0.5, 0.2), Vector2.zero, 1, new Vector2(200, -40));
    }

    private void doAttack(Vector2 range, Vector2 offset, double damage, Vector2 force) {
        Collider[] cs = getCollidersInRange(range, offset);

        for (Collider c : cs) {
            if (c.getGameObject() instanceof Player && !c.getGameObject().equals(reference)) {
                Player other = (Player) c.getGameObject();
                other.takeDamage(damage, new Vector2(getDirection() * force.getX(), force.getY()));
            }
        }
    }

    private Collider[] getCollidersInRange(Vector2 range, Vector2 _offset) {
        double directionFactor = getDirection();
        Vector2 offset = new Vector2(_offset.getX()*directionFactor, _offset.getY());

        GameObject p = GameObject.getPlaceHolder( reference.getTransform(), reference.getScene());
        RectCollider col = new RectCollider(p, offset, new Dimension2D(range.getX() * directionFactor, range.getY()));

        PhysicsGameComponent pc = new PhysicsGameComponent(p);
        pc.setCollider(col);
        p.setPhysicsComponent(pc);

        //reference.getScene().addGameObject(p);

        return Collider.doesCollide(col, reference.getScene().getPhysicsComponentsTypePlayer());
    }

    private double getDirection() {
        return reference.getTransform().getXScale();
    }

    @Override
    public void tick() {
        double dt = reference.getTime().getDeltaTime();
        punchTimer.tick(dt);
        kickTimer.tick(dt);
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
        }
    }
}
