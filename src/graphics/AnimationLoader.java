package graphics;

import game.Consts;
import logic.AnimationTypes;
import logic.PlayerTypes;
import logic.Transform;
import player.Player;

public abstract class AnimationLoader {

    public static Animation loadAnimation(PlayerTypes type, AnimationTypes animType, Player player) {
        String path = AnimationLoader.getPath(type, animType);
        // Todo: somehow we have to get the right width/height and picture Count for every animation
        return Animation.loadAnim(path, 409, 409, 10, player);
    }

    private static String getPath(PlayerTypes type, AnimationTypes animType) {
        return Consts.imageSrc + "test.png";
    }

}
