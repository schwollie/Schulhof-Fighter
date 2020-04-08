package game;

public class Utilities {
    public static double map(double number, double start, double end, double min, double max) {
        double factor = (number - start) / (end - start);
        return Math.round(((max - min) * factor) + min);
    }
}
