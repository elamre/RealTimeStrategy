package com.rts.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/11/13
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityTest extends Entity {

    public EntityTest(int x, int y) {
        super(new Sprite[]{Assets.getAssets().getSprite("Animations/Units/houtman3")}, x, y);
    }

    public void create() {
        sprites[0].setSize(1f, 1f);
        sprites[0].setOrigin(0, 0);
    }

    public void draw(SpriteBatch batch) {
        sprites[0].setPosition(getX(), getY());

        for (Sprite s : sprites) {
            s.draw(batch);
        }
    }

}
