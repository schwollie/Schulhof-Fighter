package particle;

import display.Camera;
import game.ComponentType;
import game.GameComponent;
import game.GameObject;
import game.Scene;
import graphics.ImageSprite;
import logic.Dimension2D;
import logic.Transform;
import logic.Vector2;
import time.TimeEventListener;
import time.Timer;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class ParticleSystem extends GameComponent implements TimeEventListener {

    private final Timer timer;
    private final String timerName;
    private double liveTime, spread;
    private LinkedList<Particle> particles;
    private ParticleType particleType;

    private ImageSprite particleImg;

    public ParticleSystem(GameObject ref, double liveTime, double interval, ParticleType type) {
        super(ref, ComponentType.ParticleSystem);
        this.liveTime = liveTime;
        this.timerName = "spawnParticle";
        this.timer = new Timer(timerName, interval, this);
        this.particles = new LinkedList<>();

        particleImg = new ImageSprite(ref, new Dimension2D(64, 64), "images/Particles/particle.png");
        spawnParticle();
    }

    @Override
    public void tick() {
        double dt = reference.getTime().getDeltaTime();
        timer.tick(dt);

        for (Particle p: particles) {
            p.tick();
        }
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
        for (int i = 0; i < 500; i++) {
            Particle particle = new Particle(this, ParticleType.PUNCH, new Vector2(1,0));
            particles.add(particle);
        }
    }

    public void destroy() {
        for (Particle p : particles) {
            p.destroy();
        }
        //super.destroy();
    }

    public ImageSprite getParticleImg() {
        return this.particleImg;
    }

    @Override
    public void Render(Graphics2D g, Camera cam) {
        for (Particle p: particles) {
            p.Render(g, cam);
        }
    }
}
