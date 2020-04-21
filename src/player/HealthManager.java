package player;

import game.ComponentType;
import game.GameComponent;
import game.GameObject;

public class HealthManager extends GameComponent {

    private final double regBonus = 0.1; // health per second

    private final double maxHealth;
    private double currentHealth;

    //Todo: implement stamina
    private double maxStamina;
    private double currentStamina;

    public HealthManager(GameObject ref, double maxHealth) {
        super(ref, ComponentType.Logic);

        if (! (ref instanceof Player)) { throw new Error("GameObject has to be of type Player"); }

        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void takeDamage(double damage) {
        currentHealth -= damage;

        if (currentHealth <= 0) {
            ((Player)reference).onDeath();
        }
    }

    public void tick() {
        double deltaTime = reference.getTime().getDeltaTime();
        regenerate(deltaTime);
    }

    private void regenerate(double dt) {
        if (currentHealth < maxHealth && currentHealth > 0) {
            this.currentHealth = Math.min(currentHealth + regBonus * dt, maxHealth);
        }
    }

    public double getHealth() {
        return currentHealth;
    }

    public double getHealthPercentage() {
        return this.currentHealth/this.maxHealth;
    }
}
