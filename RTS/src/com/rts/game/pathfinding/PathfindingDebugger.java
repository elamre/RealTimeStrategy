package com.rts.game.pathfinding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rts.game.gameplay.Camera;
import com.rts.game.gameplay.World;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/30/13
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class PathfindingDebugger {

    static int startx = 0;
    static int starty = 0;
    static int endx = 100;
    static int endy = 100;
    static boolean last = false;

    static ArrayList<Node> path;

    public static void draw() {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(Camera.getOrthographicCamera().combined);
        //shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.setColor(1f, 0, 0, 0.5f);

        if (path != null && path.size() > 1) {
            for (int i = 0; i < path.size() - 1; i++) {
                shapeRenderer.line(path.get(i).getX(), path.get(i).getY(), path.get(i + 1).getX(), path.get(i + 1).getY());
            }
        }

        shapeRenderer.end();
    }

    public static void set(int x, int y) {

        System.out.println("Setting new point: " + x + ", " + y);

        if (last) {
            startx = x;
            starty = y;
        } else {
            endx = x;
            endy = y;
        }
        last = !last;

        path = World.getPath(startx, starty, endx, endy);

        if (path != null && path.size() > 0) {
            System.out.println("Path of size: " + path.size());
            for (Node n : path) {
                System.out.println("Node: " + n.getX() + ", " + n.getY());
            }
        } else {
            System.out.println("Invalid path");
        }


    }

}
