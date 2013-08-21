package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.Assets;
import com.rts.game.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/11/13
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityTest extends MoveableEntity {

    public EntityTest(int x, int y) {
        super(Assets.getAssets().getSprite("Animations/Units/houtman3"), x, y);
    }

}
