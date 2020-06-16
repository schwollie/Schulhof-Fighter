package player.utils.attacks;

import game.Game;
import gameobjects.ComponentType;
import gameobjects.GameComponent;
import gameobjects.GameObject;
import logic.Dimension2D;
import logic.Vector2;
import physics.Collider;
import physics.RectCollider;
import player.Player;
import player.utils.StatsManager;
import time.TimeEventListener;
import time.Timer;

public class AttackManager extends GameComponent implements TimeEventListener, AttackEventListener {

    private Player player;
    private StatsManager statsManager;

    // flags:
    private boolean isBlocking = false;
    private boolean endBlock = false;
    private double staminaDrain = 10; // per second

    private final Timer hitComboTimer = new Timer(this.reference, "hitCombo", 1.5);
    private int hitCombo = 0;

    // region Basic attacks
    private AttackProfile attackProfile = AttackProfile.getDefault(reference, this);

    // endregion

    // region constructor + init
    public AttackManager(GameObject ref) {
        super(ref, ComponentType.Logic);
        assert (ref instanceof Player) : "Reference has to be of type Player!";

        player = (Player) ref;
        statsManager = player.getStatsManager();

        initTimers();
    }

    private void initTimers() {
        hitComboTimer.addListener(this);
        hitComboTimer.pause();
    }
    // endregion

    // region attack methods
    public void block() {
        endBlock = false;
        isBlocking = true;
        player.setCanMove(false);
    }

    public void shoot() {
        attackProfile.getProjectileAttack().attack();
        statsManager.increasePower(5);
    }

    public void doKick() {
        attackProfile.getKick().attack();
        statsManager.increasePower(4);
    }

    public void doPunch() {
        attackProfile.getPunch().attack();
        statsManager.increasePower(4);
    }

    public void doSpecial1() {
        attackProfile.getSpecialAttack1().attack();
        statsManager.increasePower(-30);
    }

    public void doAttack(Vector2 range, Vector2 offset, double damage, Vector2 force) {
        Collider[] cs = getCollidersInRange(range, offset);
        force = testForComboHits(force);
        Player player = ((Player) reference);
        dealDamageToColliders(cs, damage, force, player);
    }

    // endregion

    // region combo + damage model + colliders
    private void dealDamageToColliders(Collider[] cs, double damage, Vector2 force, Player attacker) {
        for (Collider c : cs) {
            if (c.getGameObject() instanceof Player && !c.getGameObject().equals(reference)) {
                Player other = (Player) c.getGameObject();
                other.takeDamage(damage, new Vector2(getDirection() * force.getX(), force.getY()), reference);
                hitCombo++;
                attacker.getStatsManager().increaseStamina(attacker.getStatsManager().getAttackStamina());
            }
        }
    }

    private Vector2 testForComboHits(Vector2 force) {
        if (hitCombo >= 2) { //3 hits
            hitCombo = 0;
            force = force.scalarMult(4);
        } else {
            hitComboTimer.resetTimer();
        }
        return force;
    }

    private Collider[] getCollidersInRange(Vector2 range, Vector2 _offset) {
        double directionFactor = getDirection();
        Vector2 offset = new Vector2(_offset.getX() * directionFactor, _offset.getY());

        GameObject p = GameObject.getPlaceHolder(reference.getTransform(), reference.getScene());
        RectCollider col = new RectCollider(p, offset, new Dimension2D(range.getX() * directionFactor, range.getY()));

        return Collider.doesCollide(col, reference.getScene().getPhysicsComponentsTypePlayer());
    }
    // endregion

    //region tick & timer logic:
    @Override
    public void tick() {
        updateTimers();
        attackProfile.attackTick();
        manageBlock();
    }

    private void manageBlock() {
        if (isBlocking) {
            player.getStatsManager().increaseStamina(-staminaDrain * Game.timeManager.getDeltaTime());
        }

        if (endBlock) {
            player.setCanMove(true);
            isBlocking = false;
            endBlock = false;
        }

        endBlock = true;
    }

    private void updateTimers() {
        hitComboTimer.tick();
    }

    @Override
    public void onTimerStops(String timerName) {
        if (timerName.equals("hitCombo")) {
            hitCombo = 0;
        }
    }

    @Override
    public void onDamage(double damage) {
        if (isBlocking) {
            hitCombo += 1;
        } else {
            hitCombo = 0;
        }
    }

    //endregion

    // region getters + setters
    public boolean canAttack() {
        for (Attack t : attackProfile.getAttacks()) {
            if (!t.cooledDown()) {
                return false;
            }
        }

        return true;
    }

    public boolean canBlock() {
        return statsManager.getStamina() > attackProfile.getBlockStaminaDrain();
    }

    public boolean canDoSpecialAttack() {
        return statsManager.getPower() > 30;
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    public double getDirection() {
        return reference.getTransform().getXScale();
    }
    //endregion
}
