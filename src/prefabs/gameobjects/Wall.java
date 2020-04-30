package prefabs.gameobjects;

import gameobjects.GameObject;
import logic.Dimension2D;
import logic.Vector2;
import physics.PhysicsGameComponent;
import physics.RectCollider;
import scenes.Scene;

public class Wall extends GameObject {

    public Wall(Scene world, Vector2 pos, Dimension2D d) {
        super("Wall", world, 5);


        this.transform.setPosition(pos);

        this.physicsComponent = new PhysicsGameComponent(this);
        this.physicsComponent.setStatic(true);
        this.physicsComponent.setCollider(new RectCollider(this, new Vector2(0, 0), d));

        //RectSprite p = new RectSprite(this, new Vector2(0,0), d);

        //this.addComponent(p);
    }
}
