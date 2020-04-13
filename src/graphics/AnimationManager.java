package graphics;

import display.Camera;
import game.ComponentType;
import game.GameComponent;
import game.GameObject;
import logic.PlayerTypes;
import player.PlayerState;

import java.awt.*;
import java.util.HashMap;

public class AnimationManager extends GameComponent {

    private Animation currentAnim;
    private Animation lastAnim;
    private PlayerTypes playerType;

    private HashMap<PlayerState, Animation> animations;

    public AnimationManager(PlayerTypes type, GameObject ref) {
        super(ref, ComponentType.AnimationController);
        animations = new HashMap<>();
        playerType = type;
        loadAnimations();
    }

    public void loadAnimations() {
        Animation defaultAnim = AnimationLoader.loadPlayerAnimation(playerType, PlayerState.Default, reference, true, 1);
        animations.put(PlayerState.Default, defaultAnim);
        //  when no certain Animation is set this will be played

        Animation kickAnim = AnimationLoader.loadPlayerAnimation(playerType, PlayerState.Kick, reference, false, 4);
        animations.put(PlayerState.Kick, kickAnim);
        Animation punchAnim = AnimationLoader.loadPlayerAnimation(playerType, PlayerState.Punch, reference, false, 4);
        animations.put(PlayerState.Punch, punchAnim);
        Animation specialAttackAnim = AnimationLoader.loadPlayerAnimation(playerType, PlayerState.SpecialAttack, reference, false, 5);
        animations.put(PlayerState.SpecialAttack, specialAttackAnim);
        Animation blockAnim = AnimationLoader.loadPlayerAnimation(playerType, PlayerState.Block, reference, true, 4);
        animations.put(PlayerState.Block, blockAnim);
        Animation runAnim = AnimationLoader.loadPlayerAnimation(playerType, PlayerState.Walk, reference, true, 2);
        animations.put(PlayerState.Walk, runAnim);
        Animation jumpAnim = AnimationLoader.loadPlayerAnimation(playerType, PlayerState.Jump, reference, true, 2);
        animations.put(PlayerState.Jump, jumpAnim);

        currentAnim = defaultAnim;
        lastAnim = defaultAnim;
    }

    public void setState(PlayerState playerState) {

        Animation newAnim = getAnim(playerState);

        if (currentAnim == null || currentAnim.getPriority() < newAnim.getPriority()) {
            currentAnim = newAnim;
        }
    }

    @Override
    public void tick() {
        double dt = reference.getTime().getDeltaTime();
        manageAnimations(dt);
    }

    private void manageAnimations(double dt) {
        if (lastAnim != currentAnim) {
            endAllAnimations();
        }

        if (currentAnim == null) {
            currentAnim = getAnim(PlayerState.Default);
        }

        currentAnim.playAnimation(dt);

        lastAnim = currentAnim;

        if (currentAnim.isLoopAnim() || currentAnim.hasFinished()) {
            currentAnim = null;
        }
    }

    private Animation getAnim(PlayerState playerState) {
        Animation anim = animations.get(playerState);
        if (anim == null) {
            throw new Error("No matching Animation found");
        }
        return anim;
    }

    private void endAllAnimations() {
        animations.forEach((state, animation) -> {
            animation.endAnimation();
        });
    }

    @Override
    public void Render(Graphics2D g, Camera cam) {
        Animation defaultAnimation = animations.get(PlayerState.Default);

        if (lastAnim != null) {
            lastAnim.Render(g, cam);
        } else if (defaultAnimation != null) {
            defaultAnimation.Render(g, cam);
        } else {
            System.out.println("Warning: Currently no Animation is Rendered");
        }
    }

    public void unloadImage() {
        animations.forEach((state, animation) -> {
            animation.unloadImage();
        });
        animations.clear();
    }

    public void loadImage() {
        loadAnimations();
    }
}
