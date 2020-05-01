package time;

public class TimeManager {

    private final static double convertFactor = 1000000000;

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
        currentTime = System.nanoTime();
        deltaTime = currentTime - lastTime;
        lastTime = currentTime;

        currentFPS = convertFactor / deltaTime;
    }

    public void waitForTargetFPS() {
        if (currentFPS < targetFPS) {
            return;
        }

        // FPS: 90 target: 60
        double waitTime = Math.max(1, convertFactor / targetFPS - deltaTime);

        double currentTime = System.nanoTime();
        double targetTime = currentTime + waitTime;

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }

        while (currentTime < targetTime) {
            currentTime = System.nanoTime();
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
        return Math.min(deltaTime / convertFactor * timeScale, 0.2);
    }

    public double getAbsDeltaTime() {
        // Min of 5 fps -> to prevent weired physics due to lags
        return Math.min(deltaTime / convertFactor, 0.2);
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
