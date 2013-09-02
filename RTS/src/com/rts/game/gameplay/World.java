package com.rts.game.gameplay;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rts.game.pathfinding.JumpPoint;
import com.rts.game.pathfinding.Node;
import com.rts.game.pathfinding.PathfindingDebugger;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/12/13
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class World {

    Random rand = new Random();

    private Chunk[][] chunks = new Chunk[chunkAmount][chunkAmount];

    public static JumpPoint jps = new JumpPoint(128, 128);

    public static int getChunkSize() {
        return chunkSize;
    }

    private static final int chunkSize = 16;
    private static final int chunkAmount = 16;

    public void update() {

    }

    public void draw() {
        for (int x = 0; x < chunkAmount; x++) {
            for (int y = 0; y < chunkAmount; y++) {
                chunks[x][y].draw(Camera.batch);
            }
        }

        Camera.batch.end();

        ShapeRenderer box = new ShapeRenderer();
        box.setProjectionMatrix(Camera.getOrthographicCamera().combined);
        box.begin(ShapeRenderer.ShapeType.FilledRectangle);
        box.setColor(1, 0, 1, 0.8f);
        for (int x = 0; x < jps.grid.grid.length; x++) {
            for (int y = 0; y < jps.grid.grid[0].length; y++) {
                if (!jps.grid.getNode(x, y).isPass()) {
                    box.filledRect(x, y, 1, 1);
                }
            }
        }
        box.end();


        box = new ShapeRenderer();
        box.setProjectionMatrix(Camera.getOrthographicCamera().combined);
        box.begin(ShapeRenderer.ShapeType.Point);
        box.setColor(1, 0, 0, 0.8f);
        for (int x = 0; x < jps.grid.grid.length; x++) {
            for (int y = 0; y < jps.grid.grid[0].length; y++) {
                box.point(x + 0.5f, y + 0.5f, 0);
            }
        }
        box.end();


        PathfindingDebugger.draw();

        Camera.batch.begin();


    }

    public void initTestMap() {

        for (int x = 0; x < chunkAmount; x++) {
            for (int y = 0; y < chunkAmount; y++) {
                chunks[x][y] = new Chunk();
                chunks[x][y].create(x * chunkSize, y * chunkSize);
            }
        }

        for (int x = 0; x < jps.grid.grid.length; x++) {
            for (int y = 0; y < jps.grid.grid[0].length; y++) {
                if (rand.nextInt(10) == 0)
                    jps.grid.grid[x][y].setPassable(false);
                else {
                    jps.grid.grid[x][y].setPassable(true);
                }
            }
        }
    }

    public static ArrayList<Node> getPath(int x, int y, int x2, int y2) {
        return jps.search(x, y, x2, y2);
    }

    public static Node nodeAt(int x, int y) {
        return jps.grid.getNode(x, y);
    }

}
