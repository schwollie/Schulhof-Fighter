package graphics;

import display.Camera;
import game.GameObject;
import logic.Transform;
import logic.Vector2;

import java.awt.*;

public class CircleSprite extends Sprite {

    private double radius;
    private Color color = new Color(0, 255, 0);

    public CircleSprite(GameObject reference, Vector2 offset, double radius) {
        this.gameObjectRef = reference;
        this.offset = offset;
        this.radius = radius;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public synchronized void draw(Graphics2D g, Camera cam) {
        if (!visible) { return; }

        Transform ownTrans = this.getTransform().addPosition(offset);
        ownTrans.setSize(new Vector2(radius, radius));
        Transform screenCoord = cam.worldToScreen(ownTrans);

        g.setColor(this.color);
        g.fillOval((int)screenCoord.getX(), (int)screenCoord.getY(), (int)screenCoord.getXScale(), (int) screenCoord.getYScale());
    }
}
