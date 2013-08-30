package com.rts.game.gameplay;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rts.game.pathfinding.JumpPoint;
import com.rts.game.pathfinding.Node;

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

    public static JumpPoint jps = new JumpPoint();

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
                chunks[x][y].draw();
            }
        }

        ShapeRenderer box = new ShapeRenderer();
        box.begin(ShapeRenderer.ShapeType.FilledRectangle);
        box.setProjectionMatrix(Camera.getOrthographicCamera().combined);
        box.setColor(1, 0, 1, 0.8f);
        for (int x = 0; x < jps.grid.nodes.length; x++) {
            for (int y = 0; y < jps.grid.nodes[0].length; y++) {
                if (jps.grid.isBlockedAt(x, y)) {
                    box.filledRect(x, y, 1, 1);
                }
            }
        }
        box.end();


    }

    public void initTestMap() {

        for (int x = 0; x < chunkAmount; x++) {
            for (int y = 0; y < chunkAmount; y++) {
                chunks[x][y] = new Chunk();
                chunks[x][y].create(x * chunkSize, y * chunkSize);
            }
        }

        jps.grid.buildNodes(128, 128);

        for (int x = 0; x < jps.grid.nodes.length; x++) {
            for (int y = 0; y < jps.grid.nodes[0].length; y++) {
                if (rand.nextInt(10) == 0)
                    jps.grid.nodes[x][y].setBlocked(true);
                else {
                    jps.grid.nodes[x][y].setBlocked(false);
                }
            }
        }

    }

    public static ArrayList<Node> getPath(int x, int y, int x2, int y2) {
        return jps.search(x, y, x2, y2);
    }

}
