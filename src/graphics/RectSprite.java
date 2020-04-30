package graphics;

import display.Camera;
import gameobjects.GameObject;
import logic.Dimension2D;
import logic.Transform;
import logic.Vector2;

import java.awt.*;

public class RectSprite extends Sprite {

    private final Dimension2D Size;
    private Color color = new Color(0, 0, 0);

    public RectSprite(GameObject reference, Vector2 offset, Dimension2D dimensions) {
        super(reference);
        this.Size = dimensions;
        this.offset = offset;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public synchronized void Render(Graphics2D g, Camera cam) {
        if (!visible) {
            return;
        }

        Transform ownTrans = this.getAbsoluteTransform();
        ownTrans.setScale(new Vector2(Size.getWidth(), Size.getHeight()));
        Transform screenCoord = cam.world2Screen(ownTrans);

        int x = (int) screenCoord.getX();
        int y = (int) screenCoord.getY();
        int width = (int) screenCoord.getXScale();
        int height = -(int) (screenCoord.getYScale());

        if (width < 0) {
            x -= Math.abs(width);
        }
        if (height < 0) {
            y -= Math.abs(height);
        }

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha));
        g.setColor(this.color);
        g.fillRect(x, y, Math.abs(width), Math.abs(height));
    }
}
