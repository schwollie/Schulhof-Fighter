package particle;

import game.ComponentType;
import game.GameComponent;
import game.GameObject;
import game.Scene;
import logic.Transform;
import logic.Vector2;
import time.TimeEventListener;
import time.Timer;

import java.util.ArrayList;
import java.util.LinkedList;

public class ParticleSystem extends GameComponent implements TimeEventListener {

    private final Timer timer;
    private final String timerName;
    private double liveTime, spread;
    private LinkedList<Particle> particles;
    private ParticleType particleType;

    public ParticleSystem(GameObject ref, double liveTime, double interval, ParticleType type) {
        super(ref, ComponentType.ParticleSystem);
        this.liveTime = liveTime;
        this.timerName = "spawnParticle";
        this.timer = new Timer(timerName, interval, this);
        this.particles = new LinkedList<>();
    }

    @Override
    public void tick() {
        double dt = reference.getTime().getDeltaTime();
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
        Particle particle = new Particle(this, ParticleType.PUNCH, new Vector2(0,0));
        particles.add(particle);
    }

    public void destroy() {
        for (Particle p : particles) {
            p.destroy();
        }
        //super.destroy();
    }
}
