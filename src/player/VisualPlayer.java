package player;

import display.Canvas;
import game.GameWorld;
import graphics.AnimationManager;
import logic.PlayerTypes;
import logic.Transform;

public class VisualPlayer {

    private Player playerRef;
    private AnimationManager animManager;
    private Transform transform;

    public VisualPlayer(PlayerTypes type, Player player) {
        animManager = new AnimationManager(type, player);
        this.playerRef = player;
        this.transform = player.getTransform();
    }

    public void updatePlayer(GameWorld g, double dt, PlayerState playerState) {
        animManager.runAnimation(g, dt, playerState);
    }
}
