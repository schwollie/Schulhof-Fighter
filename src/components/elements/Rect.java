package components.elements;

import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;
import logic.Transform;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Rect extends GuiComponent {

    private Color c = Color.PINK;

    public Rect(GuiCanvas parent, ScreenTransform screenTransform) {
        super(parent, screenTransform);
    }

    @Override
    public void Render(Graphics2D g, Camera cam) {
        if (visible) {
            ScreenTransform ownTrans = this.getScreenTransform();
            Transform screenCoord = cam.gui2Screen(ownTrans);

            int x = (int) screenCoord.getX();
            int y = (int) screenCoord.getY();
            int width = (int) screenCoord.getXScale();
            int height = (int) screenCoord.getYScale();

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha));

            int xOffset = (int) this.anchor.getXOffset(width);
            int yOffset = (int) this.anchor.getYOffset(height);

            x = x - xOffset;
            y = y - yOffset;

            this.lastRenderPos.setValues(x, y);
            this.lastRenderWidth.setValues(width, height);

            g.setColor(c);
            g.fillRect(x, y , width, height);
        }
    }

    @Override
    public void onHoverEnter() {
        this.alpha = .2f;
    }

    @Override
    public void onHoverExit() {
        this.alpha = 1f;
    }
}
