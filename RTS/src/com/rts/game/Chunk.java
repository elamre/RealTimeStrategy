package com.rts.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/16/13
 * Time: 2:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class Chunk {

    int x;
    int y;
    String texture = "Images/Environment/Grass.png";
    Sprite sprite;

    public boolean[][] filled = new boolean[World.getChunkSize()][World.getChunkSize()];


    public void create(int x, int y) {

        this.x = x;
        this.y = y;

        sprite = new Sprite();
        texture = "Images/Environment/Grass.png";
        Texture tex = new Texture(Gdx.files.internal(texture));
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        TextureRegion region = new TextureRegion(tex, 0, 0, 256, 256);

        sprite = new Sprite(region);

        //sprite.setSize(World.getChunkSize(), World.getChunkSize());
        sprite.setOrigin(0, 0);
        sprite.setPosition(x, y);
    }


    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

}
