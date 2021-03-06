package graphics;

import display.Camera;
import gameobjects.GameObject;
import logic.Transform;
import logic.Vector2;

import java.awt.*;

public class CircleSprite extends Sprite {

    private final double radius;
    private Color color = new Color(0, 255, 0);

    public CircleSprite(GameObject reference, Vector2 offset, double radius) {
        super(reference);
        this.offset = offset;
        this.radius = radius;
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
        ownTrans.setScale(new Vector2(radius, radius));
        Transform screenCoord = cam.world2Screen(ownTrans);

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha));
        g.setColor(this.color);
        g.fillOval((int) screenCoord.getX(), (int) screenCoord.getY(), (int) screenCoord.getXScale(), (int) screenCoord.getYScale());
    }
}
