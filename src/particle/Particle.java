package particle;

import game.Consts;
import game.GameObject;
import game.Scene;
import graphics.Animation;
import graphics.AnimationLoader;
import logic.Transform;
import logic.Vector2;

public class Particle extends GameObject {
    private double livingTime;
    private Animation animation;

    public Particle(Animation animation, Scene world, Vector2 pos, double livingTime) {
        super("Particle", world);
        this.animation = animation;
        this.livingTime = livingTime;
        this.transform = new Transform(pos);
    }

    public Particle(ParticleType type, Scene world, Vector2 pos) {
        super("Particle", world);
        loadStandardParticle(type);
        this.transform = new Transform(pos);
    }

    private void loadStandardParticle(ParticleType type) {
        switch (type) {
            case PUNCH:
                animation = AnimationLoader.loadParticleAnimation(ParticleType.PUNCH, Consts.punchParticlePicCount, Consts.punchParticleAnimSpeed, Consts.punchParticleSize, this, true);
                livingTime = Consts.punchParticleLivingTime;
                break;
            default:
                throw new Error("Particle not found : " + type.name());
        }
    }

}
