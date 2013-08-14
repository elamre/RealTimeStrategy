package com.rts.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/11/13
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityTest extends Entity {

    public void create() {
        sprites = new Sprite[1];
        Texture texture = new Texture(Gdx.files.internal("data/libgdx.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion region = new TextureRegion(texture, 0, 0, 256, 256);

        sprites[0] = new Sprite(region);

        setUpBoundaries();
    }

    public void update(float delta) {
        sprites[0].setSize(0.01f, 0.01f);
        sprites[0].setOrigin(0, 0);

    }

    public void draw(SpriteBatch batch) {
        for (Sprite s : sprites) {
            s.draw(batch);
        }
    }

}
