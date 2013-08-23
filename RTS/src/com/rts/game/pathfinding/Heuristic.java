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

    public static float get(Node a, Node b) {
        switch (setting) {
            case MANHATTAN:
                return manhattan(a, b);
            default:
                return manhattan(a, b);
        }
    }

    private static float manhattan(Node a, Node b) {
        return abs(a.getX() - b.getX()) + abs(a.getY() - b.getY());
    }

    private static float abs(float a) {
        if (a < 0) {
            return -a;
        }
        return a;
    }


}
