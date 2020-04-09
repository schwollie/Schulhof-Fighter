package components;

import java.awt.*;
import java.awt.Component;

public class Button extends GuiComponent {
    private static Font buttonFont = loadFont("FjallaOne");

    private Color hoverTextColor, hoverColor, pressedColor, pressedTextColor;
    private boolean pressActivated;

    public Button(String text, int width, int height) {
        super(width, height);
        this.text = text;
        color = transparentColor;
        textColor = new Color(255,255,255, 180);
        hoverColor = transparentColor;
        hoverTextColor = new Color(255,255,255);
        textSize = 25;
        pressActivated = false;
    }

    @Override
    public void paint(Graphics g) {
        int x = getX(), y = getY(), width = getWidth(), height = getHeight();
        g.setFont(buttonFont.deriveFont(pressed?textSize-2:textSize).deriveFont(Font.BOLD));
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
        }
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
