package com.rts.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A virtual cursor to use
 */
public class Cursor {

    public static float x = Gdx.graphics.getWidth() / 2;
    public static float y = Gdx.graphics.getHeight() / 2;
    static Sprite sprite;

    public static void create() {

        sprite = new Sprite();
        String texture = "Images/Hud/mouse.png";
        Texture tex = new Texture(Gdx.files.internal(texture));
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        TextureRegion region = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());

        sprite = new Sprite(region);

        sprite.setSize(World.getChunkSize(), World.getChunkSize());
        sprite.setOrigin(0, 0);
    }

    public static void draw() {
        sprite.setPosition(x, y);
        sprite.draw(Camera.batch);
    }

    public static void update(float delta) {

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
            Gdx.input.setCursorCatched(true);
        else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.input.setCursorCatched(false);

        if (Gdx.input.isCursorCatched()) {
            Gdx.input.setCursorPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

            x += Gdx.input.getDeltaX();
            y += Gdx.input.getDeltaY();
        }

        if (x < 0) {
            x = 0;
        } else if (x > Gdx.graphics.getWidth()) {
            x = Gdx.graphics.getWidth();
        }

        if (y < 0) {
            y = 0;
        } else if (y > Gdx.graphics.getHeight()) {
            y = Gdx.graphics.getHeight();
        }

        //System.out.println(x + ", " + y);

    }

}
