package components.elements;


import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;
import logic.Anchor;

import java.awt.*;

public class ClockPointer extends GuiComponent {

    // Threading
    private RotatorThread rotator;

    // vars
    private double animSpeed = 100; // degrees pers second

    private double rotOffset = 0;
    private double rotLimit = 180;
    private double targetRot = 0;

    private boolean invertDir = false;

    private double progress = 0;

    private UiImage img;

    public ClockPointer(GuiCanvas parent, ScreenTransform screenTransform, Anchor anchor, String image) {
        super(parent, screenTransform);
        img = new UiImage(parent, this.screenTransform, image);

        this.anchor = anchor;
        img.setAnchor(this.anchor);
    }


    @Override
    public void Render(Graphics2D g, Camera cam) {
        img.Render(g, cam);
    }

    private void startRotation(double targetAngle) {
        this.targetRot = targetAngle;

        if (rotator != null) {
            return;
        }

        rotator = new RotatorThread(this);
        rotator.start();
    }

    // region getters and setters

    private void setRotation(double angle) {
        startRotation(angle + rotOffset);
    }

    public double getRotOffset() {
        return rotOffset;
    }

    public void setRotOffset(double rotOffset) {
        this.rotOffset = rotOffset;
        img.setRotation(rotOffset + img.getRotation());
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
        setRotation(progress * rotLimit * (invertDir ? -1 : 1));
    }

    public boolean isInvertDir() {
        return invertDir;
    }

    public void setInvertDir(boolean invertDir) {
        this.invertDir = invertDir;
    }

    public double getAnimSpeed() {
        return animSpeed;
    }

    public void setAnimSpeed(double animSpeed) {
        this.animSpeed = animSpeed;
    }

    public UiImage getImg() {
        return img;
    }

    public void setRotator(RotatorThread rotator) {
        this.rotator = rotator;
    }

    public double getTargetRot() {
        double rot = targetRot - ((int) (targetRot / 360) * 360);
        if (rot < 0) {
            rot = 360 + rot;
        }

        return rot;
    }

    public RotatorThread getRotator() {
        return rotator;
    }

    //endregion

    //region super methods:
    @Override
    public void addTransform(ScreenTransform other) {
        super.addTransform(other);
        img.setScreenTransform(this.screenTransform);
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
    // endregion
}

class RotatorThread extends Thread {

    private double tolerance = 1;

    private double speed; // degrees per second


    private ClockPointer clockPointer;

    public RotatorThread(ClockPointer pointer) {
        this.clockPointer = pointer;
        this.speed = clockPointer.getAnimSpeed() / 100;
        tolerance = speed * 0.6;
    }

    @Override
    public void run() {

        while (true) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (Math.abs(clockPointer.getImg().getRotation() - clockPointer.getTargetRot()) < tolerance) {
                break;
            }

            double delta = clockPointer.getImg().getRotation() - clockPointer.getTargetRot();

            double dir = delta > 0 ? -1 : 1;
            dir *= Math.abs(delta) < 180 ? 1 : -1;

            clockPointer.getImg().setRotation(clockPointer.getImg().getRotation() + dir * speed);
        }

        clockPointer.getImg().setRotation(clockPointer.getTargetRot());
        clockPointer.setRotator(null);
    }
}
