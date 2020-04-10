package graphics;

import display.Camera;
import game.GameObject;
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

        Transform ownTrans = this.getTransform().addPosition(this.offset);
        ownTrans.setScale(new Vector2(Size.getWidth(), Size.getHeight()));
        Transform screenCoord = cam.worldToScreen(ownTrans);

        int x = (int) screenCoord.getX();
        int y = (int) screenCoord.getY();
        int width = (int) screenCoord.getXScale();
        int height = -(int) (screenCoord.getYScale());

        g.setColor(this.color);
        g.fillRect(x, y, width, height);
    }
}
