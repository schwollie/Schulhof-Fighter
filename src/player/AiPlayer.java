package player;

import game.GameWorld;
import logic.PlayerTypes;
import logic.Vector2;

public class AiPlayer extends Player {
    public AiPlayer(GameWorld world, Vector2 pos, PlayerTypes type, String tag) {
        super(world, pos, type, tag);
    }
}
