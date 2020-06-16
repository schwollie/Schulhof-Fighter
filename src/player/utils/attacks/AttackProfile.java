package player.utils.attacks;

import gameobjects.GameObject;
import logic.Vector2;

import java.util.ArrayList;
import java.util.Arrays;

public class AttackProfile {

    private ArrayList<Attack> attacks = new ArrayList<>();

    private Attack punch;
    private Attack kick;
    private ProjectileAttack projectileAttack;
    private Attack specialAttack1;
    private Attack specialAttack2;

    private double blockStaminaDrain;
    private double successfulBlockStaminaGain;

    public AttackProfile(Attack punch, Attack kick, Attack specialAttack1,
                         Attack specialAttack2, ProjectileAttack projectileAttack,
                         double blockStaminaDrain, double successfulBlockStaminaGain) {

        this.punch = punch;
        this.kick = kick;
        this.specialAttack1 = specialAttack1;
        this.specialAttack2 = specialAttack2;
        this.projectileAttack = projectileAttack;
        this.blockStaminaDrain = blockStaminaDrain;
        this.successfulBlockStaminaGain = successfulBlockStaminaGain;

        this.attacks.addAll(Arrays.asList(this.punch, this.kick, this.projectileAttack,
                this.specialAttack1, this.specialAttack1));
    }

    public void attackTick() {
        attacks.forEach(attack -> attack.tick());
    }

    public Attack getPunch() {
        return punch;
    }

    public Attack getKick() {
        return kick;
    }

    public ProjectileAttack getProjectileAttack() {
        return projectileAttack;
    }

    public Attack getSpecialAttack1() {
        return specialAttack1;
    }

    public Attack getSpecialAttack2() {
        return specialAttack2;
    }

    public double getBlockStaminaDrain() {
        return blockStaminaDrain;
    }

    public double getSuccessfulBlockStaminaGain() {
        return successfulBlockStaminaGain;
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public static AttackProfile getDefault(GameObject reference, AttackManager amg) {
        Attack kick = new Attack(reference, amg, new Vector2(2, 1), new Vector2(0.5, 2), 1,
                .5, 0.25, true);

        Attack punch = new Attack(reference, amg, new Vector2(2, 1), new Vector2(0.5, 2), 1,
                .5, 0.25, true);

        ProjectileAttack shoot = new ProjectileAttack(reference, amg, new Vector2(2, 1), new Vector2(0.5, 2), 1,
                .5, 0.3, true);

        Attack sp1 = new Attack(reference, amg, new Vector2(2, 1), new Vector2(0.5, 2), 5,
                .5, 0.25, true);

        Attack sp2 = new Attack(reference, amg, new Vector2(2, 1), new Vector2(0.5, 2), 5,
                .5, 0.25, true);

        return new AttackProfile(punch, kick, sp1, sp2, shoot, 0.1, 0.1);
    }
}
