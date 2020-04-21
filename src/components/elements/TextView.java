package components.elements;

import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;
import logic.Dimension2D;
import logic.Transform;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class TextView extends GuiComponent {

    private Font font = loadFont("FjallaOne");
    private float fontSize = 11;
    private TextAlign textAlign = TextAlign.CENTER;
    private int fontType = Font.BOLD;
    private String text;

    public TextView(GuiCanvas parent, ScreenTransform s, String text) {
        super(parent, s);
        this.text = text;
    }

    private Dimension2D getStringBounds(Graphics2D g2, String str,
                                      float x, float y)
    {
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
        Rectangle r = gv.getPixelBounds(null, x, y);
        return new Dimension2D(r.getWidth(), r.getHeight());
    }

    @Override
    public synchronized void Render(Graphics2D g, Camera cam) {
        if (visible) {
            ScreenTransform ownTrans = this.getScreenTransform();
            Transform screenCoord = cam.gui2Screen(ownTrans);

            int x = (int) screenCoord.getX();
            int y = (int) screenCoord.getY();

            //set alpha
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.transparentColor.getAlpha()));

            // set Font style and size (size must be dependent on screen size)
            float fSize = this.fontSize*(float)cam.getResolution().getWidth()/500;
            g.setFont(font.deriveFont(fSize).deriveFont(fontType));

            int width = g.getFontMetrics().stringWidth(text);
            int height = (int)(getStringBounds(g, text, x, y).getHeight() * 1/parentGUI.getRatio());

            switch (textAlign) {
                case CENTER -> g.drawString(text, x - width/2, y - height/2);
                case LEFT -> g.drawString(text, x - width, y- height/2);
                case RIGHT -> g.drawString(text, x, y- height/2);
                default -> throw new Error("No TextAlign on TextView is specified!");
            }
        }
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

    // region not used interface methods:
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
    // endregion

}
