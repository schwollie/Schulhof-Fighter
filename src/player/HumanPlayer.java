package player;

import input.PlayerKeyListener;
import logic.PlayerTypes;
import logic.Vector2;

public class HumanPlayer extends Player implements PlayerKeyListener {

    public HumanPlayer(Vector2 pos, PlayerTypes type, String tag) {
        super(pos, type, tag);
    }

    @Override
    public void keyRight() {
        this.walkRight();
    }

    @Override
    public void keyLeft() {
        this.walkLeft();
    }

    // jumping
    @Override
    public void keyUp() {
        this.Jump();
    }
}
