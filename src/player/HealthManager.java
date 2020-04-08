package player;

import game.GameObject;

public class HealthManager {

    private GameObject ref;

    private double regBonus = 0.1; // health per second

    private double maxHealth;
    private double currentHealth;

    //Todo: implement stamina
    private double maxStamina;
    private double currentStamina;

    public HealthManager(GameObject ref, double maxHealth) {
        this.ref = ref;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void takeDamage(double damage) {
        currentHealth -= damage;
    }

    public void tick(double dt) {
        regenerate(dt);
    }

    private void regenerate(double dt) {
        if (currentHealth < maxHealth && currentHealth > 0) {
            this.currentHealth = Math.min(currentHealth + regBonus*dt, maxHealth);
        }
    }

    public double getHealth() {
        return currentHealth;
    }
}
