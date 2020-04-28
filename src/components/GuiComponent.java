package components;


import display.Camera;
import game.Consts;
import graphics.SpecifiedAnchor;
import logic.Anchor;
import logic.Dimension2D;
import logic.Transform;
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
    protected Rectangle bounds = new Rectangle(0,0,0,0);

    protected Vector2 lastRenderPos = new Vector2(0,0);
    protected Dimension2D lastRenderWidth = new Dimension2D(0,0);

    public GuiComponent(GuiCanvas parent, ScreenTransform screenTransform) {
        this.screenTransform = screenTransform;
        this.parentGUI = parent;

        setRectFromTransform();
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

    public void setRectFromTransform() {

        ScreenTransform ownTrans = this.getScreenTransform();
        Transform screenCoord = Camera.gui2Screen(ownTrans, new Dimension2D(Consts.windowWidth, Consts.windowHeight));

        int x = (int) (screenCoord.getX());
        int y = (int) (screenCoord.getY());
        int width = (int) (screenCoord.getXScale());
        int height = (int) (screenCoord.getYScale());

        int xOffset = (int) this.anchor.getXOffset(width);
        int yOffset = (int) this.anchor.getYOffset(height);

        x = x-xOffset;
        y = y-yOffset;

        Rectangle r = new Rectangle(Math.min(x, x+width), Math.min(y, y+height), Math.max(x, x+width), Math.max(y, y+height) );
        bounds = r;
    }

    public boolean isPointOnComponent(int x, int y) {
        return x >= bounds.getX() && x <= bounds.getMaxX() &&
                y >= bounds.getY() && y <= bounds.getMaxY();
    }

    public ScreenTransform getScreenTransform() {
        return screenTransform;
    }

    public void addTransform(ScreenTransform other) {
        this.screenTransform = screenTransform.add(other);
        setRectFromTransform();
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
