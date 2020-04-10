package particle;

import game.GameObject;
import game.Scene;
import logic.Transform;
import logic.Vector2;
import time.TimeEventListener;
import time.Timer;

import java.util.ArrayList;

public class ParticleSystem extends GameObject implements TimeEventListener {
    private final Timer timer;
    private final String timerName;
    private double liveTime, spread;
    private ArrayList<Particle> particles;
    private ParticleType particleType;

    public ParticleSystem(Scene scene, Vector2 pos, double liveTime, double interval, ParticleType type) {
        super("Particle System", scene);
        this.transform = new Transform(pos);
        this.liveTime = liveTime;
        this.timerName = "spawnParticle";
        this.timer = new Timer(timerName, interval, this);
        this.particles = new ArrayList<>();
    }

    @Override
    public void tick() {
        super.tick();
        double dt = getTime().getDeltaTime();
        timer.tick(dt);
    }

    @Override
    public void onTimerStops(String timerName) {
        if (timerName.equals(timerName)) {
            if (timer.getElapsedTime() < liveTime) {
                timer.resetTime();
                spawnParticle();
            } else {
                destroy();
            }
        }
    }

    private void spawnParticle() {
        Particle particle = new Particle(ParticleType.PUNCH, scene, transform.getPosition());
        particles.add(particle);
    }

    public void destroy() {
        for (Particle p : particles) {
            p.destroy();
        }
        destroy();
    }
}
