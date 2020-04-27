package player.controller;

import gameobjects.GameComponent;
import gameobjects.GameObject;
import input.PlayerInputManager;
import input.PlayerKeyListener;
import player.Player;
import player.PlayerSide;

public class HumanController extends PlayerController implements PlayerKeyListener {

    public HumanController(Player p, PlayerSide side, GameObject gameHandler) {
        super(p);

        switch (side) {
            case LEFT -> ((PlayerInputManager)gameHandler.getComponent(PlayerInputManager.class)).setListenerMapping1(this);
            case RIGHT -> ((PlayerInputManager)gameHandler.getComponent(PlayerInputManager.class)).setListenerMapping2(this);
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
