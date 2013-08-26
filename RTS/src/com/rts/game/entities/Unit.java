package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.networking.packets.system.EntityCreationPacket;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/23/13
 * Time: 5:32 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Unit extends Entity {
    private float angle = 0;

    protected Unit(int x, int y) {
        super(x, y);
    }

    public Unit(int id, int x, int y, Sprite sprite) {
        super(id, x, y);
        setSprite(sprite);
    }

    public Unit(EntityCreationPacket packet, Sprite sprite) {
        super(packet);
        setSprite(sprite);
    }

    @Override
    public void implementUpdate_1(float deltaT) {
        implementUpdate_2(deltaT);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void faceAt(float x, float y) {
        float tempAngle = (float) Math.atan2(x - this.x, y - this.y);
        angle = -(float) Math.toDegrees(tempAngle);
        getSprite().setPosition(getX() + width / 2, getY() + height / 2);
        getSprite().setRotation(angle);
    }

    public void faceAt(int angle) {
        this.angle = angle;
        getSprite().setPosition(getX() + width / 2, getY() + height / 2);
        getSprite().setRotation(angle);
    }

    public abstract void implementUpdate_2(float deltaT);

    @Override
    public void implementDraw_1(SpriteBatch spriteBatch) {
        implementDraw_2(spriteBatch);
        getSprite().draw(spriteBatch);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public abstract void implementDraw_2(SpriteBatch spriteBatch);
}
