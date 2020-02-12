package player;

import input.PlayerKeyListener;
import logic.PlayerTypes;
import logic.Vector2;

public class HumanPlayer extends Player implements PlayerKeyListener {

    public HumanPlayer(Vector2 pos, PlayerTypes type) {
        super(pos, type);
    }

    @Override
    public void keyRight() {
        this.getPlayerPhysics().setVelocityX(300);
    }

    @Override
    public void keyLeft() {
        this.getPlayerPhysics().setVelocityX(-300);
    }

    // jumping
    @Override
    public void keyUp() {
        this.getPlayerPhysics().setVelocityY(-500);
    }
}
