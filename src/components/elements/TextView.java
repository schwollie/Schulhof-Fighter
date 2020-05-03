package components.elements;

import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;
import game.Consts;
import logger.Log;
import logger.MessageType;
import logic.Transform;

import java.awt.*;
import java.util.logging.Logger;

public class TextView extends GuiComponent {

    private Font font = loadFont(Consts.font1);
    private float fontSize = 15;
    private TextAlign textAlign = TextAlign.CENTER;
    private TextAlignVertical textAlignVertical = TextAlignVertical.CENTER;
    private TextAlign anchor = TextAlign.CENTER;
    private TextAlignVertical anchorVertical = TextAlignVertical.CENTER;
    private int fontType = Font.BOLD;
    private String text;
    private Color textColor = Color.WHITE;
    private boolean isCustomFont, positionInRect;

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
            int width = (int) screenCoord.getXScale();
            int height = (int) screenCoord.getYScale();

            g.setColor(this.textColor);
            //set alpha
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha));

            // set Font style and size (size must be dependent on screen size)
            if(!isCustomFont) {
                float fSize = this.fontSize * (float) cam.getResolution().getWidth() / 500;
                g.setFont(font.deriveFont(fontType).deriveFont(fSize));
            }else{
                g.setFont(font);
            }

            int fontWidth = g.getFontMetrics().stringWidth(text);
            int fontHeight = g.getFontMetrics().getHeight();
            //anchor
            switch (anchor) {
                case CENTER -> {
                    x = x + width/2;
                }
                case LEFT -> {
                }
                case RIGHT -> {
                    x = x + width;
                }
                default -> Log.log("Anchor not set for TextView with text::" + text, MessageType.DEBUG);
            }
            switch (anchorVertical) {
                case CENTER -> {
                    y = y + height / 2;
                }
                case TOP -> {
                }
                case BOTTOM -> {
                    y = y + height;
                }
                default -> Log.log("AnchorV not set for TextView with text::" + text, MessageType.DEBUG);
            }
            //textAlign
            switch (textAlign) {
                case CENTER -> {
                    x = x - fontWidth / 2;
                }
                case LEFT -> {
                }
                case RIGHT -> {
                    x = x - fontWidth;
                }
                default -> Log.log("Align not set for TextView with text::" + text, MessageType.DEBUG);
            }
            switch (textAlignVertical) {
                case CENTER -> {
                    y = y + fontHeight / 4;
                }
                case TOP -> {
                }
                case BOTTOM -> {
                    y = y + fontHeight / 2;
                }
                default -> Log.log("AlignV not set for TextView with text::" + text, MessageType.DEBUG);
            }
            g.drawString(text, x, y);
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

    public TextAlignVertical getTextAlignVertical() {
        return textAlignVertical;
    }

    public void setTextAlignVertical(TextAlignVertical textAlignVertical) {
        this.textAlignVertical = textAlignVertical;
    }

    public TextAlign getAnchor() {
        return anchor;
    }

    public void setAnchor(TextAlign anchor) {
        this.anchor = anchor;
    }

    public TextAlignVertical getAnchorVertical() {
        return anchorVertical;
    }

    public void setAnchorVertical(TextAlignVertical anchorVertical) {
        this.anchorVertical = anchorVertical;
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

    public void setCustomFont(Font font) {
        this.font = font;
        this.isCustomFont = true;
    }

    public void setPositionInRect(boolean active) {
        positionInRect = active;
    }

}
