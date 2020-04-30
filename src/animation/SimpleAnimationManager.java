package animation;

import game.Game;
import gameobjects.ComponentType;
import gameobjects.GameComponent;
import gameobjects.GameObject;
import graphics.RenderManager;
import loading.AnimationLoader;
import logic.Dimension2D;
import player.PlayerState;

public class SimpleAnimationManager extends GameComponent {

    private Animation animation;
    private String path;
    private int picCount;
    private float animationSpeed;
    private Dimension2D size;

    public SimpleAnimationManager(String path, int picCount, float animationSpeed, Dimension2D size, GameObject ref) {
        super(ref, ComponentType.AnimationController);
        this.path = path;
        this.picCount = picCount;
        this.size = size;
        this.animationSpeed = animationSpeed;
        loadAnimation();
    }

    private void loadAnimation() {
        animation = AnimationLoader.loadAnimation(path, picCount, animationSpeed, size, reference, true, 100);
    }

    @Override
    public void tick() {
        double dt = Game.timeManager.getDeltaTime();
        animation.playAnimation(dt);
    }

    private Animation getAnim(PlayerState playerState) {
        return animation;
    }

    private void endAnimation() {
        animation.endAnimation();
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    @Override
    public void UpdateDrawables(RenderManager renderManager) {
        animation.UpdateDrawables(renderManager);
    }

    public void unloadImage() {
        animation.unloadImage();
    }

    public void loadImage() {
        loadAnimation();
    }

}
