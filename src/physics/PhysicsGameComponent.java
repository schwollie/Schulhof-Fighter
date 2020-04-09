package physics;

import game.*;
import logic.Vector2;

public class PhysicsGameComponent extends GameComponent {

    private boolean isStatic = false;
    private double mass = 1;
    private double massInverse = 1/mass;  // for calculations
    private Vector2 velocity = new Vector2(0, 0);

    private Collider collider = null;

    private Vector2 force = Vector2.zero;

    public PhysicsGameComponent(GameObject gameObject) {
        super(gameObject, ComponentType.PhysicsComponent);
    }

    //region Physics Calculations:

    public void tick() {
        double deltaTime = reference.getTime().getDeltaTime();
        if (isStatic) { return; }
        this.calcPhysics(deltaTime, reference.getScene());
        this.applyForce(deltaTime);
        this.force = new Vector2(0, 0);  // reset all forces

        updatePos(deltaTime);
    }

    public void updatePos(double dt) {
        if (isStatic) {return; }
        updateTransform(dt);
    }

    private void calcPhysics(double dt, Scene scene) {
        this.calcCollisionForce(scene);
        this.addGravity();
        this.addDrag(dt);
        //this.getForce().print();
    }

    private void addGravity() {
        // F = m*a = m*g
        addForce( new Vector2(0, Consts.gravity).scalarMult(mass));
    }

    private void calcCollisionForce(Scene scene) {

        for (PhysicsGameComponent p: scene.getPhysicsComponents()) {
            if (p == this) { continue; }
            if (p.collider == null) { continue; }
            if (this.collider == null) { return; }

            this.collider.manageCollision(this, p);
        }
    }

    private void addDrag(double dt) {
        //Todo: wrong formula
        // F = v * -drag
        double v = this.velocity.getLength();
        Vector2 direction = this.velocity.getNormalized();

        direction.setY(0); // its only linear drag

        double force = -Consts.linearDrag * v * mass;

        Vector2 vForce = direction.scalarMult(force);
        this.addForce(vForce);
    }

    private void applyForce(double dt) {
        this.velocity = velocity.add( this.force.scalarMult((1/this.mass)).scalarMult(dt) );
    }

    private void updateTransform(double dt) {
        Vector2 newV = this.velocity.scalarMult(dt);
        reference.setTransform(reference.getTransform().addPosition( newV));
    }

    //endregion

    //region Getters + Setters:

    public void addForce(Vector2 force) {
        this.force = this.force.add(force);
    }

    public Collider getCollider() { return this.collider; }
    public Vector2 getVelocity() { return this.velocity; }
    public Vector2 getForce() { return this.force; }

    public double getMass() {
        return mass;
    }

    public double getMassInverse() {
        return massInverse;
    }

    public void setCollider(Collider c) {
        this.collider = c;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
        mass = Double.POSITIVE_INFINITY;
        massInverse = 0;
    }

    public void addPosition(Vector2 pos) {
        this.reference.getTransform().setPosition( reference.getTransform().addPosition(pos).getPosition() );
    }

    public void addVelocity(Vector2 v) {
        this.velocity = this.velocity.add(v);
    }

    public void setVelocityX (double x) { this.velocity.setX(x); }
    public void setVelocityY (double y) { this.velocity.setY(y); }

    public void setMass(double mass) {
        this.mass = mass;
        this.massInverse = 1/mass;
    }

    //endregion
}
