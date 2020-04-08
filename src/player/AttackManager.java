package player;

import game.GameObject;
import game.GameWorld;
import logic.Vector2;
import physics.*;
import time.TimeEventListener;

public class AttackManager implements CollissionListener, TimeEventListener {

    private GameObject ref;

    private Collider atBoxNormal;
    private Collider atBoxLow;

    // flags:
    private boolean isBlocking = false;

    // timers


    public AttackManager(GameObject g) {
        ref = g;
    }

    public void doKick() {
        double directionFactor = ref.getTransform().getXScale();
        Vector2 pos = ref.getTransform().getPosition().add( new Vector2(1*directionFactor*0.3,0.2) );
        Collider[] cs = Collider.doesCollide(Collider.getPointCollider(pos), ref.getGameWorld().physicsObjects);

        for (Collider c: cs) {
            if (c.getGameObject() instanceof Player) {
                Player other = (Player)c.getGameObject();
                other.takeDamage(2, new Vector2(200, 8000));
            }
        }
    }

    public void tick(GameWorld g, double deltaTime) {

    }

    @Override
    public void onCollision(Collider c1, Collider c2) {

    }

    @Override
    public void onTimerStops(String timerName) {

    }
}
