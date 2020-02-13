package physics;

import game.Consts;
import game.GameObject;
import game.GameWorld;
import logic.Vector2;

public class PhysicsObject {

    private boolean isStatic = false;
    private double mass = 1;
    private double massInverse = 1/mass;  // for calculations
    private Vector2 velocity = new Vector2(0, 0);

    private GameObject gameObjectRef;
    private Collider collider = null;

    private Vector2 force = Vector2.zero;


    public PhysicsObject(GameObject gameObject) {
        this.gameObjectRef = gameObject;
        //this.setCollider(new CircleCollider(gameObject.getTransform(),new Vector2(100,100), this, 100));
        this.setCollider(new RectCollider(gameObject.getTransform(), new Vector2(130,50),this, new Vector2(120, 350)));
    }

    public PhysicsObject(double mass, Vector2 velocity, Collider collider, GameObject gameObject) {
        this.mass = mass;
        this.velocity = velocity;
        this.collider = collider;
        this.gameObjectRef = gameObject;
    }

    public void calcForces(double dt, GameWorld world) {
        if (isStatic) { return; }
        this.calcPhysics(dt, world);
        this.applyForce(dt);
    }

    public void updatePos(double dt) {
        if (isStatic) {return; }
        updateTransform(dt);
    }

    private void calcPhysics(double dt, GameWorld world) {
        this.force = new Vector2(0, 0);  // reset all forces
        this.calcCollisionForce(dt, world);
        this.addGravity();
        this.addDrag(dt);
        //this.getForce().print();
    }

    private void addGravity() {
        // F = m*a = m*g
        addForce( new Vector2(0, Consts.gravity).scalarMult(mass));
    }

    private void calcCollisionForce(double dt, GameWorld world) {

        for (PhysicsObject p: world.physicsObjects) {
            if (p == this) { continue; }
            if (p.collider == null) { continue; }
            if (this.collider == null) { continue; }

            //this.addForce(this.collider.calcForce(p, dt));
            this.collider.calcForce(p);
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
        //TODO: this can't be the true solution for the ground glitch effect
        //newV.setX((int)newV.getX());
        //newV.setY((int)newV.getY());
        gameObjectRef.getTransform().addPosition( newV);
    }

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

    public void addVelocity(Vector2 v) {
        this.velocity = this.velocity.add(v);
    }

    public void setVelocityX (double x) { this.velocity.setX(x); }
    public void setVelocityY (double y) { this.velocity.setY(y); }



    public void setMass(double mass) {
        this.mass = mass;
        this.massInverse = 1/mass;
    }
}
