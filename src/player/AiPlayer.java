package player;

import game.Scene;
import logic.PlayerType;
import logic.Vector2;

public class AiPlayer extends Player {
    public AiPlayer(Scene world, Vector2 pos, PlayerType type, String tag) {
        super(world, pos, type, tag);
    }
}
