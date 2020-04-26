package logic;

import java.util.Random;

public abstract class RandomClass {

    public static final Random rnd = new Random();

    public static boolean chance(double chance) {
        double r = rnd.nextDouble();
        if (chance > r) {
            return true;
        }
        return false;
    }

}
