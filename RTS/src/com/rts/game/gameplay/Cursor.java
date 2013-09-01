package com.rts.game.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rts.game.Assets;
import com.rts.game.abilities.Ability;

/**
 * A virtual cursor to use
 */
public class Cursor {

    public static float x = Gdx.graphics.getWidth() / 2;
    public static float y = Gdx.graphics.getHeight() / 2;
    static Sprite sprite;
    static Sprite spriteAbilityUse;

    public static boolean abilityRequested = false;
    public static Ability abilityRequesting = null;

    public static void create() {
        sprite = new Sprite(Assets.getAssets().getTextureRegion("Special/mouse"));
        sprite.setSize(World.getChunkSize(), World.getChunkSize());
        sprite.setOrigin(0, 0);
        sprite.flip(false, true);


        spriteAbilityUse = new Sprite();
        spriteAbilityUse = new Sprite(Assets.getAssets().getTextureRegion("UI/button"));
        spriteAbilityUse.setSize(World.getChunkSize(), World.getChunkSize());
        spriteAbilityUse.setOrigin(0, 0);
        spriteAbilityUse.flip(false, true);
    }

    public static void draw() {
        Camera.makeHUDBatch();

        if (!abilityRequested) {
            sprite.setPosition(x, y);
            sprite.draw(Camera.batch);
        } else {
            spriteAbilityUse.setPosition(x, y);
            spriteAbilityUse.draw(Camera.batch);
        }
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
    }

}
