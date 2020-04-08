package player;

import game.GameWorld;
import input.PlayerKeyListener;
import logic.PlayerTypes;
import logic.Vector2;

public class HumanPlayer extends Player implements PlayerKeyListener {

    public HumanPlayer(GameWorld world, Vector2 pos, PlayerTypes type, String tag) {
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
        System.out.println("punch");
    }

    @Override
    public void keyBlock() {
        System.out.println("block");
    }
}
