package graphics;

import logic.Transform;
import logic.Vector2;

import java.awt.*;

public class RectSprite extends Sprite {

    private Vector2 offset;
    private Vector2 dimensions;

    public RectSprite(Transform transform, Vector2 offset, Vector2 dimensions) {
        this.transform = transform;
        this.dimensions = dimensions;
        this.offset = offset;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(200, 20, 50));
        g.fillRect((int)transform.getX()+(int)offset.getX(), (int)transform.getY()+(int)offset.getY(),
                (int)dimensions.getX(), (int)dimensions.getY());
    }
}
