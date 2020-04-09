package logic;


// The Shaker gives an offset for the current Time -> can be used for Camera Shaking

import game.ComponentType;
import game.GameComponent;
import game.GameObject;
import time.TimeEventListener;
import time.Timer;

public class Shaker extends GameComponent implements TimeEventListener {

    private Vector2 offset;

    private Vector2 maxDeviation;
    private Vector2 speed;
    private Timer timer;

    private Vector2 lastOffset = new Vector2(0,0);

    public Shaker(GameObject ref, Vector2 maxDeviation, Vector2 speed, double maxTime) {
        super(ref, ComponentType.Logic);
        this.maxDeviation = maxDeviation;
        this.speed = speed.scalarMult(10);
        timer = new Timer("ShakerTimer", maxTime, this);
    }

    @Override
    public void tick() {
        double dt = reference.getTime().getDeltaTime();
        timer.tick(dt);

        //newOffset
        Vector2 offset = getOffset();

        //current Pos
        Vector2 pos = reference.getTransform().getPosition();

        // first undo last offset
        reference.getTransform().setPosition(pos.subtract(lastOffset));

        // now apply new offset
        pos = reference.getTransform().getPosition();
        Vector2 newPos = pos.add(offset);

        reference.getTransform().setPosition(newPos);

        lastOffset = offset;
    }

    public Vector2 getOffset() {
        double p = timer.getElapsedTimePercentage();
        double strength = 1-p; // linear

        double newX = Math.sin(timer.getElapsedTime()*speed.getX()) * maxDeviation.getX() * strength;
        double newY = Math.cos(timer.getElapsedTime()*speed.getY()) * maxDeviation.getY() * strength;
        return new Vector2(newX, newY);
    }

    @Override
    public void onTimerStops(String timerName) {
        if (timerName.equals("ShakerTimer")) {
            this.reference.removeComponent(this);
        }
    }
}
