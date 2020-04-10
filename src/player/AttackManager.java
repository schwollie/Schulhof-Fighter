package player;

import game.ComponentType;
import game.GameComponent;
import game.GameObject;
import logic.Dimension2D;
import logic.Shaker;
import logic.Vector2;
import physics.Collider;
import physics.CollissionListener;
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


    public AttackManager(GameObject g) {
        super(g, ComponentType.Logic);
        punchTimer.addListener(this);
        punchTimer.pause();
    }

    public void doKick() {
        Collider[] cs = getCollidersInRange(0.2, 0.2);

        for (Collider c : cs) {
            if (c.getGameObject() instanceof Player && !c.getGameObject().equals(reference)) {
                Player other = (Player) c.getGameObject();
                other.takeDamage(2, new Vector2(getDirection() * 200, -40));
            }
        }
    }

    private Collider[] getCollidersInRange(double rangeX, double rangeY) {
        double directionFactor = getDirection();
        GameObject p = GameObject.getPlaceHolder( reference.getTransform(), reference.getScene());
        RectCollider col = new RectCollider(p, Vector2.zero, new Dimension2D(rangeX , rangeY));
        return Collider.doesCollide(col, reference.getScene().getPhysicsComponentsTypePlayer());
    }

    private double getDirection() {
        return reference.getTransform().getXScale();
    }

    public void doPunch() {
        punchTimer.resume();
    }

    public void doPunchHit() {
        Collider[] cs = getCollidersInRange(0.6, 0.2);

        for (Collider c : cs) {
            if (c.getGameObject() instanceof Player && !c.getGameObject().equals(reference)) {
                Player other = (Player) c.getGameObject();
                other.takeDamage(1, new Vector2(getDirection() * 200, 500));
            }
        }
    }

    @Override
    public void tick() {
        double dt = reference.getTime().getDeltaTime();
        punchTimer.tick(dt);
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
        }
    }
}
