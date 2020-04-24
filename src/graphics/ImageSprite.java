package graphics;

import display.Camera;
import gameobjects.GameObject;
import loading.SpriteLoader;
import logic.Dimension2D;
import logic.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;


public class ImageSprite extends Sprite {

    private BufferedImage img;
    private String filename;
    private Dimension2D boundaries;  // array with length 2 [width, height]
    private Anchor anchor = Anchor.Center;

    public ImageSprite(GameObject reference, double resolution, String filename) {
        super(reference);
        this.filename = filename;
        this.visible = false;
        loadImage();
        img = rescaleImage((int)(img.getWidth()*resolution), (int)(img.getHeight()*resolution));
        this.boundaries = new Dimension2D(img.getWidth(), img.getHeight());
    }

    public ImageSprite(GameObject reference, double resolution, String filename, Anchor anchor) {
        super(reference);
        this.filename = filename;
        this.visible = false;
        loadImage();
        img = rescaleImage((int)(img.getWidth()*resolution), (int)(img.getHeight()*resolution));
        this.boundaries = new Dimension2D(img.getWidth(), img.getHeight());
        this.anchor = anchor;
    }

    public ImageSprite(GameObject reference, Dimension2D boundaries, String filename) {
        super(reference);
        this.filename = filename;
        this.visible = false;
        loadImage();
        img = rescaleImage((int) boundaries.getWidth(), (int) boundaries.getHeight());
        this.boundaries = boundaries;
    }

    public ImageSprite(GameObject reference, BufferedImage image) {
        super(reference);
        this.visible = true;
        img = image;
        this.boundaries = new Dimension2D(img.getWidth(), img.getHeight());
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
            img = SpriteLoader.getFromFilePath(filename);
            visible = true;
        }
    }

    public void unloadImage() {
        if (visible) {
            visible = false;
            img = null;
        }
    }

    public BufferedImage rescaleImage(int width, int height) {
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
            Transform screenCoord = cam.world2Screen(ownTrans);

            int x = (int) screenCoord.getX();
            int y = (int) screenCoord.getY();
            int width = (int) screenCoord.getXScale();
            int height = (int) (screenCoord.getYScale() * getYScaleFactor());

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha));

            // center of image is on x, y
            switch (anchor) {
                case Center:
                    g.drawImage(img, x - width / 2, y - height / 2, width, height, null);
                    break;
                case BottomLeft:
                    g.drawImage(img, x, y - height, width, height, null);
                    break;
                case BottomRight:
                    g.drawImage(img, x - width, y - height, width, height, null);
                    break;
                case TopLeft:
                    g.drawImage(img, x, y, width, height, null);
                    break;
                case TopRight:
                    g.drawImage(img, x - width, y, width, height, null);
                    break;
                default:
                    throw new Error("No Anchor on ImageSprite is specified!");
            }

        }
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
    }
}
