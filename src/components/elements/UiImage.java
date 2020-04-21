package components.elements;

import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;
import graphics.Anchor;
import logic.Dimension2D;
import logic.Transform;
import logic.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UiImage extends GuiComponent {

    private BufferedImage srcImg;
    private BufferedImage croppedImg;
    private String filename;
    private Dimension2D boundaries;
    private Anchor anchor = Anchor.TopLeft;
    private double ratio;
    private Rectangle2D.Double cropR = new Rectangle2D.Double(0,0,1,1);

    public UiImage(GuiCanvas parent, ScreenTransform s, String filename) {
        super(parent, s);
        this.filename = filename;
        loadImage();
        srcImg = rescaleImage((srcImg.getWidth()), (srcImg.getHeight()));
        this.boundaries = new Dimension2D(srcImg.getWidth(), srcImg.getHeight());

        setRatio();
        updateTransform();
        updateCroppedImg();
    }

    public UiImage(GuiCanvas parent, ScreenTransform s, double resolution, String filename, Anchor anchor) {
        super(parent, s);
        this.filename = filename;
        loadImage();
        srcImg = rescaleImage((int)(srcImg.getWidth()*resolution), (int)(srcImg.getHeight()*resolution));
        this.boundaries = new Dimension2D(srcImg.getWidth(), srcImg.getHeight());
        this.anchor = anchor;

        setRatio();
        updateTransform();
        updateCroppedImg();
    }

    private void updateTransform() {
        if (preserveAspect) {
            this.screenTransform.setScale(new Vector2(screenTransform.getScale().getX(), screenTransform.getScale().getX()*1/ratio));
        }
    }

    private void setRatio() {
        ratio = this.boundaries.getWidth() / this.boundaries.getHeight();
    }

    private void updateCroppedImg() {
        if (cropR!=null) {
            int x = (int)(cropR.getX() * boundaries.getWidth());
            int y = (int)(cropR.getY() * boundaries.getHeight());
            int width = (int)(cropR.getWidth() * boundaries.getWidth());
            int height = (int)(cropR.getHeight() * boundaries.getHeight());

            if (width == 0 || height == 0) { this.hideElement(); return; } else { this.setVisible(); }

            croppedImg = srcImg.getSubimage(x, y, width, height);
            return;
        }
        croppedImg = srcImg;
    }

    // given in percentage of picture
    public void setCropR(Rectangle2D.Double r) {
        this.cropR = r;
        updateCroppedImg();
    }

    public void loadImage() {
        try {
            srcImg = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage rescaleImage(int width, int height) {
        Image tmp = srcImg.getScaledInstance(width, height, Image.SCALE_FAST);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    @Override
    public synchronized void Render(Graphics2D g, Camera cam) {
        if (visible) {
            ScreenTransform ownTrans = this.getScreenTransform();
            Transform screenCoord = cam.gui2Screen(ownTrans);

            int x = (int) screenCoord.getX();
            int y = (int) screenCoord.getY();
            int width = (int) (screenCoord.getXScale() * cropR.getWidth());
            int height = (int) (screenCoord.getYScale() * cropR.getHeight());

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.transparentColor.getAlpha()));

            // center of image is on x, y
            switch (anchor) {
                case Center -> g.drawImage(croppedImg, x - width / 2, y - height / 2, width, height, null);
                case BottomLeft -> g.drawImage(croppedImg, x, y - height, width, height, null);
                case BottomRight -> g.drawImage(croppedImg, x - width, y - height, width, height, null);
                case TopLeft -> g.drawImage(croppedImg, x, y, width, height, null);
                case TopRight -> g.drawImage(croppedImg, x - width, y, width, height, null);
                default -> throw new Error("No Anchor on ImageSprite is specified!");
            }

        }
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
    }

    // region not used interface methods:
    @Override
    public void onClick() {

    }

    @Override
    public void onPress() {

    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onDrag() {

    }

    @Override
    public void onHover() {

    }
    // endregion
}
