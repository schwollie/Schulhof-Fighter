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

    public void setRot(double angle) {
        img.setRot(angle);
    }

    @Override
    public void onHoverEnter() {
        //System.out.println("Hover");
        //setRot(progress);
        //progress += 0.001;
    }

    @Override
    public void onHoverExit() {
        //System.out.println("HoverExit");
    }
}
