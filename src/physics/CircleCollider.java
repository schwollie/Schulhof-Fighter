package physics;

import display.Canvas;
import game.Consts;
import graphics.CircleSprite;
import graphics.RectSprite;
import logic.Transform;
import logic.Vector2;

public class CircleCollider extends Collider {

    private double radius = 1;
    private int layer = 1;

    public CircleCollider(Transform transform, Vector2 offset,  PhysicsObject physicsObject, double radius) {
        super(transform, offset, physicsObject);
        this.radius = radius;
    }

    @Override
    public void calcForce(PhysicsObject other) {
        if (other.getCollider() instanceof RectCollider) {}
        if (other.getCollider() instanceof CircleCollider) { Collider.resolveCircleVsCircle(this, (CircleCollider) other.getCollider()); }
    }

    @Override
    public void updateSprite(Canvas c) {
        if (debugSprite==null) {
            debugSprite = new CircleSprite(this.transform, this.offset, this.radius);
            c.addSprite(debugSprite);
        }
    }

    public double getRadius() {
        return radius;
    }

    public void setLayer(int newLayer) { this.layer = newLayer; }
}
