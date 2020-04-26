package player.controller;

import input.PlayerKeyListener;
import player.Player;
import player.PlayerSide;

public class HumanController extends PlayerController implements PlayerKeyListener {

    public HumanController(Player p, PlayerSide side) {
        super(p);

        switch (side) {
            case LEFT -> this.player.getScene().getInputManager().setListenerMapping1(this);
            case RIGHT -> this.player.getScene().getInputManager().setListenerMapping2(this);
        }

    }

    @Override
    public void keyRight() {
        this.walkRight();
    }

    @Override
    public void keyLeft() {
        this.walkLeft();
    }

    @Override
    public void keyUp() {
        this.jump();
    }

    @Override
    public void keyKick() {
        this.kick();
    }

    @Override
    public void keyPunch() {
        this.punch();
    }

    @Override
    public void keyBlock() {
        this.block();
    }

    @Override
    public void keyProjectile() {
        this.shootProjectile();
    }
}
