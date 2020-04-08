package graphics;

import game.GameWorld;
import logic.AnimationTypes;
import logic.Dimension2D;
import player.Player;

import java.awt.*;

public class Animation {

    private Player playerRef;
    private ImageSprite[] images;
    private float speed = 7f;  // pictures per second
    private boolean loopAnim;
    private boolean hasFinished = false; // only for non looped animations
    private AnimationTypes animType = AnimationTypes.NotSpecified;
    private int priority = 0;

    // for animation
    private int currentImage = 0;
    private double timeElapsed = 0;
    private ImageSprite currentSprite;

    public Animation(ImageSprite[] imgs, Player player) {
        this.images = imgs;
        this.playerRef = player;
        //this.loopAnim = loopAnim;

        this.currentSprite = imgs[0];
        //this.animType = type;
        //this.priority = priority;
    }

    public static Animation loadAnim(String animSheetPath, AnimSpecs specs, Player player) {

        int pictureCount = specs.getAnimPicCount();
        int priority = specs.priority;
        boolean loopAnim = specs.loopAnim;

        AnimationTypes type = specs.animType;
        Dimension2D bounds = specs.getImgDimensions();

        // create sprites from spritesheet
        ImageSprite[] animImages = new ImageSprite[pictureCount];
        ImageSprite animSheet = new ImageSprite(player, new Dimension((int) (bounds.getWidth() * pictureCount), (int) bounds.getHeight()), animSheetPath);

        for (int i = 0; i < pictureCount; i++) {
            animImages[i] = animSheet.getSlice((int) (i * bounds.getWidth()), 0, (int) bounds.getWidth(), (int) bounds.getHeight());
        }

        // setup Animation
        Animation anim = new Animation(animImages, player);
        anim.setSpeed(specs.getAnimSpeed());
        anim.setAnimType(specs.animType);
        anim.setPriority(specs.priority);
        anim.setLoopAnim(specs.loopAnim);

        return anim;
    }

    public void playAnimation(GameWorld g, double deltaTime) {
        timeElapsed += deltaTime;

        if (timeElapsed > 1 / speed || currentSprite == null) {
            nextFrame(g);
            timeElapsed = 0;
        }
    }

    public void endAnimation(GameWorld g) {
        g.removeSprite(currentSprite);
        this.currentImage = 0;
        this.currentSprite = null;
        this.timeElapsed = 0;
        hasFinished = false;
    }

    public boolean hasFinished() {
        return this.hasFinished;
    }

    public boolean isLoopAnim() {
        return loopAnim;
    }

    private void nextFrame(GameWorld g) {
        if (currentImage >= images.length) {
            currentImage = 0;

            if (!loopAnim) {
                hasFinished = true;
            }

        }

        if (images.length == 0) {
            return;
        }

        g.removeSprite(currentSprite);
        currentSprite = images[currentImage];
        g.addSprite(currentSprite);

        currentImage++;
    }

    public int getPriority() {
        return priority;
    }

    public AnimationTypes getAnimType() {
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

    @Override
    public String toString() {
        return "Animation{" +
                "animType=" + animType +
                '}';
    }

    public void setAnimType(AnimationTypes animType) {
        this.animType = animType;
    }

}
