package player;

import display.Canvas;
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
    }

    public void updatePlayer(Canvas c, double dt) {
        animManager.runDefaultAnim(c, dt);
    }
}
