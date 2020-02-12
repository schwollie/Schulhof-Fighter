package graphics;

import display.Canvas;
import logic.AnimationTypes;
import logic.PlayerTypes;
import logic.Transform;
import player.Player;

import java.awt.*;

public class AnimationManager {

    private Player playerRef;

    private Animation fightAnim;
    private Animation defaultAnim;
    private Animation specialAttackAnim;
    private Animation runAnim;

    public AnimationManager(PlayerTypes type, Player player) {
        this.playerRef = player;

        defaultAnim = AnimationLoader.loadAnimation(type, AnimationTypes.Default, player);
        fightAnim = AnimationLoader.loadAnimation(type, AnimationTypes.Fight, player);
        specialAttackAnim = AnimationLoader.loadAnimation(type, AnimationTypes.SpecialAttack, player);
        runAnim = AnimationLoader.loadAnimation(type, AnimationTypes.Run, player);
    }

    public void runDefaultAnim(Canvas c, double dt) {
        defaultAnim.playAnimation(c, dt);
    }

}
