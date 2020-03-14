package player;

import game.GameObject;
import physics.Collider;

public class AttackManager {

    private GameObject ref;

    private Collider atBoxNormal;
    private Collider atBoxLow;

    public AttackManager(GameObject g) {
        ref = g;
    }

    public void lowAttack() {}

    public void dAttack() {}

    public void dblock() {}

    public void lowBlock() {}

}
