package com.rts.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.entities.Entity;
import com.rts.game.entities.EntityList;
import com.rts.game.gameplay.Camera;
import com.rts.networking.mutual.packets.EntityCreation;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/7/13
 * Time: 8:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildingGhost {
    public EntityCreation position;
    Sprite sprite;
    Color color;
    private int width, height;

    public BuildingGhost() {
        color = new Color();
        position = new EntityCreation();
    }

    public void update(float deltaT) {
        position.x = (int) Camera.getRealWorldX();
        position.y = (int) Camera.getRealWorldY();
        sprite.setPosition(position.x, position.y);
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.setColor(color);
        spriteBatch.draw(sprite, position.x - width / 2, position.y - height / 2, width / 2, height / 2, width, height, 1, 1, 0);
        spriteBatch.setColor(1, 1, 1, 1);
    }

    public void changeEntity(int newEntityId) {
        position.entityType = newEntityId;
        Entity spriteEntity = null;
        try {
            spriteEntity = EntityList.getEntity(newEntityId).getConstructor(EntityCreation.class).newInstance(new EntityCreation());
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        sprite = new Sprite(spriteEntity.getTextureRegion());
        sprite.setColor(1f, 1f, .5f, .3f);
    }

    public void setUnable() {
        color.set(1f, 0.5f, 0.5f, 0.8f);
    }

    public void setPossible() {
        color.set(1f, 1f, 1f, 0.8f);
    }

    public void setWidth(int width) {
        sprite.setScale(width, sprite.getScaleY());
        this.width = width;
    }

    public void setHeight(int height) {
        sprite.setScale(sprite.getScaleX(), height);
        this.height = height;
    }

    public void setScale(int width, int height) {
        sprite.setScale(0.08f);
        sprite.setOrigin(width / 2, height / 2);
        this.width = width;
        this.height = height;

    }
}
