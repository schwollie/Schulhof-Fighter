package player.utils.attacks;

import display.Camera;
import gameobjects.ComponentType;
import gameobjects.GameComponent;
import gameobjects.GameObject;
import logic.Vector2;
import player.Player;
import time.TimeEventListener;
import time.Timer;

public class Attack extends GameComponent implements TimeEventListener {

    protected static final String coolDownTimerName = "coolDownTimer";
    protected static final String hitDelayTimerName = "hitDelayTimer";

    protected boolean doCameraShake;

    protected Vector2 range;
    protected Vector2 force;

    protected Timer coolDownTimer;
    protected Timer hitDelayTimer;
    protected boolean cooledDown = false;

    protected double damage;

    protected AttackManager attackManager;

    public Attack(GameObject reference, AttackManager attackManager, Vector2 force, Vector2 range, double damage,
                  double coolDownTime, double hitDelayTime, boolean doCameraShake) {

        super(reference, ComponentType.Attack);
        assert (reference instanceof Player) : "Reference has to be of type Player!";

        this.force = force;
        this.range = range;

        this.coolDownTimer = new Timer(reference, coolDownTimerName, coolDownTime, this);
        this.coolDownTimer.resume();
        this.hitDelayTimer = new Timer(reference, hitDelayTimerName, hitDelayTime, this);
        this.hitDelayTimer.pause();

        this.damage = damage;
        this.doCameraShake = doCameraShake;

        this.attackManager = attackManager;
    }

    @Override
    public void tick() {
        coolDownTimer.tick();
        hitDelayTimer.tick();
    }

    // region attack preparation
    public void attack() {
        prepareAttack();
    }

    protected void prepareAttack() {
        cooledDown = false;
        ((Player) reference).setCanMove(false);
        resetDelayTimer();
        resetCooldownTimer();
    }
    // endregion

    // region attack logic:
    protected void handleAttack() {
        onAttackStart();
        onAttack();
        onAttackEnd();
    }

    protected void onAttackStart() {
        if (doCameraShake) {
            ((Camera) reference.getScene().getCam()).shake(new Vector2(0.02, 0.02), new Vector2(2 * damage, 2), .4);
        }
    }

    protected void onAttack() {
        attackManager.doAttack(this.range, Vector2.zero, this.damage, this.force);
    }

    protected void onAttackEnd() {
        ((Player) reference).setCanMove(true);
        coolDownTimer.resume();
    }
    // endregion

    public boolean cooledDown() {
        return cooledDown;
    }

    @Override
    public void onTimerStops(String timerName) {
        if (timerName.equals(coolDownTimerName)) {
            cooledDown = true;
            resetCooldownTimer();
            coolDownTimer.pause();
        } else if (timerName.equals(hitDelayTimerName)) {
            resetDelayTimer();
            handleAttack();
            hitDelayTimer.pause();
        }
    }

    protected void resetCooldownTimer() {
        coolDownTimer.resetTimer();
        coolDownTimer.resume();
    }

    protected void resetDelayTimer() {
        hitDelayTimer.resetTimer();
        hitDelayTimer.resume();
    }
}
