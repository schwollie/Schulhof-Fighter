package gui.components;

import java.awt.*;

public class Button extends Component {

    public Button(String text, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.text = text;
    }

    @Override
    public void paint(Graphics g) {
        if (pressed) {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
        } else {
            g.setColor(hovered ? Color.BLUE : Color.BLACK);
            g.fillRect(x, y, width, height);
            g.setColor(Color.WHITE);
        }
        g.drawRect(x, y, width, height);
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
            //text could be too long! -> make it shorter
            g.drawString(text, posX, y + height / 2);
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
