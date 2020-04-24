package player;

import game.Consts;
import gameobjects.GameObject;
import scenes.Scene;
import graphics.ImageSprite;
import animation.SimpleAnimationManager;
import logic.Dimension2D;
import logic.Transform;
import logic.Vector2;
import physics.Collider;
import physics.CollissionListener;
import physics.PhysicsGameComponent;

public class Projectile extends GameObject implements CollissionListener {

    private static ImageSprite[] sprites;
    private SimpleAnimationManager animationManager;
    private PhysicsGameComponent physics;
    private double speed;

    public Projectile(String tag, Scene world, Vector2 pos, double speed) {
        super(tag, world);
        this.speed = speed;
        this.transform = new Transform(pos);
        this.transform.setPosition(pos);
        this.transform.setScale(new Vector2(.2, .2));


        physics = new PhysicsGameComponent(this);
        this.setPhysicsComponent(physics);
        physics.setVelocityY(2);
        physics.setMass(.000001);

        if (sprites == null) {
            animationManager = new SimpleAnimationManager("images/Hausperger/runSheet.png", 8, 8, new Dimension2D(50, 50), this);
            sprites = animationManager.getAnimation().getImages();
        } else {
            animationManager = new SimpleAnimationManager(sprites, 8, this);
        }
        this.addComponent(animationManager);
    }

    public Projectile(String tag, Scene world) {
        super(tag, world);
        animationManager = new SimpleAnimationManager("images/Hausperger/runSheet.png", 8, 8, Consts.hauspergerCharacterSize, this);
    }

    @Override
    public void Tick() {
        super.Tick();
        animationManager.tick();
        physics.tick();
        this.physicsComponent.setVelocityX(speed);
    }

    @Override
    public void onCollision(Collider c1, Collider c2) {

    }
}
