package graphics;

import display.Camera;
import game.GameObject;
import logic.Dimension2D;
import logic.Transform;
import logic.Vector2;

import java.awt.*;

public class RectSprite extends Sprite {

    private Dimension2D Size;
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
        if (!visible) { return; }

        Transform ownTrans = this.getTransform().addPosition(offset);
        ownTrans.setScale(new Vector2(Size.getWidth(), Size.getHeight()));
        Transform screenCoord = cam.worldToScreen(ownTrans);

        g.setColor(this.color);
        g.fillRect((int)screenCoord.getX(), (int)screenCoord.getY(), (int)screenCoord.getXScale(), (int) screenCoord.getYScale());
    }
}
