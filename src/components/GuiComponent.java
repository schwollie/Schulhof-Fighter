package components;


import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class GuiComponent implements ComponentMethods {
    private final int UNUSED = -Integer.MAX_VALUE;
    protected final Color transparentColor = new Color(0, 0, 0, 0);
    private double left, right, top, bottom;
    private double width, height;
    protected float textSize;
    protected TextAlign textAlign;
    protected String name, text;
    protected boolean pressed, hovered;
    protected Panel parentPanel;
    protected Color color, textColor;

    public GuiComponent(double width, double height) {
        this.left = UNUSED;
        this.right = UNUSED;
        this.bottom = UNUSED;
        this.top = UNUSED;
        this.width = width;
        this.height = height;
        this.textAlign = TextAlign.LEFT;
        this.textSize = 20;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        this.top = top;
    }

    public double getBottom() {
        return bottom;
    }

    public void setBottom(double bottom) {
        this.bottom = bottom;
    }

    public double getWidthInPercentage() {
        return width;
    }

    public void setWidthInPercentage(double width) {
        this.width = width;
    }

    public double getHeightInPercentage() {
        return height;
    }

    public void setHeightInPercentage(double height) {
        this.height = height;
    }

    public TextAlign getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(TextAlign textAlign) {
        this.textAlign = textAlign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
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

    public int getX() {
        double factor = left == UNUSED ? (right == UNUSED ? 0 : right / 100) : left / 100;
        return (int) (parentPanel.getWidth() * factor);
    }

    public int getY() {
        double factor = top == UNUSED ? (bottom == UNUSED ? 0 : bottom / 100) : top / 100;
        return (int) (parentPanel.getWidth() * factor);
    }

    public int getWidth() {
        return (int) (parentPanel.getWidth() * (width / 100));
    }

    public int getHeight() {
        return (int) (parentPanel.getWidth() * (height / 100));
    }

    public Panel getParentPanel() {
        return parentPanel;
    }

    public void setParentPanel(Panel parentPanel) {
        this.parentPanel = parentPanel;
    }

    protected String testStringLength(String text, FontMetrics fontMetrics) {
        String result = text;
        while (fontMetrics.stringWidth(result) > getWidth()) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    public enum TextAlign {
        CENTER, LEFT, RIGHT
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
