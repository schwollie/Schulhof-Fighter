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

import java.util.ArrayList;

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
    private AttackProfile attackProfile;

    // Todo: use AttackProfile instead of writing down all attacks here
    private ArrayList<Attack> attacks = new ArrayList<>();

    private Attack kick = new Attack(reference, this, new Vector2(2, 1), new Vector2(0.5, 2), 1,
            .5, 0.25, true);

    private Attack punch = new Attack(reference, this, new Vector2(2, 1), new Vector2(0.5, 2), 1,
            .5, 0.25, true);

    private ProjectileAttack shoot = new ProjectileAttack(reference, this, new Vector2(2, 1), new Vector2(0.5, 2), 1,
            .5, 0.3, true);


    // endregion

    // region constructor + init
    public AttackManager(GameObject ref) {
        super(ref, ComponentType.Logic);
        assert (ref instanceof Player) : "Reference has to be of type Player!";

        player = (Player) ref;
        statsManager = player.getStatsManager();

        initTimers();
        init();
    }

    private void init() {
        attacks.add(kick);
        attacks.add(punch);
        attacks.add(shoot);
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
        shoot.attack();
    }

    public void doKick() {
        kick.attack();
    }

    public void doPunch() {
        punch.attack();
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
        attacks.forEach(attack -> attack.tick());
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
        for (Attack t : attacks) {
            if (!t.cooledDown()) {
                return false;
            }
        }

        return true;
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    public double getDirection() {
        return reference.getTransform().getXScale();
    }
    //endregion
}
