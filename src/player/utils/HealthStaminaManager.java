package player.utils;

import gameobjects.ComponentType;
import gameobjects.GameComponent;
import gameobjects.GameObject;
import player.Player;

public class HealthStaminaManager extends GameComponent {

    private final double regBonus = 0.1; // health per second
    private final double damageStamina = 5, attackStamina = 10;

    private final double maxHealth;
    private double currentHealth;

    //Todo: implement stamina
    private double maxStamina;
    private double currentStamina;

    public HealthStaminaManager(GameObject ref, double maxHealth, double maxStamina) {
        super(ref, ComponentType.Logic);

        if (!(ref instanceof Player)) {
            throw new Error("GameObject has to be of type Player");
        }

        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.maxStamina = maxStamina;
        this.currentStamina = 50;
    }

    public void takeDamage(double damage) {
        currentHealth -= damage;

        if (currentHealth <= 0) {
            ((Player) reference).onDeath();
        }
        increaseStamina(damageStamina);
    }

    public void increaseStamina(double amount) {
        currentStamina += amount;
        currentStamina = currentStamina <= maxStamina ? currentStamina : maxStamina;
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
        return currentHealth / maxHealth;
    }

    public double getStamina() {
        return currentStamina;
    }

    public double getAttackStamina() {
        return attackStamina;
    }

    public double getStaminaPercentage() {
        return currentStamina / maxStamina;
    }
}