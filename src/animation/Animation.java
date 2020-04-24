package animation;

import gameobjects.ComponentType;
import gameobjects.GameComponent;
import gameobjects.GameObject;
import graphics.ImageSprite;
import graphics.RenderManager;
import logic.Dimension2D;
import player.PlayerState;

public class Animation extends GameComponent {

    private final GameObject ref;
    private final ImageSprite[] images;
    private float speed = 7f;  // pictures per second
    private boolean loopAnim;
    private boolean hasFinished = false; // only for non looped animations
    private PlayerState animType = PlayerState.NotSpecified;
    private int priority = 0;

    // for animation
    private int currentImage = 0;
    private double timeElapsed = 0;
    private ImageSprite currentSprite;
    private boolean visible = true;

    public Animation(ImageSprite[] imgs, GameObject ref) {
        super(ref, ComponentType.Animation);
        this.images = imgs;
        this.ref = ref;
        this.currentSprite = imgs[0];
    }

    public void playAnimation(double deltaTime) {
        this.visible = true;
        timeElapsed += deltaTime;

        if (timeElapsed > 1 / speed || currentSprite == null) {
            nextFrame();
            timeElapsed = 0;
        }
    }

    public void endAnimation() {
        this.visible = false;
        this.currentImage = 0;
        this.currentSprite = null;
        this.timeElapsed = 0;
        hasFinished = false;
    }

    private void nextFrame() {
        if (currentImage >= images.length) {
            currentImage = 0;

            if (!loopAnim) {
                hasFinished = true;
            }

        }

        if (images.length == 0) {
            return;
        }

        currentSprite = images[currentImage];

        currentImage++;
    }

    public boolean hasFinished() {
        return this.hasFinished;
    }

    public boolean isLoopAnim() {
        return loopAnim;
    }

    public static Animation loadAnim(String animSheetPath, AnimSpecs specs, GameObject ref) {
        int pictureCount = specs.getAnimPicCount();
        int priority = specs.priority;
        boolean loopAnim = specs.loopAnim;

        PlayerState type = specs.animType;
        Dimension2D bounds = specs.getImgDimensions();

        // create sprites from spritesheet
        ImageSprite[] animImages = new ImageSprite[pictureCount];
        ImageSprite animSheet = new ImageSprite(ref, new Dimension2D((int) (bounds.getWidth() * pictureCount), (int) bounds.getHeight()), animSheetPath);

        for (int i = 0; i < pictureCount; i++) {
            animImages[i] = animSheet.getSlice((int) (i * bounds.getWidth()), 0, (int) bounds.getWidth(), (int) bounds.getHeight());
        }

        // setup Animation
        Animation anim = new Animation(animImages, ref);
        anim.setSpeed(specs.getAnimSpeed());
        anim.setAnimType(specs.animType);
        anim.setPriority(specs.priority);
        anim.setLoopAnim(specs.loopAnim);

        return anim;
    }

    public static Animation loadAnim(ImageSprite[] images, AnimSpecs specs, GameObject ref) {
        int pictureCount = specs.getAnimPicCount();
        int priority = specs.priority;
        boolean loopAnim = specs.loopAnim;

        ImageSprite[] animImages = new ImageSprite[images.length];
        for (int i = 0; i < images.length; i++) {
            animImages[i] = new ImageSprite(ref, images[i].getImg());
        }

        // setup Animation
        Animation anim = new Animation(animImages, ref);
        anim.setSpeed(specs.getAnimSpeed());
        anim.setAnimType(specs.animType);
        anim.setPriority(specs.priority);
        anim.setLoopAnim(specs.loopAnim);

        return anim;
    }

    public int getPriority() {
        return priority;
    }

    public PlayerState getAnimType() {
        return animType;
    }

    public void setLoopAnim(boolean loopAnim) {
        this.loopAnim = loopAnim;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ImageSprite[] getImages() {
        return images;
    }

    @Override
    public void UpdateDrawables(RenderManager renderManager) {
        if (visible && currentSprite != null) {
            currentSprite.UpdateDrawables(renderManager);
        }
    }

    @Override
    public String toString() {
        return "Animation{" +
                "animType=" + animType +
                '}';
    }

    public void setAnimType(PlayerState animType) {
        this.animType = animType;
    }

    @Override
    public void unloadImage() {
        super.unloadImage();
        for (ImageSprite img : images) {
            img.unloadImage();
        }
    }

}
