package com.rts.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/12/13
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class World {

    private Chunk[][] chunks = new Chunk[chunkAmount][chunkAmount];

    public static int getChunkSize() {
        return chunkSize;
    }

    private static final int chunkSize = 16;
    private static final int chunkAmount = 16;

    public void update() {

    }

    public void draw(SpriteBatch batch) {
        for (int x = 0; x < chunkAmount; x++) {
            for (int y = 0; y < chunkAmount; y++) {
                chunks[x][y].draw(batch);
            }
        }
    }

    public void initTestMap() {
        for (int x = 0; x < chunkAmount; x++) {
            for (int y = 0; y < chunkAmount; y++) {
                chunks[x][y] = new Chunk();
                chunks[x][y].create(x * chunkSize, y * chunkSize);
            }
        }
    }

    public boolean getCollisionAt(int x, int y) {
        return chunks[x / getChunkSize()][y / getChunkSize()].filled[x % getChunkSize()][y % getChunkSize()];
    }
}
