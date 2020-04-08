package player;

import display.Canvas;
import game.GameWorld;
import graphics.AnimationManager;
import logic.PlayerTypes;
import logic.Transform;
import logic.Vector2;

public class VisualPlayer {

    private Player playerRef;
    private AnimationManager animManager;
    private Transform transform;
    private Vector2 spriteOffset;

    public VisualPlayer(PlayerTypes type, Player player) {
        animManager = new AnimationManager(type, player);
        this.playerRef = player;
        this.transform = player.getTransform();
        this.spriteOffset = new Vector2(-1, -1); // pivot point is on center
    }

    public void updatePlayer(GameWorld g, double dt) {
        animManager.runAnimation(g, dt);
    }

    public void setState(PlayerState state) {
        animManager.setState(state);
    }
}
