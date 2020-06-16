package player.utils;

import game.Game;
import gameobjects.ComponentType;
import gameobjects.GameComponent;
import gameobjects.GameObject;
import player.Player;

public class StatsManager extends GameComponent {

    private final double regBonus = .1; // health per second
    private final double regStamina = 1;
    private final double damageStamina = 5, attackStamina = 10;

    private final double maxHealth;
    private double currentHealth;

    private double maxStamina;
    private double currentStamina;

    private double maxPower;
    private double currentPower;

    public StatsManager(GameObject ref, double maxHealth, double maxStamina, double maxPower) {
        super(ref, ComponentType.Logic);

        if (!(ref instanceof Player)) {
            throw new Error("GameObject has to be of type Player");
        }

        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.maxStamina = maxStamina;
        this.currentStamina = 0;
        this.maxPower = maxPower;
        this.currentPower = 0;
    }

    public void takeDamage(double damage, boolean isBlocking) {
        if (isBlocking) {
            damage *= 0.2;
            currentStamina -= 1;
        }
        currentHealth -= damage;

        if (currentHealth <= 0) {
            ((Player) reference).onDeath();
        }
        increaseStamina(damageStamina);
    }

    public void increaseStamina(double amount) {
        currentStamina += amount;
        currentStamina = Math.min(currentStamina, maxStamina);
    }

    public void increasePower(double amount) {
        currentPower += amount;
        currentPower = Math.min(currentPower, maxPower);
    }

    public void tick() {
        double deltaTime = Game.timeManager.getDeltaTime();
        regenerate(deltaTime);
    }

    private void regenerate(double dt) {
        if (currentHealth < maxHealth && currentHealth > 0) {
            this.currentHealth = Math.min(currentHealth + regBonus * dt, maxHealth);
        }

        if (currentStamina < maxStamina && currentStamina >= 0) {
            this.currentStamina = Math.min(currentStamina + regStamina * dt, maxStamina);
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

    public double getPower() {
        return currentPower;
    }

    public double getPowerPercentage() {
        return currentPower / maxPower;
    }
}
