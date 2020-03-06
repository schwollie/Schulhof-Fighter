package graphics;

import display.Canvas;
import game.GameWorld;
import logic.Transform;
import player.Player;

import java.awt.*;

public class Animation {

    private Player playerRef;
    private ImageSprite[] images;
    private float speed = 7f;  // pictures per second

    // for animation
    private int currentImage = 0;
    private double timeElapsed = 0;
    private ImageSprite currentSprite;

    public Animation(ImageSprite[] imgs, Player player) {
        this.images = imgs;
        this.playerRef = player;
    }

    public void playAnimation(GameWorld g, double deltaTime) {
        timeElapsed += deltaTime;

        if (timeElapsed > 1/speed || currentSprite == null) {
            nextFrame(g);
            timeElapsed = 0;
        }
    }

    public void endAnimation(GameWorld g) {
        g.removeSprite(currentSprite);
        this.currentImage = 0;
        this.currentSprite = null;
        this.timeElapsed = 0;
    }

    private void nextFrame(GameWorld g) {
        currentImage ++;

        if (currentImage >= images.length) {
            currentImage = 0;
        }

        if (images.length == 0) { return; }

        g.removeSprite(currentSprite);
        currentSprite = images[currentImage];
        g.addSprite(currentSprite);

    }

    public static Animation loadAnim(String animSheetPath, int width, int height, int pictureCount, Player player) {

        ImageSprite[] animImages = new ImageSprite[pictureCount];
        ImageSprite animSheet = new ImageSprite(player, new Dimension(width*pictureCount, height), animSheetPath);

        for (int i = 0; i < pictureCount; i++) {
            animImages[i] = animSheet.getSlice(i*width, 0, width, height);
        }

        return new Animation(animImages, player);
    }

}
