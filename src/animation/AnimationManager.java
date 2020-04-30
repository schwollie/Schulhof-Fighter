package animation;

import game.Game;
import gameobjects.ComponentType;
import gameobjects.GameComponent;
import gameobjects.GameObject;
import graphics.RenderManager;
import loading.AnimationLoader;
import logic.PlayerType;
import player.PlayerState;

import java.util.HashMap;

public class AnimationManager extends GameComponent {

    private Animation currentAnim;
    private Animation lastAnim;
    private PlayerType playerType;

    private HashMap<PlayerState, Animation> animations;

    public AnimationManager(PlayerType type, GameObject ref) {
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

        Animation specialAttackAnim2 = AnimationLoader.loadPlayerAnimation(playerType, PlayerState.SpecialAttack2, reference, false, 5);
        animations.put(PlayerState.SpecialAttack2, specialAttackAnim2);

        Animation gotHit1 = AnimationLoader.loadPlayerAnimation(playerType, PlayerState.GotHit1, reference, false, 50);
        animations.put(PlayerState.GotHit1, gotHit1);

        Animation gotHit2 = AnimationLoader.loadPlayerAnimation(playerType, PlayerState.GotHit2, reference, false, 50);
        animations.put(PlayerState.GotHit2, gotHit2);

        Animation blockAnim = AnimationLoader.loadPlayerAnimation(playerType, PlayerState.Block, reference, true, 4);
        animations.put(PlayerState.Block, blockAnim);

        Animation runAnim = AnimationLoader.loadPlayerAnimation(playerType, PlayerState.Walk, reference, true, 2);
        animations.put(PlayerState.Walk, runAnim);

        Animation jumpAnim = AnimationLoader.loadPlayerAnimation(playerType, PlayerState.Jump, reference, true, 2);
        animations.put(PlayerState.Jump, jumpAnim);

        Animation deathAnim = AnimationLoader.loadPlayerAnimation(playerType, PlayerState.Death, reference, true, 10);
        animations.put(PlayerState.Death, deathAnim);

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
        double dt = Game.timeManager.getDeltaTime();
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
    public void UpdateDrawables(RenderManager renderManager) {
        Animation defaultAnimation = animations.get(PlayerState.Default);

        if (lastAnim != null) {
            lastAnim.UpdateDrawables(renderManager);
        } else if (defaultAnimation != null) {
            defaultAnimation.UpdateDrawables(renderManager);
        } else {
            System.err.println("Warning: Currently no Animation is Rendered");
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
