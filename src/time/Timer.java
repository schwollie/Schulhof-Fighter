package time;

import java.util.ArrayList;

public class Timer {

    private String name;

    private double startTime;
    private double time;
    private double elapsedTime = 0;

    private boolean hasStopped = false;

    private ArrayList<TimeEventListener> listeners = new ArrayList<>();

    public Timer(String name, double time) {
        this.name = name;
        this.time = time;
        this.startTime = time;
    }

    public Timer(String name, double time, TimeEventListener l) {
        this.name = name;
        this.time = time;
        this.listeners.add(l);
        this.startTime = time;
    }

    public Timer(String name, double time, ArrayList<TimeEventListener> l) {
        this.name = name;
        this.time = time;
        this.listeners = l;
        this.startTime = time;
    }

    public void tick(double dt) {
        elapsedTime += dt;

        if (hasStopped) { return; }

        time -= dt;

        if (time <= 0) {
            hasStopped = true;
            sendEvent();
        }
    }

    public double getElapsedTime() {
        return elapsedTime;
    }

    public double getElapsedTimePercentage() { return elapsedTime/startTime; }

    public boolean isFinished() {
        return time <= 0;
    }

    private void sendEvent() {
        for (TimeEventListener t : listeners) {
            t.onTimerStops(this.name);
        }
    }

    public void addListener(TimeEventListener l) {
        this.listeners.add(l);
    }

    public void removeListener(TimeEventListener l ) {
        this.listeners.remove(l);
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
