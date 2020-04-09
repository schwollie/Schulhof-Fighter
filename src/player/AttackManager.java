package player;

import game.GameComponent;
import game.ComponentType;
import game.GameObject;
import logic.Shaker;
import logic.Vector2;
import physics.*;
import time.TimeEventListener;
import time.Timer;

public class AttackManager extends GameComponent implements CollissionListener, TimeEventListener {

    private Collider atBoxNormal;
    private Collider atBoxLow;

    // flags:
    private boolean isBlocking = false;

    // timers
    private Timer punchTimer = new Timer("punchTimer", 0.25);


    public AttackManager(GameObject g) {
        super(g, ComponentType.Logic);
        punchTimer.addListener(this);
    }

    public void doKick() {
        Collider[] cs = getCollidersInRange(0.3, 0.2);

        for (Collider c: cs) {
            if (c.getGameObject() instanceof Player) {
                Player other = (Player)c.getGameObject();
                other.takeDamage(2, new Vector2(getDirection()*200, 4000));
            }
        }
    }

    private Collider[] getCollidersInRange(double rangeX, double rangeY){
        double directionFactor = getDirection();
        Vector2 pos = reference.getTransform().getPosition().add( new Vector2(directionFactor*rangeX, rangeY));
        return Collider.doesCollide(Collider.getPointCollider(pos), reference.getScene().getPhysicsComponentsTypePlayer());
    }
    private double getDirection(){
        return reference.getTransform().getXScale();
    }

    public void doPunch() {
        if(punchTimer.isFinished()){
            punchTimer.resetTimer();
        }
    }
    public void doPunchHit(){
        Collider[] cs = getCollidersInRange(0.6, 0.2);

        for (Collider c: cs) {
            if (c.getGameObject() instanceof Player) {
                Player other = (Player)c.getGameObject();
                other.takeDamage(1, new Vector2(getDirection()*200, 500));
                if (!other.hasComponent(Shaker.class))
                    other.addComponent(new Shaker(other, new Vector2(0.05, 0.02), new Vector2(1, 1), 1));
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
        if(timerName.equals("punchTimer")){
            doPunchHit();
        }
    }
}
