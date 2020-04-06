package player;

import game.GameObject;
import logic.Vector2;
import physics.*;

public class AttackManager implements CollissionListener {

    private GameObject ref;

    private Collider atBoxNormal;
    private Collider atBoxLow;

    public AttackManager(GameObject g) {
        ref = g;
    }

    public void lowAttack() {

    }

    public void dAttack() {}

    public void dblock() {}

    public void lowBlock() {}

    @Override
    public void onCollision(Collider c1, Collider c2) {

    }
}
