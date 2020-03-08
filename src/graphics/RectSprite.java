package graphics;

import display.Camera;
import game.GameObject;
import logic.Transform;
import logic.Vector2;

import java.awt.*;

public class RectSprite extends Sprite {

    private Vector2 Size;
    private Color color = new Color(0, 0, 0);

    public RectSprite(GameObject reference, Vector2 offset, Vector2 dimensions) {
        this.gameObjectRef = reference;
        this.Size = dimensions;
        this.offset = offset;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public synchronized void draw(Graphics2D g, Camera cam) {
        if (!visible) { return; }

        Transform ownTrans = this.getTransform().addPosition(offset);
        ownTrans.setSize(new Vector2(Size.getX(), Size.getY()));
        Transform screenCoord = cam.worldToScreen(ownTrans);

        g.setColor(this.color);
        g.fillRect((int)screenCoord.getX(), (int)screenCoord.getY(), (int)screenCoord.getXScale(), (int) screenCoord.getYScale());
    }
}
