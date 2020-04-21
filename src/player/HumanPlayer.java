package player;

import game.Scene;
import input.PlayerKeyListener;
import logic.PlayerType;
import logic.Vector2;

public class HumanPlayer extends Player implements PlayerKeyListener {

    public HumanPlayer(Scene world, Vector2 pos, PlayerType type, String tag) {
        super(world, pos, type, tag);
    }

    @Override
    public void keyRight() {
        this.walk(1);
    }

    @Override
    public void keyLeft() {
        this.walk(-1);
    }

    // jumping
    @Override
    public void keyUp() {
        this.Jump();
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
}
