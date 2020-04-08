package player;

import game.Component;
import game.ComponentType;
import game.GameObject;
import game.Scene;
import logic.Vector2;
import physics.*;
import time.TimeEventListener;

public class AttackManager extends Component implements CollissionListener, TimeEventListener {

    private Collider atBoxNormal;
    private Collider atBoxLow;

    // flags:
    private boolean isBlocking = false;

    // timers


    public AttackManager(GameObject g) {
        super(g, ComponentType.Logic);
    }

    public void doKick() {
        double directionFactor = reference.getTransform().getXScale();
        Vector2 pos = reference.getTransform().getPosition().add( new Vector2(1*directionFactor*0.3,0.2) );
        Collider[] cs = Collider.doesCollide(Collider.getPointCollider(pos), reference.getScene().getPhysicsComponents());

        for (Collider c: cs) {
            if (c.getGameObject() instanceof Player) {
                Player other = (Player)c.getGameObject();
                other.takeDamage(2, new Vector2(200, 8000));
            }
        }
    }

    public void doPunch() {
        //Todo:
    }

    @Override
    public void tick(double deltaTime) {

    }

    @Override
    public void onCollision(Collider c1, Collider c2) {

    }

    @Override
    public void onTimerStops(String timerName) {

    }
}
