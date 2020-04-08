package player;

import game.Scene;
import logic.PlayerTypes;
import logic.Vector2;

public class AiPlayer extends Player {
    public AiPlayer(Scene world, Vector2 pos, PlayerTypes type, String tag) {
        super(world, pos, type, tag);
    }
}
