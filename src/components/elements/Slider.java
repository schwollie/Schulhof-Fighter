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
    protected TextView textView;

    protected double progress = 0;

    protected boolean reverse = false;

    // nice progress effect
    private double speed = 5;
    private double targetProgress;
    private SliderThread sliderThread;

    public Slider(GuiCanvas parent, ScreenTransform screenTransform) {
        super(parent, screenTransform);
        ScreenTransform textTransform = new ScreenTransform(screenTransform.getPos(), screenTransform.getScale());
        Vector2 scale = textTransform.getScale();
        textTransform.setPos(textTransform.getPos().add(new Vector2(scale.getX() / 2, scale.getY())));
        textView = new TextView(parent, textTransform, "perc");
        textView.setFont(new Font("Comic Sans", Font.PLAIN, 15));
        textView.setVisible(false);
        parent.addGuiComponent(textView);
    }

    @Override
    public void addTransform(ScreenTransform other) {
        super.addTransform(other);
        if (bar != null) {
            bar.addTransform(other);
        }
        if (handle != null) {
            handle.addTransform(other);
        }
        if (textView != null) {
            textView.addTransform(other);
        }
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

    public void applyProgress(double p) {
        this.progress = Math.min(Math.max(p, 0.000001), 1);
        setCropFactor();
    }

    public void setProgress(double p) {
        textView.setText((Math.round(100*p))+ " %");
        this.targetProgress = Math.min(Math.max(p, 0.000001), 1);

        if (sliderThread != null) {
            return;
        }

        sliderThread = new SliderThread(this);
        sliderThread.start();
    }

    public double getProgress() {
        return progress;
    }

    public void setCropFactor() {
        if (bar != null) {
            bar.setCropR(new Rectangle2D.Double(1 - progress, 0, progress, 1));
            if (reverse) {
                bar.setOffset(new Vector2((1 - progress), 0));
            }
        }
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isReverse() {
        return reverse;
    }

    public double getTargetProgress() {
        return targetProgress;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
        setCropFactor();
    }

    public void setSliderThread(SliderThread sliderThread) {
        this.sliderThread = sliderThread;
    }

    @Override
    public void onHoverEnter() {
        super.onHoverEnter();
        //textView.setVisible(true);
    }

    @Override
    public void onHoverExit() {
        super.onHoverExit();
        //textView.setVisible(false);
    }
}

class SliderThread extends Thread {

    private double tolerance;

    private double speed; // degrees per second


    private Slider slider;

    public SliderThread(Slider slider) {
        this.slider = slider;
        this.speed = slider.getSpeed() / 1000;

        tolerance = speed * 0.6;
    }

    @Override
    public void run() {

        while (true) {

            // 100 fps is more than enough
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // test if more sliding is needed
            if (Math.abs(slider.getProgress() - slider.getTargetProgress()) < tolerance) {
                break;
            }

            // slide
            double delta = slider.getProgress() - slider.getTargetProgress();
            int dir = delta > 0 ? -1 : 1;

            slider.applyProgress(slider.getProgress() + dir * speed);

        }

        slider.applyProgress(slider.getTargetProgress());
        slider.setSliderThread(null);
    }
}
