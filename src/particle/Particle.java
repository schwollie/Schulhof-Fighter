package particle;

import display.Camera;
import game.*;
import graphics.Animation;
import graphics.AnimationLoader;
import logic.Transform;
import logic.Vector2;

import java.awt.*;

public class Particle extends GameComponent {

    private double livingTime;
    private Animation animation;
    private Vector2 pos;

    private ParticleSystem parentSystem;

    public Particle(ParticleSystem parentSystem, Animation animation, Vector2 pos, double livingTime) {
        super(parentSystem.getReference(), ComponentType.Particle);
        this.parentSystem = parentSystem;
        this.animation = animation;
        this.livingTime = livingTime;
        this.pos = pos;
    }

    public Particle(ParticleSystem parentSystem, ParticleType type, Vector2 pos) {
        super(parentSystem.getReference(), ComponentType.Particle);
        this.parentSystem = parentSystem;
        loadStandardParticle(type);
        this.pos = pos;
    }

    private void loadStandardParticle(ParticleType type) {
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
    }

    @Override
    public void Render(Graphics2D g, Camera cam) {
        super.Render(g, cam);
    }
}
