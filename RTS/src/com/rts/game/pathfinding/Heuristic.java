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

    public static int setting = 0;

    public static void setCurrentHeuristic(int newSetting) {
        setting = newSetting;
    }

    public static float get(int a, int b) {
        return a + b;
    }

    public static float euclidian(int dx, int dy) {
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public static float get(Node a, Node b) {
        switch (setting) {
            case MANHATTAN:
                return manhattan(a, b);
            default:
                return manhattan(a, b);
        }
    }

    public static float get(int x, int y, int x2, int y2) {
        switch (setting) {
            case MANHATTAN:
                return manhattan(x, y, x2, y2);
            default:
                return manhattan(x, y, x2, y2);
        }
    }

    private static float manhattan(Node a, Node b) {
        return manhattan(a.getX(), a.getY(), b.getX(), b.getY());
    }

    private static float manhattan(int x, int y, int x2, int y2) {
        return abs(x - x) + abs(y - y);
    }

    private static float abs(float a) {
        if (a < 0) {
            return -a;
        }
        return a;
    }


}
