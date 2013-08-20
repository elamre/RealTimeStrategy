package com.rts.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/20/13
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Assets {
    private static Assets assets;
    private TextureAtlas textureAtlas;

    public Assets() {
        textureAtlas = new TextureAtlas(Gdx.files.internal("Images/SpriteSheet.txt"));
    }

    public static Assets getAssets() {
        if (assets == null) {
            assets = new Assets();
        }
        return assets;
    }

    public Sprite getSprite(String name) {
        return textureAtlas.createSprite(name);
    }
}
