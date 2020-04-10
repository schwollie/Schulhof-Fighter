package particle;

import display.Camera;
import game.*;
import graphics.Animation;
import graphics.AnimationLoader;
import graphics.ImageSprite;
import logic.Transform;
import logic.Vector2;

import java.awt.*;

public class Particle extends GameComponent {

    private double livingTime;
    //private Animation animation;

    private final ParticleSystem parentSystem;

    public Particle(ParticleSystem parentSystem, Animation animation, Vector2 pos, double livingTime) {
        super(parentSystem.getReference(), ComponentType.Particle);
        this.parentSystem = parentSystem;
        //this.animation = animation;
        this.livingTime = livingTime;
        this.transform.setPosition(pos);
    }

    public Particle(ParticleSystem parentSystem, ParticleType type, Vector2 pos) {
        super(parentSystem.getReference(), ComponentType.Particle);
        this.parentSystem = parentSystem;
        //loadStandardParticle(type);
        this.transform.setPosition(pos);
        this.transform.setScale(new Vector2(0.1, 0.1));
    }

    /*private void loadStandardParticle(ParticleType type) {
        switch (type) {
            case PUNCH:
                animation = AnimationLoader.loadParticleAnimation(ParticleType.PUNCH,
                        Consts.punchParticlePicCount, Consts.punchParticleAnimSpeed,
                        Consts.punchParticleSize, parentSystem.getReference(), true);
                livingTime = Consts.punchParticleLivingTime;
                break;
            default:
                throw new Error("Particle not found : " + type.name());
        }
    }*/

    @Override
    public void tick() {
        double dt = reference.getTime().getDeltaTime();

        this.transform = this.transform.addPosition(Vector2.randomVec(0, dt*10));
    }

    @Override
    public void Render(Graphics2D g, Camera cam) {
        ImageSprite sprite2draw = parentSystem.getParticleImg();
        sprite2draw.setRelativeTransform(this.transform);
        sprite2draw.Render(g, cam);
    }
}
