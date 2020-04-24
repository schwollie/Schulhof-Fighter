package player;

import input.PlayerKeyListener;
import logic.PlayerType;
import logic.Vector2;
import scenes.Scene;

public class HumanPlayer extends Player implements PlayerKeyListener {

    public HumanPlayer(Scene world, Vector2 pos, PlayerType type, String tag) {
        super(world, pos, type, tag);
    }

    @Override
    public void keyRight() {
        if (canMove) {
            this.walk(1);
        }
    }

    @Override
    public void keyLeft() {
        if (canMove) {
            this.walk(-1);
        }
    }

    // jumping
    @Override
    public void keyUp() {
        if (canMove) {
            this.Jump();
        }
    }

    @Override
    public void keyKick() {
        this.kick();
        //System.out.println("kick");
    }

    @Override
    public void keyPunch() {
        this.punch();
    }

    @Override
    public void keyBlock() {
        System.out.println("block");
    }

    @Override
    public void keyProjectile() {
        this.shootProjectile();
    }
}
