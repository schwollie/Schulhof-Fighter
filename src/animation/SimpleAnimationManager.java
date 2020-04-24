package animation;

import game.ComponentType;
import game.GameComponent;
import game.GameObject;
import graphics.ImageSprite;
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

    public SimpleAnimationManager(ImageSprite[] images, float animationSpeed, GameObject ref) {
        super(ref, ComponentType.AnimationController);
        this.animationSpeed = animationSpeed;
        loadAnimation(images);
    }

    private void loadAnimation() {
        animation = AnimationLoader.loadAnimation(path, picCount, animationSpeed, size, reference, true, 100);
    }

    private void loadAnimation(ImageSprite[] animImages) {
        animation = AnimationLoader.loadAnimation(animImages, animationSpeed, reference, true, 100);
    }


    @Override
    public void tick() {
        double dt = reference.getTime().getDeltaTime();
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
