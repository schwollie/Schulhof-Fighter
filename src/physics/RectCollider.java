package physics;

import gameobjects.GameObject;
import graphics.RectSprite;
import graphics.RenderManager;
import logic.Dimension2D;
import logic.Vector2;

public class RectCollider extends Collider {

    Dimension2D dimensions;

    public RectCollider(GameObject reference, Vector2 offset, Dimension2D dimensions) {
        super(reference, offset);
        this.dimensions = dimensions;  // width, height
        checkDimensions();
    }

    @Override
    public void manageCollision(PhysicsGameComponent self, PhysicsGameComponent other) {
        if (other.getCollider() instanceof RectCollider) {
            this.resolveRectVsRect(this, (RectCollider) other.getCollider(), self, other);
        }
        if (other.getCollider() instanceof CircleCollider) {
            return;
        } //Collider.resolveCircleVsRect(this, (CircleCollider)other.getCollider()); }
    }

    @Override
    public boolean doesCollide(Collider c) {
        if (c instanceof RectCollider) {
            return RectVsRect(this, (RectCollider) c);
        }
        if (c instanceof CircleCollider) {
            return CircleVsRect((CircleCollider) c, this);
        }
        throw new Error("Type of collider is unknown");
    }

    @Override
    public void UpdateDrawables(RenderManager renderManager) {
        if (debugSprite == null) {
            debugSprite = new RectSprite(GameObject.getPlaceHolder(this.reference.getTransform(), reference.getScene()), this.offset, this.dimensions);
        } else {
            debugSprite.setGameObjectRef(GameObject.getPlaceHolder(this.reference.getTransform(), reference.getScene()));
        }

        debugSprite.UpdateDrawables(renderManager);
    }

    private void checkDimensions() {
        if (this.dimensions.getWidth() < 0) {
            this.dimensions.setWidth(dimensions.getWidth()*-1);
            this.offset = this.offset.add(new Vector2(-dimensions.getWidth(), 0));
        }

        if (this.dimensions.getHeight() < 0) {
            this.dimensions.setHeight(dimensions.getHeight()*-1);
            this.offset = this.offset.add(new Vector2(0, -dimensions.getHeight()));
        }
    }

    public Dimension2D getDimensions() {
        return dimensions;
    }

    public double getX() {
        return this.offset.getX() + reference.getTransform().getX();
    }

    public double getY() {
        return this.offset.getY() + reference.getTransform().getY();
    }

    public double getWidth() {
        return this.dimensions.getWidth();
    }

    public double getHeight() {
        return this.dimensions.getHeight();
    }
}

