package particle;

import logic.Transform;
import logic.Vector2;
import logic.XRange;

import java.util.LinkedList;
import java.util.Random;

public class CircleEmitter {

    private Transform transform;
    private XRange force;
    private XRange particleAmount;

    public CircleEmitter(Transform transform, XRange particleAmount, XRange force) {
        this.transform = transform;
        this.force = force;
        this.particleAmount = particleAmount;
    }

    public LinkedList<Particle> emmitParticles(ParticleSystem particleSystem) {

        LinkedList<Particle> particles = new LinkedList<>();

        int particleNum = (int)particleAmount.getRandom();

        for (int i = 0; i<particleNum; i++) {
            double w = i/(double)particleNum * Math.PI * 2;

            Vector2 force = new Vector2(Math.cos(w), Math.sin(w)).scalarMult(this.force.getRandom());

            Transform transform = new Transform();
            transform.setPosition(new Vector2(0,0));
            transform.setScale(new Vector2(0.1, 0.1));

            Particle particle = new Particle(particleSystem, transform, force);

            particles.add(particle);
        }

        return particles;

    }



}
