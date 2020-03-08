package graphics;

import display.Canvas;
import game.GameWorld;
import logic.AnimationTypes;
import logic.PlayerTypes;
import player.Player;
import player.PlayerState;

public class AnimationManager {

    private Player playerRef;
    private PlayerState lastPlayerState = PlayerState.Default;

    private Animation fightAnim;
    private Animation defaultAnim;
    private Animation specialAttackAnim;
    private Animation runAnim;

    private Animation currentAnim;

    public AnimationManager(PlayerTypes type, Player player) {
        this.playerRef = player;

        defaultAnim = AnimationLoader.loadAnimation(type, AnimationTypes.Default, player);
        fightAnim = AnimationLoader.loadAnimation(type, AnimationTypes.Fight, player);
        specialAttackAnim = AnimationLoader.loadAnimation(type, AnimationTypes.SpecialAttack, player);
        runAnim = AnimationLoader.loadAnimation(type, AnimationTypes.Run, player);
    }

    public void runAnimation(GameWorld g, double dt, PlayerState playerState) {

        if (playerState!=lastPlayerState) {
            endAllAnimations(g);
            changeAnim(playerState);
            lastPlayerState = playerState;
        }

        if (currentAnim==null) {
            currentAnim = defaultAnim;
        }

        currentAnim.playAnimation(g, dt);
    }

    private void changeAnim(PlayerState playerState) {
        if (playerState==PlayerState.Default) { currentAnim = defaultAnim; }
        else if (playerState==PlayerState.WalkRight) { currentAnim = runAnim; }
        else if (playerState==PlayerState.Jump) { currentAnim = defaultAnim; }
        //Todo more types of animation
    }

    private void endAllAnimations(GameWorld g) {
        defaultAnim.endAnimation(g);
        fightAnim.endAnimation(g);
        specialAttackAnim.endAnimation(g);
        runAnim.endAnimation(g);
    }

}
