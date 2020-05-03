package components.elements;

import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;
import game.Consts;
import game.Game;
import logic.Transform;

import java.awt.*;

public class InputSlider extends GuiComponent {
    private double value, maxValue;
    private Color activeColor, disabledColor, pointColor;
    private boolean selected;
    private int x, width;

    public InputSlider(GuiCanvas parent, ScreenTransform screenTransform) {
        super(parent, screenTransform);
        activeColor = new Color(46, 204, 113);
        disabledColor = new Color(189, 195, 199);
        pointColor = new Color(52, 73, 94);
        value = 50;
        maxValue = 100;
    }

    @Override
    public void Render(Graphics2D g, Camera cam) {
        ScreenTransform ownTrans = this.getScreenTransform();
        Transform screenCoord = cam.gui2Screen(ownTrans);

        x = (int) screenCoord.getX();
        width = (int)screenCoord.getXScale();
        int y = (int) screenCoord.getY();
        int height = (int) screenCoord.getYScale();
        int sliderPos = (int) (width*getPercentage());

        g.setColor(activeColor);
        g.fillRoundRect(x,y,sliderPos,height, 10,10);
        g.setColor(disabledColor);
        g.fillRoundRect(x+sliderPos,y,width-sliderPos,height, 10,10);
        g.setColor(pointColor);
        int radius = height+height/3;
        g.fillOval(x+sliderPos-radius/2,y-height/6, radius, radius);
    }

    @Override
    public void onPress() {
    }

    @Override
    public void onRelease() {
        setValueByPos(Game.inputManager.getMousePosition().getX());
    }

    @Override
    public void onDrag() {
        setValueByPos(Game.inputManager.getMousePosition().getX());
    }

    public double getPercentage(){
        return value/maxValue;
    }

    private void setValueByPos(double pos){
        double newPerc = Math.min((pos-x)/(double)width, 1);
        value = maxValue*newPerc;
    }

}
