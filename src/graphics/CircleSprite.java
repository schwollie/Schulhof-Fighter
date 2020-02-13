package graphics;

import logic.Transform;
import logic.Vector2;

import java.awt.*;

public class CircleSprite extends Sprite {

    private Vector2 offset;
    private double radius;

    public CircleSprite(Transform transform, Vector2 offset, double radius) {
        this.transform = transform;
        this.offset = offset;
        this.radius = radius;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(200, 20, 50));
        g.fillOval((int)transform.getX()+(int)offset.getX(), (int)transform.getY()+(int)offset.getY(), (int)radius*2, (int) radius*2);
    }
}
