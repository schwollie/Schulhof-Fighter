package components;


import logic.Dimension2D;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class GuiComponent implements ComponentMethods {
    private final int UNUSED = -Integer.MAX_VALUE;
    protected final Color transparentColor = new Color(0, 0, 0, 1);
    protected boolean pressed, hovered;
    protected Color color, textColor;
    protected GuiCanvas parentGUI;
    protected boolean visible = true;
    protected boolean preserveAspect = true;
    protected ScreenTransform screenTransform;

    public GuiComponent(ScreenTransform screenTransform) {
        this.screenTransform = screenTransform;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public double getX() {
        return this.screenTransform.getPos().getX();
    }

    public double getY() {
        return this.screenTransform.getPos().getY();
    }

    public double getWidth() {
        return this.screenTransform.getScale().getX();
    }

    public double getHeight() {
        return this.screenTransform.getScale().getY();
    }

    public int getInPixelX(Dimension2D resolution) {
        return (int)(this.screenTransform.getPos().getX() * resolution.getWidth());
    }

    public int getInPixelY(Dimension2D resolution) {
        return (int)(this.screenTransform.getPos().getY() * resolution.getHeight());
    }

    public int getInPixelWidth(Dimension2D resolution) {
        return (int)(this.screenTransform.getScale().getX()*resolution.getWidth());
    }

    public int getInPixelHeight(Dimension2D resolution) {
        return (int)(this.screenTransform.getScale().getY()*resolution.getHeight());
    }

    public ScreenTransform getScreenTransform() {
        return screenTransform;
    }

    public void setVisible() { this.visible = true; }

    public void hideElement() { this.visible = false; }

    public GuiCanvas getParentGUI() {
        return parentGUI;
    }

    public void setParentGUI(GuiCanvas parentGUI) {
        this.parentGUI = parentGUI;
    }

    protected String testStringLength(String text, FontMetrics fontMetrics) {
        String result = text;
        while (fontMetrics.stringWidth(result) > getWidth()) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    public static Font loadFont(String fontName) {
        InputStream stream;
        Font font = null;
        try {
            stream = new FileInputStream(new File("fonts/" + fontName + ".ttf"));
            font = Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.err.println("Could not load font");
        }
        return font;
    }
}
