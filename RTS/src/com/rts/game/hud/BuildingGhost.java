package com.rts.game.hud;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.entities.Entity;
import com.rts.game.entities.EntityList;
import com.rts.game.gameplay.Camera;

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
        x = (int) Camera.getRealWorldX();
        y = (int) Camera.getRealWorldY();
    }

    @Override
    public void implementDraw_1(SpriteBatch spriteBatch) {

    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(sprite, x - (1 - width) * 2, y - (1 - height) * 2, width / 2, height / 2, width, height, 1, 1, angle);

    }

    public void changeEntity(int newEntityId) {
        height = 1;
        width = 1;
        sprite = new Sprite(EntityList.getTextureArea(newEntityId));
        sprite.setColor(sprite.getColor().r, sprite.getColor().g, sprite.getColor().b, .3f);
    }
}
