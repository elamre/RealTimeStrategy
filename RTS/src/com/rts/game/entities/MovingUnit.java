package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rts.networking.packets.game.EntityCreationPacket;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/28/13
 * Time: 8:35 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class MovingUnit extends Unit {
    protected float deltaX = 0;
    protected float deltaY = 0;
    protected float speed = 100;
    private Animation animation;
    private float stateTime = 0;

    protected MovingUnit(int x, int y, int entityType) {
        super(x, y, entityType);
    }

    public MovingUnit(int id, int x, int y, int entityType, TextureRegion textureRegion) {
        super(id, x, y, entityType, textureRegion);
    }

    public MovingUnit(EntityCreationPacket packet, int entityType, TextureRegion textureRegion) {
        super(packet, entityType, textureRegion);
    }

    public MovingUnit(EntityCreationPacket packet, int entityType, TextureRegion textureRegion, int frames, float frameSpeed) {
        super(packet, entityType, null);
        TextureRegion animationRegions[] = new TextureRegion[frames];
        for (int i = 0; i < frames; i++) {
            animationRegions[i] = textureRegion.split(textureRegion.getRegionWidth() / frames, textureRegion.getRegionHeight())[0][i];
        }
        animation = new Animation(frameSpeed, animationRegions);
        animation.setPlayMode(Animation.LOOP_PINGPONG);
        System.out.println("Frame amount: " + animationRegions.length);
        setTextureRegion(animationRegions[0]);
    }

    @Override
    public void implementUpdate_2(float deltaT) {
        if (animation != null) {
            if (speed != 0) {
                stateTime += deltaT;
                setTextureRegion(animation.getKeyFrame(stateTime));
                if (stateTime >= animation.animationDuration * 2) {
                    stateTime -= animation.animationDuration * 2;
                }
            }
        }
        this.x -= deltaX * deltaT * speed;
        this.y -= deltaY * deltaT * speed;
        implementUpdate_3(deltaT);
    }

    public abstract void implementUpdate_3(float deltaT);

    @Override
    public void implementDraw_2(SpriteBatch spriteBatch) {
        implementDraw_3(spriteBatch);

    }

    public abstract void implementDraw_3(SpriteBatch spriteBatch);

    public float getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(float deltaX) {
        this.deltaX = deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
