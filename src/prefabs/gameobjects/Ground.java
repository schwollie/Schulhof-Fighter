package prefabs.gameobjects;

import game.GameObject;
import game.Scene;
import graphics.RectSprite;
import logic.Dimension2D;
import logic.Vector2;
import physics.PhysicsGameComponent;
import physics.RectCollider;

public class Ground extends GameObject {

    public Ground(Scene scene) {
        super("ground", scene);
        setup();
    }

    private void setup() {
        PhysicsGameComponent groundCollider = new PhysicsGameComponent(this);
        this.setPhysicsComponent(groundCollider);
        groundCollider.setStatic(true);
        groundCollider.setCollider(new RectCollider(this, new Vector2(0, 0), new Dimension2D(10, .1)));
        this.addComponent(new RectSprite(this, new Vector2(0, 0), new Dimension2D(10, .1)));

        this.getTransform().addPosition(new Vector2(0, -1));
    }
}
