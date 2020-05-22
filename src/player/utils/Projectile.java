package player.utils;

import animation.SimpleAnimationManager;
import gameobjects.GameObject;
import logic.Dimension2D;
import logic.Transform;
import logic.Vector2;
import physics.Collider;
import physics.CollissionListener;
import physics.PhysicsGameComponent;
import physics.RectCollider;
import player.Player;
import scenes.Scene;

public class Projectile extends GameObject implements CollissionListener {

    private final double damage = 2;
    private final Vector2 force = new Vector2(3, 3);

    private SimpleAnimationManager animationManager;
    private PhysicsGameComponent physics;
    private double speed;
    private GameObject sender;

    public Projectile(Scene world, GameObject sender, Vector2 pos, double speed, double dir) {
        super("Projectile", world);
        this.speed = speed;
        this.sender = sender;
        this.transform = new Transform(pos);
        this.transform.setPosition(pos);
        this.transform.setScale(new Vector2(.2, .2));

        this.transform.setScale(new Vector2(Math.abs(transform.getXScale()) * dir, transform.getYScale()));

        physics = new PhysicsGameComponent(this);
        this.setPhysicsComponent(physics);
        physics.setVelocityY(.7);
        physics.setMass(0.1);


        RectCollider rc = new RectCollider(this, new Vector2(.1, .1), Dimension2D.valueOf(this.getTransform().getScale()));
        rc.addListener(this);
        rc.setTrigger(true);
        physics.setCollider(rc);

        //rc  =new RectCollider(this, new Vector2(1,1), Dimension2D.valueOf(this.getTransform().getScale()));

        animationManager = new SimpleAnimationManager("images/Hausperger/projectileSheet.png", 2, 8,
                new Dimension2D(500, 250), this);
        this.addComponent(animationManager);

        // destroy this projectile after 10 seconds
        this.destroy(10);
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
        if (c1.getReference().equals(this) && c2.getReference() instanceof Player && !c2.getReference().equals(sender)) {
            Player target = (Player) c2.getReference();
            target.receiveHit(damage, force, sender);
            scene.removeGameObject(this);
        }
    }
}
