package components.elements;

import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Slider extends GuiComponent {

    protected UiImage bar;
    protected UiImage handle;

    protected double progress = 1;

    public Slider(GuiCanvas parent, ScreenTransform screenTransform) {
        super(parent, screenTransform);
    }

    @Override
    public void Render(Graphics2D g, Camera cam) {
        if (bar != null) {
            bar.Render(g, cam);
        }

        if (handle != null) {
            handle.Render(g, cam);
        }
    }

    public void loadBar(String filename) {
        bar = new UiImage(this.parentGUI, this.screenTransform, filename);
    }

    public void setProgress(double p) {
        this.progress = Math.min(Math.max(p, 0.000001), 1);
        setCropFactor();
    }

    public double getProgress() {
        return progress;
    }

    public void setCropFactor() {
        if (bar != null) {
            bar.setCropR(new Rectangle2D.Double(1-progress,0,progress,1));
        }
    }

    @Override
    public void addTransform(ScreenTransform other) {
        super.addTransform(other);
        if (bar != null) { bar.addTransform(other); }
        if (handle != null) { handle.addTransform(other); }
    }

    // region not import interface methods:
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
