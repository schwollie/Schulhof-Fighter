package components;


import graphics.SpecifiedAnchor;
import logic.Anchor;
import logic.Dimension2D;
import logic.Vector2;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class GuiComponent implements ComponentMethods {
    private final int UNUSED = -Integer.MAX_VALUE;
    protected float alpha = 1f;
    protected GuiCanvas parentGUI;
    protected boolean visible = true;
    protected boolean preserveAspect = true;
    protected ScreenTransform screenTransform;
    protected Anchor anchor = new Anchor(SpecifiedAnchor.TopLeft);

    protected Vector2 lastRenderPos = new Vector2(0,0);
    protected Dimension2D lastRenderWidth = new Dimension2D(0,0);

    public GuiComponent(GuiCanvas parent, ScreenTransform screenTransform) {
        this.screenTransform = screenTransform;
        this.parentGUI = parent;
    }

    public void setAlpha(float a) {
        this.alpha = Math.min(Math.max(0, a), 1);
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

    //TODO: dont do that with the last render position!!
    public int getInPixelX(Dimension2D resolution) {
        //double x = this.screenTransform.getPos().getX() * resolution.getWidth() + this.anchor.getXOffset( getInPixelWidth(resolution) );
        return (int)lastRenderPos.getX();
    }

    public int getInPixelY(Dimension2D resolution) {
        //double x = this.screenTransform.getPos().getY() * resolution.getHeight() - this.anchor.getYOffset( getInPixelHeight(resolution) );
        return (int)lastRenderPos.getY();
    }

    public int getInPixelWidth(Dimension2D resolution) {
        //return (int)(this.screenTransform.getScale().getX()*resolution.getWidth());
        return (int)lastRenderWidth.getWidth();
    }

    public int getInPixelHeight(Dimension2D resolution) {
        //return (int)(this.screenTransform.getScale().getY()*resolution.getWidth()*1/parentGUI.getRatio());
        return (int)lastRenderWidth.getHeight();
    }

    public ScreenTransform getScreenTransform() {
        return screenTransform;
    }

    public void addTransform(ScreenTransform other) {
        this.screenTransform = screenTransform.add(other);
    }

    public void setVisible() { this.visible = true; }

    public void hideElement() { this.visible = false; }

    public GuiCanvas getParentGUI() {
        return parentGUI;
    }

    public void setParentGUI(GuiCanvas parentGUI) {
        this.parentGUI = parentGUI;
    }

    protected String getAdaptedStringLength(String text, FontMetrics fontMetrics) {
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
