package particle;

import display.Camera;
import game.*;
import graphics.Animation;
import graphics.ImageSprite;
import graphics.SpriteManager;
import logic.Vector2;

import java.awt.*;

public class Particle extends GameComponent {

    private double livingTime;
    //private Animation animation;

    private final ParticleSystem parentSystem;
    private Vector2 force = new Vector2(0,0);

    public Particle(ParticleSystem parentSystem, Animation animation, Vector2 pos, double livingTime) {
        super(parentSystem.getReference(), ComponentType.Particle);
        this.parentSystem = parentSystem;
        //this.animation = animation;
        this.livingTime = livingTime;
        this.transform.setPosition(pos);
    }

    public Particle(ParticleSystem parentSystem, ParticleType type, Vector2 pos, Vector2 force) {
        super(parentSystem.getReference(), ComponentType.Particle);
        this.parentSystem = parentSystem;
        //loadStandardParticle(type);
        this.transform.setPosition(pos);
        this.transform.setScale(new Vector2(0.1, 0.1));
        this.force = force;
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

        this.transform = this.transform.addPosition(force.scalarMult(dt));
    }

    @Override
    public void UpdateSprites(SpriteManager spriteManager) {
        ImageSprite sprite2draw = parentSystem.getParticleImg();
        sprite2draw.setRelativeTransform(this.transform);
        sprite2draw.UpdateSprites(spriteManager);
    }
}
