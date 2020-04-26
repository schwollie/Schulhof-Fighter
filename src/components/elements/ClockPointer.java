package components.elements;


import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;
import logic.Anchor;

import java.awt.*;

public class ClockPointer extends GuiComponent {

    // 360 degrees = 1

    private double rotOffset = 0;
    private double rotLimit = 1;
    private boolean invertDir = false;

    private Anchor anchor = new Anchor(0.5, 0.1);

    public ClockPointer(GuiCanvas parent, ScreenTransform screenTransform) {
        super(parent, screenTransform);
    }


    @Override
    public void Render(Graphics2D g, Camera cam) {



    }

    // region not needed super methods:
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
