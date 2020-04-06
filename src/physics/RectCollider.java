package physics;

import game.GameObject;
import game.GameWorld;
import graphics.RectSprite;
import logic.Vector2;

public class RectCollider extends Collider {

    Vector2 dimensions;

    public RectCollider(GameObject reference, Vector2 offset, Vector2 dimensions) {
        super(reference, offset);
        this.dimensions = dimensions;  // width, height
    }

    @Override
    public void manageCollision(PhysicsObject self, PhysicsObject other) {
        if (other.getCollider() instanceof RectCollider) { this.resolveRectVsRect(this, (RectCollider) other.getCollider(), self, other);}
        if (other.getCollider() instanceof CircleCollider) { return; } //Collider.resolveCircleVsRect(this, (CircleCollider)other.getCollider()); }
    }

    @Override
    public void updateSprite(GameWorld g) {
        if (debugSprite==null) {
            debugSprite = new RectSprite(GameObject.getPlaceHolder(this.gameObjectRef.getTransform()), this.offset, this.dimensions);
            g.addSprite(debugSprite);
        } else {
            debugSprite.setGameObjectRef(GameObject.getPlaceHolder(this.gameObjectRef.getTransform()));
        }
    }

    public Vector2 getDimensions() {
        return dimensions;
    }
}

