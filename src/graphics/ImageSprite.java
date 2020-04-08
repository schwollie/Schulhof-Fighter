package graphics;

import display.Camera;
import game.GameObject;
import logic.Transform;
import logic.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageSprite extends Sprite {

    private BufferedImage img;
    private Dimension boundaries;  // array with length 2 [width, height]

    public ImageSprite(GameObject reference, Dimension boundaries, String filename) {
        super(reference);

        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        img = rescaleImage(boundaries.width, boundaries.height);
        this.boundaries = boundaries;
    }

    public ImageSprite(GameObject reference, Dimension boundaries, BufferedImage image) {
        super(reference);
        img = image;
        this.boundaries = boundaries;
        img = rescaleImage(boundaries.width, boundaries.height);
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
        return new ImageSprite(this.reference, new Dimension(width, height),slice);
    }

    public double getYScaleFactor() { // to preserve image ratio
        return (double)img.getHeight() / img.getWidth();
    }

    @Override
    public synchronized void Render(Graphics2D g, Camera cam) {
        if (visible) {
            Transform ownTrans = this.getTransform().addPosition(this.offset);
            Transform screenCoord = cam.worldToScreen(ownTrans);

            int x = (int)screenCoord.getX();
            int y = (int)screenCoord.getY();
            int width = (int)screenCoord.getXScale();
            int height = (int)(screenCoord.getYScale() * getYScaleFactor());

            // center of image is on x, y
            g.drawImage(img, x-width/2, y-height/2, width, height, null);

        }
    }

    public Dimension getBoundaries() {
        return boundaries;
    }

    public void updateBoundaries(Dimension newBounds) {
        if (newBounds.getWidth() != this.boundaries.getWidth() || boundaries.getHeight() != this.boundaries.getHeight()) {
            rescaleImage((int)newBounds.getWidth(), (int)newBounds.getHeight());
        }

        this.boundaries = newBounds;
    }

}
