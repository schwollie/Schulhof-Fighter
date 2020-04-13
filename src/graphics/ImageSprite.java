package graphics;

import display.Camera;
import game.GameObject;
import logic.Dimension2D;
import logic.Transform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageSprite extends Sprite {

    private BufferedImage img;
    private String filename;
    private Dimension2D boundaries;  // array with length 2 [width, height]

    public ImageSprite(GameObject reference, Dimension2D boundaries, String filename) {
        super(reference);
        this.filename = filename;
        this.visible = false;
        loadImage();
        img = rescaleImage((int) boundaries.getWidth(), (int) boundaries.getHeight());
        this.boundaries = boundaries;
    }

    public ImageSprite(GameObject reference, Dimension2D boundaries, BufferedImage image, String filename) {
        super(reference);
        img = image;
        this.filename = filename;
        this.boundaries = boundaries;
        img = rescaleImage((int) boundaries.getWidth(), (int) boundaries.getHeight());
    }

    public void loadImage() {
        if (!visible) {
            try {
                img = ImageIO.read(new File(filename));
                visible = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void unloadImage() {
        if (visible) {
            visible = false;
            img = null;
        }
    }

    private BufferedImage rescaleImage(int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_FAST);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public ImageSprite getSlice(int x, int y, int width, int height) {
        BufferedImage slice = this.img.getSubimage(x, y, width, height);
        return new ImageSprite(this.reference, new Dimension2D(width, height), slice, filename);
    }

    public double getYScaleFactor() { // to preserve image ratio
        return (double) img.getHeight() / img.getWidth();
    }

    @Override
    public synchronized void Render(Graphics2D g, Camera cam) {
        if (visible) {
            Transform ownTrans = this.getAbsoluteTransform();
            Transform screenCoord = cam.worldToScreen(ownTrans);

            int x = (int) screenCoord.getX();
            int y = (int) screenCoord.getY();
            int width = (int) screenCoord.getXScale();
            int height = (int) (screenCoord.getYScale() * getYScaleFactor());

            // center of image is on x, y
            g.drawImage(img, x - width / 2, y - height / 2, width, height, null);
        }
    }

    public Dimension2D getBoundaries() {
        return boundaries;
    }

    public void updateBoundaries(Dimension2D newBounds) {
        if (newBounds.getWidth() != this.boundaries.getWidth() || boundaries.getHeight() != this.boundaries.getHeight()) {
            rescaleImage((int) newBounds.getWidth(), (int) newBounds.getHeight());
        }

        this.boundaries = newBounds;
    }

}
