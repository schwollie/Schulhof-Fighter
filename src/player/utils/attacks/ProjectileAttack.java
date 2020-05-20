package player.utils.attacks;

import gameobjects.GameObject;
import logic.Vector2;
import player.utils.Projectile;

public class ProjectileAttack extends Attack {

    private Projectile projectile;

    public ProjectileAttack(GameObject reference, AttackManager attackManager, Vector2 force, Vector2 range,
                            double damage, double coolDownTime, double hitDelayTime, boolean doCameraShake) {

        super(reference, attackManager, force, range, damage, coolDownTime, hitDelayTime, doCameraShake);
    }


    @Override
    protected void onAttack() {
        shootProjectile();
    }

    public void shootProjectile() {
        double dir = attackManager.getDirection();
        Projectile p =
                new Projectile(reference.getScene(), reference, reference.getTransform().getPosition(), dir * 1.5, dir);
        reference.getScene().addGameObject(p);
    }
}
