package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rts.game.abilities.Ability;
import com.rts.networking.mutual.packets.EntityCreation;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/23/13
 * Time: 5:32 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Unit extends Entity {

    public ArrayList<Ability> abilities;

    protected Unit(int x, int y, int entityType) {
        super(x, y, entityType);
        abilities = new ArrayList<Ability>(4);
    }

    public Unit(int id, int x, int y, int entityType, TextureRegion textureRegion) {
        super(id, x, y, entityType);
        setTextureRegion(textureRegion);
        abilities = new ArrayList<Ability>(4);
    }

    public Unit(EntityCreation entityCreation, int entityType, TextureRegion textureRegion) {
        super(entityCreation, entityType);
        if (textureRegion != null)             //animation class will handle it itself. See MovingUnit
            setTextureRegion(textureRegion);
        abilities = new ArrayList<Ability>(4);
    }

    @Override
    public void implementUpdate_1(float deltaT) {
        for (int i = 0, l = abilities.size(); i < l; i++) {
            abilities.get(i).logic(deltaT);
        }
/*        for (Ability a : abilities) {
            a.logic(deltaT);
        }*/

        implementUpdate_2(deltaT);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public float faceAt(float x, float y) {
        float tempAngle = (float) Math.atan2(x - this.x, y - this.y);
        angle = -(float) Math.toDegrees(tempAngle);
        System.out.println(angle);
        return angle;
    }

    public float faceAt(float angle) {
        this.angle = angle;
        return angle;
    }

    public abstract void implementUpdate_2(float deltaT);

    @Override
    public void implementDraw_1(SpriteBatch spriteBatch) {
        implementDraw_2(spriteBatch);
        for (Ability a : abilities) {
            a.draw();
        }
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public abstract void implementDraw_2(SpriteBatch spriteBatch);

}
