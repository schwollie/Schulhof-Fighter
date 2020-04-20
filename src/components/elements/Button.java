package components.elements;

import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;

import java.awt.*;

public class Button extends GuiComponent {
    private static final Font buttonFont = loadFont("FjallaOne");

    private final Color hoverTextColor;
    private final Color hoverColor;
    private Color pressedColor;
    private Color pressedTextColor;
    private final boolean pressActivated;

    public Button(String text, ScreenTransform transform) {
        super(transform);
        //this.text = text;
        color = transparentColor;
        textColor = new Color(255, 255, 255, 180);
        hoverColor = transparentColor;
        hoverTextColor = new Color(255, 255, 255);
        //textSize = 25;
        pressActivated = false;
    }

    @Override
    public void Render(Graphics2D g, Camera cam) {
        /*int x = getInPixelX(cam.getResolution());
        int y = getInPixelY(cam.getResolution());
        int width = getInPixelWidth(cam.getResolution());
        int height = getInPixelHeight(cam.getResolution());

        g.setFont(buttonFont.deriveFont(pressed ? textSize - 2 : textSize).deriveFont(Font.BOLD));

        if (pressActivated && pressed) {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
        } else {
            g.setColor(hovered ? hoverColor : color);
            g.fillRect(x, y, width, height);
            g.setColor(hovered ? hoverTextColor : textColor);
        }
        if (text != null) {
            int posX = 0;
            switch (textAlign) {
                case LEFT:
                    posX = x + 1;
                    break;
                case CENTER:
                    posX = x + width / 2 - g.getFontMetrics().stringWidth(text) / 2;
                    break;
                case RIGHT:
                    posX = x + width - g.getFontMetrics().stringWidth(text);
                    break;
            }
            g.drawString(testStringLength(text, g.getFontMetrics()), posX, y + height / 2);
        }*/
    }


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

}
