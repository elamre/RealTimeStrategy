package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/20/13
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShadowEntity extends Entity {
    private int id;

    ShadowEntity(TextureRegion textureRegion, int id) {
        setTextureRegion(textureRegion);
        this.id = id;

    }

    public int getId() {
        return id;
    }

    @Override
    public void onCreate() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void implementUpdate_1(float deltaT) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void changeCoordinates(Entity entity) {
        if (entity != null) {
            this.x = entity.getX();
            this.y = entity.getY();
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        if (textureRegion != null) {
            spriteBatch.setColor(spriteBatch.getColor().r, spriteBatch.getColor().g, spriteBatch.getColor().b, 0.4f);
            spriteBatch.draw(textureRegion, x+90, y+90, width / 2, height / 2, width, height, 1, 1, 90);
            spriteBatch.setColor(spriteBatch.getColor().r, spriteBatch.getColor().g, spriteBatch.getColor().b, 1f);
        }
    }

    @Override
    public void implementDraw_1(SpriteBatch spriteBatch) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
