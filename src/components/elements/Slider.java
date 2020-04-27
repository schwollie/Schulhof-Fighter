package components.elements;

import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;
import logic.Vector2;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Slider extends GuiComponent {

    protected UiImage bar;
    protected UiImage handle;

    protected double progress = 1;

    protected boolean reverse = false;

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
            if (reverse) {
                bar.setCropR(new Rectangle2D.Double(1-progress,0,progress,1));
                bar.setOffset(new Vector2((1-progress), 0));
            } else {
                bar.setCropR(new Rectangle2D.Double(1-progress,0,progress,1));
            }

        }
    }

    @Override
    public void addTransform(ScreenTransform other) {
        super.addTransform(other);
        if (bar != null) { bar.addTransform(other); }
        if (handle != null) { handle.addTransform(other); }
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
        setCropFactor();
    }

}
