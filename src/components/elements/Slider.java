package components.elements;

import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;

import java.awt.*;

public class Slider extends GuiComponent {

    protected UiImage bar;
    protected UiImage handle;

    protected double progress = 0;

    public Slider(ScreenTransform screenTransform) {
        super(screenTransform);
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
