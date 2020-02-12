package graphics;

import logic.Transform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageSprite extends Sprite {

    private BufferedImage img;
    private Dimension boundaries;  // array with length 2 [width, height]

    public ImageSprite(Dimension boundaries, String filename) {
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        img = rescaleImage(boundaries.width, boundaries.height);
        this.boundaries = boundaries;
    }

    public ImageSprite(Dimension boundaries, BufferedImage image) {
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
        return new ImageSprite(new Dimension(width, height),slice);
    }

    public void draw(Graphics g) {
        if (visible == true)
            g.drawImage(img, (int)transform.getPosition().getX(), (int)transform.getPosition().getY(),
                    img.getWidth(), img.getHeight(), null);
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

    public void updateTransform(Transform t) {
        this.transform = t;
    }

}
