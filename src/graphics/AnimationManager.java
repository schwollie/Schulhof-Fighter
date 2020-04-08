package graphics;

import display.Camera;
import game.Component;
import game.ComponentType;
import game.GameObject;
import game.Scene;
import logic.AnimationType;
import logic.PlayerTypes;
import player.PlayerState;

import java.awt.*;

public class AnimationManager extends Component {

    private Animation kickAnim;
    private Animation punchAnim;
    private Animation defaultAnim;
    private Animation specialAttackAnim;
    private Animation runAnim;
    private Animation jumpAnim;
    private Animation blockAnim;

    private Animation currentAnim;
    private Animation lastAnim;

    public AnimationManager(PlayerTypes type, GameObject ref) {
        super(ref, ComponentType.AnimationController);

        defaultAnim = AnimationLoader.loadAnimation(type, AnimationType.Default, ref, true, 1);
        //  when no certain Animation is set this will be played

        kickAnim = AnimationLoader.loadAnimation(type, AnimationType.Kick, ref, false, 4);
        punchAnim = AnimationLoader.loadAnimation(type, AnimationType.Punch, ref, false, 4);
        specialAttackAnim = AnimationLoader.loadAnimation(type, AnimationType.SpecialAttack, ref, false, 5);
        blockAnim = AnimationLoader.loadAnimation(type, AnimationType.Block, ref, true, 4);
        runAnim = AnimationLoader.loadAnimation(type, AnimationType.Run, ref, true, 2);
        jumpAnim = AnimationLoader.loadAnimation(type, AnimationType.Jump, ref, true, 2);

        currentAnim = defaultAnim;
        lastAnim = defaultAnim;
    }

    public void setState(PlayerState playerState) {

        Animation newAnim = getAnim(playerState);

        if (currentAnim==null || currentAnim.getPriority() < newAnim.getPriority()) {
            currentAnim = newAnim;
        }
    }

    public void runAnimation(double dt) {
        manageAnimations(dt);
    }

    private void manageAnimations(double dt) {
        if (lastAnim!=currentAnim) {
            endAllAnimations();
        }

        if (currentAnim==null) {
            currentAnim = getAnim(PlayerState.Default);
        }

        currentAnim.playAnimation(dt);

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
        else if (playerState==PlayerState.Punch) { return punchAnim; }
        throw new Error("No matching Animation found");
    }

    private void endAllAnimations() {
        defaultAnim.endAnimation();
        kickAnim.endAnimation();
        punchAnim.endAnimation();
        blockAnim.endAnimation();
        specialAttackAnim.endAnimation();
        runAnim.endAnimation();
        jumpAnim.endAnimation();
    }

    @Override
    public void Render(Graphics2D g, Camera cam) {
        if (lastAnim != null) {
            lastAnim.Render(g, cam);
        } else if (defaultAnim != null) {
            defaultAnim.Render(g, cam);
        } else {
            System.out.println("Warning: Currently no Animation is Rendered");
        }
    }
}
