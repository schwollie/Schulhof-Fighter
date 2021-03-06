package components.elements;

import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;
import loading.SpriteLoader;
import logic.Anchor;
import logic.Dimension2D;
import logic.Transform;
import logic.Vector2;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class UiImage extends GuiComponent {

    private BufferedImage srcImg;
    private BufferedImage croppedImg;
    private String filename;
    private Dimension2D boundaries;

    private double ratio;
    private Rectangle2D.Double cropR = new Rectangle2D.Double(0,0,1,1);
    private Vector2 offset = new Vector2(0,0);

    private double rot = 0;

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
            this.setRectFromTransform();
        }
    }

    private void setRatio() {
        ratio = this.boundaries.getWidth() / this.boundaries.getHeight();
    }

    private void updateCroppedImg() {
        if (rot!=0) { }

        if (cropR!=null) {
            int x = (int)(cropR.getX() * boundaries.getWidth());
            int y = (int)(cropR.getY() * boundaries.getHeight());
            int width = (int)(cropR.getWidth() * boundaries.getWidth());
            int height = (int)(cropR.getHeight() * boundaries.getHeight());

            if (width == 0 || height == 0) {
                this.setVisible(false);
                return;
            } else {
                this.setVisible(true);
            }

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
        srcImg = SpriteLoader.getFromFilePath(filename);
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

            int x = (int) (screenCoord.getX() + offset.getX() * screenCoord.getXScale());
            int y = (int) (screenCoord.getY() + offset.getY() * screenCoord.getYScale());
            int width = (int) (screenCoord.getXScale() * cropR.getWidth());
            int height = (int) (screenCoord.getYScale() * cropR.getHeight());

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha));

            int xOffset = (int) this.anchor.getXOffset(width);
            int yOffset = (int) this.anchor.getYOffset(height);

            x = x-xOffset;
            y = y-yOffset;

            this.lastRenderPos.setValues(x, y);
            this.lastRenderWidth.setValues(width, height);

            // rot
            AffineTransform t = new AffineTransform();
            t.setToRotation(Math.toRadians(rot), x + xOffset, y + yOffset);
            g.setTransform(t);

            g.drawImage(croppedImg, x, y, width, height, null);

            t = new AffineTransform();
            t.setToRotation(0, x, y);
            g.setTransform(t);
        }
    }

    public void setOffset(Vector2 f) {
        this.offset = f;
    }

    public void setRotation(double rot) {
        this.rot = rot - ((int) (rot / 360) * 360);
        if (this.rot < 0) {
            this.rot = 360 + this.rot;
        }
    }

    public double getRotation() {
        return rot - ((int) (rot / 360) * 360);
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
    }

    public UiImage(GuiCanvas parent, ScreenTransform screenTransform) {
        super(parent, screenTransform);
    }

    @Override
    public void onHoverEnter() {

        //setAlpha(.5f);
    }

    @Override
    public void onHoverExit() {

        //setAlpha(1f);
    }
}
