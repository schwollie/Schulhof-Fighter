package physics;

import display.Camera;
import game.GameObject;
import game.Scene;
import graphics.RectSprite;
import logic.Dimension2D;
import logic.Vector2;

import java.awt.*;

public class RectCollider extends Collider {

    Dimension2D dimensions;

    public RectCollider(GameObject reference, Vector2 offset, Dimension2D dimensions) {
        super(reference, offset);
        this.dimensions = dimensions;  // width, height
    }

    @Override
    public void manageCollision(PhysicsComponent self, PhysicsComponent other) {
        if (other.getCollider() instanceof RectCollider) { this.resolveRectVsRect(this, (RectCollider) other.getCollider(), self, other);}
        if (other.getCollider() instanceof CircleCollider) { return; } //Collider.resolveCircleVsRect(this, (CircleCollider)other.getCollider()); }
    }

    @Override
    public boolean doesCollide(Collider c) {
        if (c instanceof RectCollider) { return RectVsRect(this, (RectCollider) c); }
        if (c instanceof CircleCollider) { return CircleVsRect((CircleCollider)c, this); }
        throw new Error("Type of collider is unknown");
    }

    @Override
    public void Render(Graphics2D g, Camera cam) {
        if (debugSprite==null) {
            debugSprite = new RectSprite(GameObject.getPlaceHolder(this.reference.getTransform(), reference.getScene()), this.offset, this.dimensions.asVector());
        } else {
            debugSprite.setGameObjectRef(GameObject.getPlaceHolder(this.reference.getTransform(), reference.getScene()));
        }

        debugSprite.Render(g, cam);
    }

    public Dimension2D getDimensions() {
        return dimensions;
    }

    public double getX() {
        return this.offset.getX();
    }

    public double getY() {
        return this.offset.getY();
    }

    public double getWidth() {
        return this.dimensions.getWidth();
    }

    public double getHeight() {
        return this.dimensions.getHeight();
    }
}

