package components.elements;

import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;
import logic.Transform;

import java.awt.*;

public class Switch extends GuiComponent {

    private boolean active;
    private Color activeColor, disabledColor, pointColor;

    public Switch(GuiCanvas parent, ScreenTransform screenTransform) {
        super(parent, screenTransform);
        activeColor = new Color(46, 204, 113);
        disabledColor = new Color(189, 195, 199);
        pointColor = new Color(52, 73, 94);

    }

    @Override
    public void Render(Graphics2D g, Camera cam) {
        ScreenTransform ownTrans = this.getScreenTransform();
        Transform screenCoord = cam.gui2Screen(ownTrans);

        int x = (int) screenCoord.getX();
        int y = (int) screenCoord.getY();
        int width = (int) screenCoord.getXScale();
        int height = (int) screenCoord.getYScale();
        g.setColor(active?activeColor:disabledColor);
        g.fillRoundRect(x,y,width,height, 10,10);
        g.setColor(pointColor);
        x = active?x+width-(height/3*2):x-height/3;
        int radius = height+height/3;
        g.fillOval(x,y-height/6, radius, radius);
    }

    @Override
    public void onClick() {
        active = !active;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
