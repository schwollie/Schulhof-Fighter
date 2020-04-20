package particle;

import display.Camera;
import game.*;
import graphics.Drawable;
import logic.Transform;
import logic.Vector2;
import time.TimeEventListener;
import time.Timer;

import java.awt.*;

public class Particle extends GameComponent implements Drawable, TimeEventListener {

    private final ParticleSystem parentSystem;
    private Vector2 force = new Vector2(0,0);
    private Vector2 velocity = new Vector2(0,0);
    private double mass;
    private double drag;

    private Timer lifeTimer;

    private double startOpacity;
    private double endOpacity;
    private double opacity = 1;

    private double startSize;
    private double endSize;
    private double size;

    private double gravityFactor;

    public Particle(ParticleSystem parentSystem, Transform transform, Vector2 startForce) {
        super(parentSystem.getReference(), ComponentType.Particle);
        this.parentSystem = parentSystem;
        this.transform = transform;
        this.force = startForce;

        this.setup();
    }

    private void setup() {
        startOpacity = parentSystem.getStartOpacity().getRandom();
        endOpacity = parentSystem.getFinalOpacity().getRandom();

        startSize = parentSystem.getStartSize().getRandom();
        endSize = parentSystem.getFinalSize().getRandom();

        lifeTimer = new Timer(reference, "lifespanTimer", parentSystem.getLiveTime().getRandom(), this);
        mass = parentSystem.getParticleMass().getRandom();
        gravityFactor = parentSystem.getGravityFactor().getRandom();
        drag = parentSystem.getDragFactor().getRandom();
    }

    @Override
    public void tick() {
        double dt = reference.getTime().getDeltaTime();
        lifeTimer.tick();
        setStats();
        calcPhysics(dt);
    }

    private void setStats() {
        this.opacity = startOpacity - ((startOpacity - endOpacity) * lifeTimer.getElapsedTimePercentage());
        this.size = startSize - ((startSize - endSize) * lifeTimer.getElapsedTimePercentage());
        this.transform.setScale(new Vector2(size, size));
    }

    // region physics:
    private void calcPhysics(double dt) {
        addDrag();
        addGravity();
        applyForce(dt);
        force = new Vector2(0,0);

        updateTransform(dt);
    }

    private void addGravity() {
        // F = m*a = m*g
        addForce(new Vector2(0, Consts.gravity * gravityFactor).scalarMult(mass));
    }

    private void addDrag() {
        //Todo: wrong formula
        // F = v * -drag
        double v = this.velocity.getLength();
        Vector2 direction = this.velocity.getNormalized();

        double force = -this.drag * v * mass;

        Vector2 vForce = direction.scalarMult(force);
        this.addForce(vForce);
    }

    private void applyForce(double dt) {
        this.velocity = velocity.add(this.force.scalarMult((1 / this.mass)).scalarMult(dt));
    }

    private void updateTransform(double dt) {
        Vector2 newV = this.velocity.scalarMult(dt);
        this.transform = this.transform.addPosition(newV);
    }

    public void addForce(Vector2 force) {
        this.force = this.force.add(force);
    }
    // endregion

    @Override
    public void Render(Graphics2D g, Camera cam) {
        parentSystem.getParticleSprite().setRenderLayer(10);
        parentSystem.getParticleSprite().setAlpha(opacity);
        parentSystem.getParticleSprite().setRelativeTransform(this.transform.add(parentSystem.getRelativeTransform()));
        parentSystem.getParticleSprite().Render(g, cam);
    }

    @Override
    public int getRenderLayer() {
        return parentSystem.getRenderLayer();
    }

    @Override
    public void setRenderLayer(int layer) {
        //this method should not be used in class particle
        parentSystem.setRenderLayer(layer);
    }

    @Override
    public void onTimerStops(String timerName) {
        if (timerName.equals("lifespanTimer")) {
            parentSystem.removeParticle(this);
            super.destroy();
        }
    }

    //region getters + setters:

    public double getStartOpacity() {
        return startOpacity;
    }

    public void setStartOpacity(double startOpacity) {
        this.startOpacity = startOpacity;
    }

    public double getStartSize() {
        return startSize;
    }

    public void setStartSize(double startSize) {
        this.startSize = startSize;
    }
    //endregion
}
