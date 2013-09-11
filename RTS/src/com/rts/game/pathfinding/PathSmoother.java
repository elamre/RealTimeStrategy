package com.rts.game.pathfinding;

import com.rts.game.gameplay.World;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 9/5/13
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class PathSmoother {

    public static ArrayList<Node> smoothBasic(ArrayList<Node> path) {

        if (path == null) {
            return new ArrayList<Node>(1);
        }

        if (path.size() < 3) {
            return path;
        }

        boolean simplified = false;

        ArrayList<Node> smoothed = new ArrayList<Node>(path);

        while (!simplified) {

            Node remove = null;

            breakthisplz:
            for (int i = 0; i < smoothed.size() - 2; i++) {
                if (validLinePath(smoothed.get(i), smoothed.get(i + 2))) {
                    remove = smoothed.get(i + 1);
                    System.out.println(remove);
                    break breakthisplz;
                }
            }

            smoothed.remove(remove);

            if (remove == null) {
                simplified = true;
            }

            if (smoothed.size() < 3) {
                simplified = true;
            }

        }

        return smoothed;

    }

    private static boolean validLinePath(Node a, Node b) {

        /*
        float angle = -(float) Math.atan2(b.getX() - a.getX(), b.getY() - a.getY());
        float dx = (float) -(Math.cos(angle - Math.PI / 2));
        float dy = (float) -(Math.sin(angle - Math.PI / 2));

        System.out.println(dx + ", " + dy);

        */

        boolean valid = true;

        Line2D l = new Line2D.Float(a.getCenterX(), a.getCenterY(), b.getCenterX(), b.getCenterY());
        Rectangle2D rect;

        /*
        outerloop:
        for (float x = a.getX(); x < b.getX(); x += dx) {
            for (float y = a.getY(); y < b.getY(); y += dy) {
                for (int i = -1; i < 1; i++) {
                    for (int i2 = -1; i2 < 1; i2++) {
                        Node n = World.nodeAt(x + i, y + i2);
                        rect = new Rectangle2D.Float(n.getX(), n.getY(), 1, 1);
                        if (!n.isPass())
                            if (l.intersects(rect)) {
                                valid = false;
                                break outerloop;
                            }
                    }
                }
            }
        }
        */

        //TODO: Only scan through blocks near line

        int x = (a.getX() < b.getX() ? a.getX() : b.getX());
        int y = (a.getY() < b.getY() ? a.getY() : b.getY());
        int x2 = (a.getX() > b.getX() ? a.getX() : b.getX());
        int y2 = (a.getY() > b.getY() ? a.getY() : b.getY());
        outerloop:
        for (int i = x - 1; i < x2 + 1; i++) {
            for (int c = y - 1; c < y2 + 1; c++) {
                Node n = World.nodeAt(i, c);
                rect = new Rectangle2D.Float(n.getX(), n.getY(), 1, 1);
                if (!n.isPass())
                    if (l.intersects(rect)) {
                        valid = false;
                        break outerloop;
                    }
            }
        }


        return valid;

    }

}
