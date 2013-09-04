package com.rts.game.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rts.game.Assets;
import com.rts.game.gameplay.World;

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
    TextureRegion texture;
/*
    Sprite sprite;
*/

    public void create(int x, int y) {

        this.x = x;
        this.y = y;

/*
        sprite = new Sprite();
*/
        texture = Assets.getAssets().getTextureRegion("Map/grass");
/*
        texture = "Images/Environment/Grass.png";
        Texture tex = new Texture(Gdx.files.internal(texture));
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        TextureRegion region = new TextureRegion(tex, 0, 0, 256, 256);
*/

/*        sprite = new Sprite(Assets.getAssets().getSprite("Map/grass"));
        sprite.setOrigin(0, 0);
        sprite.setPosition(x, y);*/
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, 1f, 1f);
        //sprite.draw(batch);
    }

}
