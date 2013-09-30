package com.rts.game.gameplay;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.rts.game.entities.EntityManager;
import com.rts.game.entities.Tree;
import com.rts.game.pathfinding.JumpPoint;
import com.rts.game.pathfinding.Node;
import com.rts.game.pathfinding.PathSmoother;
import com.rts.game.screens.ShapeRenderer;

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

    private static final int chunkSize = 16;
    private static final int chunkAmount = 16 * 16;
    public static JumpPoint jps = new JumpPoint(128, 128);
    static boolean useSmoothedPath = false;
    Random rand = new Random();
    private Chunk[][] chunks = new Chunk[chunkAmount][chunkAmount];

    public static int getChunkSize() {
        return chunkSize;
    }

    public static ArrayList<Node> getPath(int x, int y, int x2, int y2) {
        if (useSmoothedPath)
            return PathSmoother.smoothBasic(jps.search(x, y, x2, y2));
        return jps.search(x, y, x2, y2);
    }

    public static Node nodeAt(int x, int y) {
        return jps.grid.getNode(x, y);
    }

    public static Node nodeAt(float x, float y) {
        return jps.grid.getNode((int) x, (int) y);
    }

    public void update() {

    }

    public void draw() {
        for (int x = 0; x < chunkAmount; x++) {
            for (int y = 0; y < chunkAmount; y++) {
                chunks[x][y].draw(Camera.batch);
            }
        }

        //Camera.batch.end();

        ShapeRenderer box = new ShapeRenderer();
/*        box.setProjectionMatrix(Camera.getOrthographicCamera().combined);
        box.begin(ShapeRenderer.ShapeType.FilledRectangle);*/
        //box.setColor(1, 0, 1, 0.8f);
        ShapeRenderer.setColor(Color.CYAN);
        for (int x = 0; x < jps.grid.grid.length; x++) {
            for (int y = 0; y < jps.grid.grid[0].length; y++) {
                if (!jps.grid.getNode(x, y).isPass()) {
                    if (!Gdx.input.isKeyPressed(Input.Keys.R)) {
                        ShapeRenderer.drawRectangle(x, y, 1, 1, true);
                    }
                }
            }
        }
    }

    public void initTestMap() {

        for (int x = 0; x < chunkAmount; x++) {
            for (int y = 0; y < chunkAmount; y++) {
                chunks[x][y] = new Chunk();
                chunks[x][y].create(x, y);
            }
        }

        for (int x = 1; x < jps.grid.grid.length - 1; x++) {
            for (int y = 1; y < jps.grid.grid[0].length - 1; y++) {
                if (rand.nextInt(10) == 0) {
                    // if (jps.grid.grid[x][y].isPass() && jps.grid.grid[x - 1][y].isPass() && jps.grid.grid[x][y - 1].isPass() && jps.grid.grid[x - 1][y - 1].isPass()) {
                    if (jps.grid.grid[x][y].standing == null && jps.grid.grid[x - 1][y].standing == null && jps.grid.grid[x][y - 1].standing == null && jps.grid.grid[x - 1][y - 1].standing == null) {
                        EntityManager.addEntity(new Tree(x, y));
                    } else {
                        jps.grid.grid[x][y].setPassable(true);
                    }
                } else {
                    jps.grid.grid[x][y].setPassable(true);
                }
            }
        }
    }

}
