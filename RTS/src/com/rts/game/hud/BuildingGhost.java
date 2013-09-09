package com.rts.game.hud;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.entities.Entity;
import com.rts.game.entities.EntityList;
import com.rts.game.gameplay.Cursor;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/7/13
 * Time: 8:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildingGhost extends Entity {
    Sprite sprite;

    public BuildingGhost(int x, int y) {
        super(x, y, 0);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void implementUpdate_1(float deltaT) {
        if (sprite != null)
            sprite.setPosition(Cursor.x, Cursor.y);
    }

    @Override
    public void implementDraw_1(SpriteBatch spriteBatch) {
        if (sprite != null)
            sprite.draw(spriteBatch);
    }

    public void changeEntity(int newEntityId) {
        sprite = new Sprite(EntityList.getTextureArea(newEntityId));
        sprite.setColor(sprite.getColor().r, sprite.getColor().g, sprite.getColor().b, .3f);
    }
}
