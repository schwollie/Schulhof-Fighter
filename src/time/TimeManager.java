package time;

public class TimeManager {

    private final int targetFPS;

    private double currentTime = 0;
    private double lastTime = 0;
    private double deltaTime = 0;
    private double currentFPS = 0;

    public TimeManager(int targetFPS) {
        this.targetFPS = targetFPS;
        stepForward();
    }

    public void stepForward() {
        currentTime = System.currentTimeMillis();
        deltaTime = currentTime - lastTime;
        lastTime = currentTime;

        currentFPS = 1000 / deltaTime;
    }

    public void waitForTargetFPS() {
        if (currentFPS < targetFPS) {
            return;
        }

        // FPS: 90 target: 60
        double waitTime = Math.max(1, 1000 / targetFPS - deltaTime);

        try {
            Thread.sleep((long) waitTime);
        } catch (InterruptedException e) {
        }

        stepForward();
    }

    public double getCurrentFPS() {
        return currentFPS;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public double getLastTime() {
        return lastTime;
    }

    public double getDeltaTime() {
        return deltaTime / 1000;
    }

    @Override
    public String toString() {
        return "FpsTracker{" +
                "deltaTime=" + deltaTime +
                ", currentFPS=" + currentFPS +
                '}';
    }
}
