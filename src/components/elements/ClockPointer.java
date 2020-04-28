package components.elements;


import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;
import logic.Anchor;

import java.awt.*;

public class ClockPointer extends GuiComponent {

    private double rotOffset = 0;
    private double rotLimit = 360;
    private boolean invertDir = false;

    private double progress = 0;

    private UiImage img;

    public ClockPointer(GuiCanvas parent, ScreenTransform screenTransform, String image) {
        super(parent, screenTransform);
        img = new UiImage(parent, this.screenTransform, image);

        img.setAnchor(new Anchor(0.5, 0.15));
        this.anchor = new Anchor(0.5, 0.15);
    }


    @Override
    public void Render(Graphics2D g, Camera cam) {
        img.Render(g, cam);
    }

    private void setRotation(double angle) {
        System.out.println(angle + rotOffset);
        img.setRotation(angle + rotOffset);
    }

    public double getRotOffset() {
        return rotOffset;
    }

    public void setRotOffset(double rotOffset) {
        this.rotOffset = rotOffset;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
        System.out.println(progress * rotLimit * (invertDir ? -1 : 1));
        setRotation(progress * rotLimit * (invertDir ? -1 : 1));
    }

    public boolean isInvertDir() {
        return invertDir;
    }

    public void setInvertDir(boolean invertDir) {
        this.invertDir = invertDir;
    }

    @Override
    public void onHoverEnter() {
        //System.out.println("Hover");
        //setRot(progress);
        //progress += 0.1;
    }

    @Override
    public void onHoverExit() {
        //System.out.println("HoverExit");
    }
}
