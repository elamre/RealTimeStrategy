package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rts.game.screens.ShapeRenderer;
import com.rts.networking.packets.game.EntityCreationPacket;
import com.rts.networking.packets.game.MoveEntityPacket;
import com.rts.util.Logger;


/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/11/13
 * Time: 7:53 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Entity {
    private final int id;
    private final int entityType;
    private final float debugThreshold = 1;
    protected float width = 0, height = 0;
    /* The angle of the entity IN DEGREES. */
    protected boolean debug = false;
    protected float x;
    protected float y;
    protected float angle;
    private TextureRegion textureRegion;
    private float debugTimer = 0;

    //USE THIS ONLY FOR SENDING NETWORK DETAILS!
    public Entity(int x, int y, int entityType) {
        this.entityType = entityType;
        this.id = 0;
        this.x = x;
        this.y = y;
    }

    public Entity(int id, int x, int y, int entityType) {
        this.entityType = entityType;
        this.id = id;
        this.x = x;
        this.y = y;
        onCreate();
    }

    public Entity(EntityCreationPacket packet, int entityType) {
        this.entityType = entityType;
        this.id = packet.getEntityId();
        this.x = packet.getX();
        this.y = packet.getY();
        onCreate();
    }

    public abstract void onCreate();

    public void update(float deltaT) {
        if (debug) {
            if (debugTimer < debugThreshold)
                debugTimer += deltaT;
            else {
                debugTimer = 0;
                Logger.getInstance().debug(toString());
            }
        }
        implementUpdate_1(deltaT);
    }

    public abstract void implementUpdate_1(float deltaT);

    public void draw(SpriteBatch spriteBatch) {
        implementDraw_1(spriteBatch);
        if (debug) {
            drawDebug(spriteBatch);
        }
        if (textureRegion != null) {
            spriteBatch.draw(textureRegion, x + (1 - width), y + (1 - height), width / 2, height / 2, width, height, 1, 1, angle);
            //Logger.getInstance().debug("Drawing something at: " + toString());
        }
    }

    public abstract void implementDraw_1(SpriteBatch spriteBatch);

    protected void drawDebug(SpriteBatch spriteBatch) {
        ShapeRenderer.drawRectangle((int) x, (int) y, (int) width, (int) height, false);
    }

    public float getDistance(float x, float y) {
        return (float) Math.sqrt((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y));
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getOriginX() {
        return x + width / 2;
    }

    public float getOriginY() {
        return y + height / 2;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    protected void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        //this.width = textureRegion.getRegionWidth();
        //this.height = textureRegion.getRegionHeight();
        this.width = 0.8f;
        this.height = 0.8f;
    }

    public String toString() {
        return "id: " + this.id + " [" + (int) getX() + "," + (int) getY() + "]";
    }

    public MoveEntityPacket getMovePacket() {
        return null;
    }
}















