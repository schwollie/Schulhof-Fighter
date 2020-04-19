package logic;

import java.util.Random;

public class XRange {

    private final Random rnd = new Random();
    private final double start;
    private final double end;

    public XRange(double start, double end) {
        assert start < end : "start value must be smaller than end value";
        this.start = start;
        this.end = end;
    }

    public double getStart() {
        return start;
    }

    public double getEnd() {
        return end;
    }

    public double getRandom() {
        double delta = end - start;
        return rnd.nextDouble() * delta + start;
    }

    public double getValAtPercentage(double percentage) {
        double delta = end - start;
        return delta * percentage + start;
    }
}

