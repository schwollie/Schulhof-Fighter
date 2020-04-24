package particle;

import display.Camera;
import gameobjects.ComponentType;
import gameobjects.GameComponent;
import gameobjects.GameObject;
import graphics.Drawable;
import graphics.ImageSprite;
import graphics.RenderManager;
import graphics.Sprite;
import logic.Dimension2D;
import logic.XRange;
import time.TimeEventListener;
import time.Timer;

import java.awt.*;
import java.util.LinkedList;

public class ParticleSystem extends GameComponent implements Drawable, TimeEventListener {

    private int RenderLayer = reference.getLayer();
    private final Timer timer;
    private final LinkedList<Particle> particles = new LinkedList<>();
    private final LinkedList<Particle> particles2remove = new LinkedList<>();
    private ParticleType particleType;

    //particle specs
    private Sprite particleSprite;

    private XRange liveTime;
    private XRange particleCount;
    private XRange startForce = new XRange(10, 20);
    private XRange particleMass = new XRange(0.001, 0.02);
    private XRange gravityFactor = new XRange(0.1, 0.5);
    private XRange dragFactor = new XRange(0, 0.002);

    private XRange startSize = new XRange(.1, .1);
    private XRange finalSize = new XRange(.01, .02);

    private XRange startOpacity = new XRange(1, 1);
    private XRange finalOpacity = new XRange(0, 0);

    public ParticleSystem(GameObject ref, XRange liveTime, double interval, XRange particleCount) {
        super(ref, ComponentType.ParticleSystem);

        this.liveTime = liveTime;
        this.timer = new Timer(ref, "spawnParticle", interval, this);
        this.particleCount = particleCount;

        loadImage(ParticleType.PUNCH);
    }

    public void start() {
        spawnParticles();
    }

    private void loadImage(ParticleType type) {
        particleSprite =  new ImageSprite(this.reference, new Dimension2D(64, 64), "images/Particles/particle.png");
        particleSprite.setRenderLayer(2);
    }

    @Override
    public void tick() {
        checkParticles();
        timer.tick();

        for (Particle p: particles) {
            p.tick();
        }
    }

    private void checkParticles() {
        particles.removeAll(particles2remove);
        particles2remove.clear();
    }

    @Override
    public void onTimerStops(String timerName) {
        if (timerName.equals(timerName)) {
            destroy();
        }
    }

    private void spawnParticles() {
        CircleEmitter circleEmitter = new CircleEmitter(this.transform.getCopy(), particleCount, startForce);
        this.particles.addAll(circleEmitter.emmitParticles(this));
    }

    public Sprite getParticleSprite() {
        return this.particleSprite;
    }

    @Override
    public void UpdateDrawables(RenderManager renderManager) {
        renderManager.addDrawable(this);
    }

    @Override
    public void Render(Graphics2D g, Camera cam) {
        for (Particle p : particles) {
            p.Render(g, cam);
        }
    }

    @Override
    public int getRenderLayer() {
        return this.RenderLayer;
    }

    @Override
    public void setRenderLayer(int layer) {
    }

    public void removeParticle(Particle p) {
        particles2remove.add(p);
    }

    // region getters + setters:

    public Timer getTimer() {
        return timer;
    }

    public LinkedList<Particle> getParticles() {
        return particles;
    }

    public ParticleType getParticleType() {
        return particleType;
    }

    public void setParticleType(ParticleType particleType) {
        this.particleType = particleType;
    }

    public void setParticleSprite(ImageSprite particleSprite) {
        this.particleSprite = particleSprite;
    }

    public XRange getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(XRange liveTime) {
        this.liveTime = liveTime;
    }

    public XRange getStartSize() {
        return startSize;
    }

    public void setStartSize(XRange startSize) {
        this.startSize = startSize;
    }

    public XRange getFinalSize() {
        return finalSize;
    }

    public void setFinalSize(XRange finalSize) {
        this.finalSize = finalSize;
    }

    public XRange getStartOpacity() {
        return startOpacity;
    }

    public void setStartOpacity(XRange startOpacity) {
        this.startOpacity = startOpacity;
    }

    public XRange getFinalOpacity() {
        return finalOpacity;
    }

    public void setFinalOpacity(XRange finalOpacity) {
        this.finalOpacity = finalOpacity;
    }

    public XRange getGravityFactor() {
        return gravityFactor;
    }

    public void setGravityFactor(XRange gravityFactor) {
        this.gravityFactor = gravityFactor;
    }

    public XRange getDragFactor() {
        return dragFactor;
    }

    public void setDragFactor(XRange dragFactor) {
        this.dragFactor = dragFactor;
    }

    public XRange getParticleMass() {
        return particleMass;
    }

    public void setParticleMass(XRange particleMass) {
        this.particleMass = particleMass;
    }

    public void setStartForce(XRange startForce) {
        this.startForce = startForce;
    }

    public XRange getStartForce() {
        return startForce;
    }

    // endregion
}
