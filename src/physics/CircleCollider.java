package physics;

import display.Camera;
import game.GameObject;
import graphics.CircleSprite;
import graphics.SpriteManager;
import logic.Vector2;

import java.awt.*;

public class CircleCollider extends Collider {

    private double radius = 1;
    private int layer = 1;

    public CircleCollider(GameObject reference, Vector2 offset, double radius) {
        super(reference, offset);
        this.radius = radius;
    }

    @Override
    public void manageCollision(PhysicsGameComponent self, PhysicsGameComponent other) {
        if (other.getCollider() instanceof RectCollider) {
            return;
        }//Collider.resolveCircleVsRect((RectCollider)other.getCollider(), this);}
        if (other.getCollider() instanceof CircleCollider) {
            this.resolveCircleVsCircle(this, (CircleCollider) other.getCollider(), self, other);
        }
    }

    @Override
    public boolean doesCollide(Collider c) {
        if (c instanceof RectCollider) {
            return CircleVsRect(this, (RectCollider) c);
        }
        if (c instanceof CircleCollider) {
            return CircleVsCircle(this, (CircleCollider) c);
        }
        throw new Error("Type of collider is unknown");
    }

    @Override
    public void UpdateSprites(SpriteManager spriteManager) {
        if (debugSprite == null) {
            debugSprite = new CircleSprite(GameObject.getPlaceHolder(this.reference.getTransform(), reference.getScene()), this.offset, this.radius);
        } else {
            debugSprite.setGameObjectRef(GameObject.getPlaceHolder(this.reference.getTransform(), reference.getScene()));
        }

        debugSprite.UpdateSprites(spriteManager);
    }

    public double getRadius() {
        return radius;
    }

    public void setLayer(int newLayer) {
        this.layer = newLayer;
    }
}
