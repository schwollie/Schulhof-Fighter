package time;

import game.ComponentType;
import game.GameComponent;
import game.GameObject;

import java.util.ArrayList;

public class Timer extends GameComponent {

    private final String name;

    private final double startTime;
    private double time;
    private double elapsedTime = 0;

    private boolean hasStopped = false;
    private boolean pause = false;

    private ArrayList<TimeEventListener> listeners = new ArrayList<>();

    public Timer(GameObject ref, String name, double time) {
        super(ref, ComponentType.Timer);
        this.name = name;
        this.time = time;
        this.startTime = time;
    }

    public Timer(GameObject ref, String name, double time, TimeEventListener l) {
        super(ref, ComponentType.Timer);
        this.name = name;
        this.time = time;
        this.listeners.add(l);
        this.startTime = time;
    }

    public Timer(GameObject ref, String name, double time, ArrayList<TimeEventListener> l) {
        super(ref, ComponentType.Timer);
        this.name = name;
        this.time = time;
        this.listeners = l;
        this.startTime = time;
    }

    @Override
    public void tick() {
        double dt = reference.getTime().getDeltaTime();
        elapsedTime += dt;

        if (hasStopped || pause) {
            return;
        }

        time -= dt;

        if (time <= 0) {
            hasStopped = true;
            sendEvent();
        }
    }

    public void resetTimer() {
        elapsedTime = 0;
        time = startTime;
        hasStopped = false;
        pause = false;
    }

    public void resetTime() {
        time = startTime;
        hasStopped = false;
    }

    private void sendEvent() {
        for (TimeEventListener t : listeners) {
            t.onTimerStops(this.name);
        }
    }

    public void addListener(TimeEventListener l) {
        this.listeners.add(l);
    }

    public void removeListener(TimeEventListener l) {
        this.listeners.remove(l);
    }

    public double getElapsedTime() {
        return elapsedTime;
    }

    public double getElapsedTimePercentage() {
        return Math.max(Math.min(1, elapsedTime / startTime), 0);
    }

    public boolean isFinished() {
        return time <= 0;
    }

    public void pause() {
        this.pause = true;
    }

    public void resume() {
        this.pause = false;
    }

    @Override
    public String toString() {
        return "Timer{" +
                "time=" + time +
                ", elapsedTime=" + elapsedTime +
                ", hasStopped=" + hasStopped +
                '}';
    }
}
