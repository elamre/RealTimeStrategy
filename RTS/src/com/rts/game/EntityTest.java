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
        x = 0;
        sprites = new Sprite[1];
        Texture texture = new Texture(Gdx.files.internal("data/libgdx.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion region = new TextureRegion(texture, 0, 0, 256, 256);

        sprites[0] = new Sprite(region);

        System.out.println("Created");
    }

    public void update() {
         x += 0.001f;
        sprites[0].setX(x);

        sprites[0].setSize(0.9f, 0.9f * sprites[0].getHeight() / sprites[0].getWidth());
        sprites[0].setOrigin(sprites[0].getWidth() / 2, sprites[0].getHeight() / 2);

        System.out.println(x);
    }

    public void draw(SpriteBatch batch) {
        for(Sprite s : sprites) {
            s.draw(batch);
        }
        System.out.println("Drawing...");
    }

}
