package player.controller;

import player.Player;

public abstract class PlayerController {

    protected Player player;

    public PlayerController(Player p) {
        this.player = p;
    }

    public void walkRight() {
        player.walk(1);
    }

    public void walkLeft() {
        player.walk(-1);
    }

    public void jump() {
        player.Jump();
    }

    public void kick() {
        player.kick();
    }

    public void punch() {
        player.punch();
    }

    public void block() {
        player.block();
    }

    public void shootProjectile() {
        player.shootProjectile();
    }

    public void specialAttack1() {
        player.special1();
    }
}
