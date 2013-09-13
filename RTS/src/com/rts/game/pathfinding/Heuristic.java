package com.rts.game.pathfinding;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/22/13
 * Time: 4:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Heuristic {

    public static final int MANHATTAN = 0;
    public static final int EUCLIDIAN = 1;
    public static final int CHEBYSHEV = 2;


    public static int setting = 0;

    public static void setCurrentHeuristic(int newSetting) {
        setting = newSetting;
    }

    public static float euclidian(int dx, int dy) {
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public static float get(int a, int b) {
        switch (setting) {
            case MANHATTAN:
                return manhattan(a, b);
            case EUCLIDIAN:
                return euclidian(a, b);
            case CHEBYSHEV:
                return chebyshev(a, b);
            default:
                return manhattan(a, b);
        }
    }

    public static float chebyshev(int a, int b) {
        return Math.max(a, b);
    }

    public static float manhattan(int a, int b) {
        return a + b;
    }

    public static float abs(float a) {
        if (a < 0) {
            return -a;
        }
        return a;
    }


}
