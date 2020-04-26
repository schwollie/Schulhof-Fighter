package player.utils;

import animation.SimpleAnimationManager;
import gameobjects.GameObject;
import logic.Dimension2D;
import logic.Transform;
import logic.Vector2;
import physics.Collider;
import physics.CollissionListener;
import physics.PhysicsGameComponent;
import scenes.Scene;

public class Projectile extends GameObject implements CollissionListener {

    private SimpleAnimationManager animationManager;
    private PhysicsGameComponent physics;
    private double speed;

    public Projectile(String tag, Scene world, Vector2 pos, double speed, double dir) {
        super(tag, world);
        this.speed = speed;
        this.transform = new Transform(pos);
        this.transform.setPosition(pos);
        this.transform.setScale(new Vector2(.2, .2));

        this.transform.setScale(new Vector2(Math.abs(transform.getXScale()) * dir, transform.getYScale()));

        physics = new PhysicsGameComponent(this);
        this.setPhysicsComponent(physics);
        physics.setVelocityY(1);
        physics.setMass(0.2);

        animationManager = new SimpleAnimationManager("images/Hausperger/projectileSheet.png", 2, 8, new Dimension2D(500, 250), this);
        this.addComponent(animationManager);
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
