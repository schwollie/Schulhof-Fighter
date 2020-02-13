package physics;

import display.Canvas;
import graphics.RectSprite;
import graphics.Sprite;
import logic.Transform;
import logic.Vector2;

public class RectCollider extends Collider {

    Vector2 dimensions;

    public RectCollider(Transform transform, Vector2 offset, PhysicsObject physicsObject, Vector2 dimensions) {
        super(transform, offset, physicsObject);
        this.dimensions = dimensions;  // width, height
    }

    @Override
    public void calcForce(PhysicsObject other) {
        if (other.getCollider() instanceof RectCollider) { Collider.resolveRectVsRect(this, (RectCollider) other.getCollider());}
        if (other.getCollider() instanceof CircleCollider) { return; } //Collider.resolveCircleVsRect(this, (CircleCollider)other.getCollider()); }
    }

    @Override
    public void updateSprite(Canvas c) {
        if (debugSprite==null) {
            debugSprite = new RectSprite(this.transform, this.offset, this.dimensions);
            c.addSprite(debugSprite);
        }
    }

    public Vector2 getDimensions() {
        return dimensions;
    }
}

