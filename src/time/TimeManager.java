package time;

public class TimeManager {

    private final int targetFPS;

    private double currentTime = 0;
    private double lastTime = 0;
    private double deltaTime = 0;
    private double currentFPS = 0;

    private double timeScale = 1;

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

        long currentTime = System.currentTimeMillis();
        long targetTime = currentTime + (long) waitTime;

        while (currentTime < targetTime) {
            currentTime = System.currentTimeMillis();
        }

        try {
            Thread.sleep(1);
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
        // Min of 5 fps -> to prevent weired physics due to lags
        return Math.min(deltaTime / 1000 * timeScale, 0.2);
    }

    public double getAbsDeltaTime() {
        // Min of 5 fps -> to prevent weired physics due to lags
        return Math.min(deltaTime / 1000, 0.2);
    }

    @Override
    public String toString() {
        return "FpsTracker{" +
                "deltaTime=" + deltaTime +
                ", currentFPS=" + currentFPS +
                '}';
    }

    public void setTimeScale(double timeScale) {
        this.timeScale = timeScale;
    }
}
