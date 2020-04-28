package components.elements;

import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;
import game.Consts;
import logic.Transform;

import java.awt.*;

public class TextView extends GuiComponent {

    private Font font = loadFont(Consts.font1);
    private float fontSize = 15;
    private TextAlign textAlign = TextAlign.CENTER;
    private int fontType = Font.BOLD;
    private String text;
    private Color textColor = Color.WHITE;

    public TextView(GuiCanvas parent, ScreenTransform s, String text) {
        super(parent, s);
        this.text = text;
    }

    @Override
    public synchronized void Render(Graphics2D g, Camera cam) {
        if (visible) {
            ScreenTransform ownTrans = this.getScreenTransform();
            Transform screenCoord = cam.gui2Screen(ownTrans);

            int x = (int) screenCoord.getX();
            int y = (int) screenCoord.getY();

            g.setColor(this.textColor);
            //set alpha
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha));

            // set Font style and size (size must be dependent on screen size)
            float fSize = this.fontSize*(float)cam.getResolution().getWidth()/500;
            g.setFont(font.deriveFont(fontType).deriveFont(fSize));

            int width = g.getFontMetrics().stringWidth(text);
            int height = g.getFontMetrics().getHeight();




            switch (textAlign) {
                case CENTER -> {
                    x = x - width/2;
                    y = y + height/4;
                }
                case LEFT -> y = y + height/4;
                case RIGHT ->{
                    x = x - width;
                    y = y + height/4;
                }
                default -> throw new Error("No TextAlign on TextView is specified!");
            }
            g.drawString(text, x, y + g.getFontMetrics().getHeight() / 2);
        }
    }

    public void setTextColor(Color c) {
        this.textColor = c;
    }

    public void setTextAlign(TextAlign textAlign) {
        this.textAlign = textAlign;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public TextAlign getTextAlign() {
        return textAlign;
    }

    public int getFontType() {
        return fontType;
    }

    public void setFontType(int fontType) {
        this.fontType = fontType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFont(String font) {
        this.font = loadFont(font);
    }

}
