package physics;

import display.Canvas;
import game.Consts;
import game.GameObject;
import game.GameWorld;
import graphics.CircleSprite;
import logic.Transform;
import logic.Vector2;

public class CircleCollider extends Collider {

    private double radius = 1;
    private int layer = 1;

    public CircleCollider(GameObject reference, Vector2 offset, double radius) {
        super(reference, offset);
        this.radius = radius;
    }

    @Override
    public void manageCollision(PhysicsObject self, PhysicsObject other) {
        if (other.getCollider() instanceof RectCollider) { return; }//Collider.resolveCircleVsRect((RectCollider)other.getCollider(), this);}
        if (other.getCollider() instanceof CircleCollider) { this.resolveCircleVsCircle(this, (CircleCollider) other.getCollider(), self, other); }
    }

    @Override
    public boolean doesCollide(Collider c) {
        if (c instanceof RectCollider) { return CircleVsRect(this, (RectCollider) c); }
        if (c instanceof CircleCollider) { return CircleVsCircle(this, (CircleCollider)c); }
        throw new Error("Type of collider is unknown");
    }

    @Override
    public void updateSprite(GameWorld g) {
        if (debugSprite==null) {
            debugSprite = new CircleSprite(GameObject.getPlaceHolder(this.gameObjectRef.getTransform()), this.offset, this.radius);
            g.addSprite(debugSprite);
        } else {
            debugSprite.setGameObjectRef(GameObject.getPlaceHolder(this.gameObjectRef.getTransform()));
        }
    }

    public double getRadius() {
        return radius;
    }

    public void setLayer(int newLayer) { this.layer = newLayer; }
}
