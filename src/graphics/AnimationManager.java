package graphics;

import game.GameWorld;
import logic.AnimationTypes;
import logic.PlayerTypes;
import logic.Queue;
import player.Player;
import player.PlayerState;

public class AnimationManager {

    private Player playerRef;

    private Animation kickAnim;
    private Animation punchAnim;
    private Animation defaultAnim;
    private Animation specialAttackAnim;
    private Animation runAnim;
    private Animation jumpAnim;
    private Animation blockAnim;

    private Animation currentAnim;
    private Animation lastAnim;

    public AnimationManager(PlayerTypes type, Player player) {
        this.playerRef = player;

        defaultAnim = AnimationLoader.loadAnimation(type, AnimationTypes.Default, player, true, 1);
        //  when no certain Animation is set this will be played

        kickAnim = AnimationLoader.loadAnimation(type, AnimationTypes.Kick, player, false, 4);
        punchAnim = AnimationLoader.loadAnimation(type, AnimationTypes.Punch, player, false, 4);
        specialAttackAnim = AnimationLoader.loadAnimation(type, AnimationTypes.SpecialAttack, player, false, 5);
        blockAnim = AnimationLoader.loadAnimation(type, AnimationTypes.Block, player, true, 4);
        runAnim = AnimationLoader.loadAnimation(type, AnimationTypes.Run, player, true, 2);
        jumpAnim = AnimationLoader.loadAnimation(type, AnimationTypes.Jump, player, true, 2);

        currentAnim = defaultAnim;
        lastAnim = defaultAnim;
    }

    public void setState(PlayerState playerState) {

        Animation newAnim = getAnim(playerState);

        if (currentAnim==null || currentAnim.getPriority() < newAnim.getPriority()) {
            currentAnim = newAnim;
        }
    }

    public void runAnimation(GameWorld g, double dt) {
        manageAnimations(g, dt);
    }

    private void manageAnimations(GameWorld g, double dt) {
        if (lastAnim!=currentAnim) {
            endAllAnimations(g);
        }

        if (currentAnim==null) {
            currentAnim = getAnim(PlayerState.Default);
        }

        currentAnim.playAnimation(g, dt);

        lastAnim = currentAnim;

        if (currentAnim.isLoopAnim() || currentAnim.hasFinished()) {
            currentAnim = null;
        }
    }

    private Animation getAnim(PlayerState playerState) {
        if (playerState==PlayerState.Default) { return defaultAnim; }
        else if (playerState==PlayerState.Walk) { return runAnim; }
        else if (playerState==PlayerState.Jump) { return jumpAnim; }
        else if (playerState==PlayerState.Kick) { return kickAnim; }
        throw new Error("No matching Animation found");
    }

    private void endAllAnimations(GameWorld g) {
        defaultAnim.endAnimation(g);
        kickAnim.endAnimation(g);
        punchAnim.endAnimation(g);
        blockAnim.endAnimation(g);
        specialAttackAnim.endAnimation(g);
        runAnim.endAnimation(g);
        jumpAnim.endAnimation(g);
    }

}
