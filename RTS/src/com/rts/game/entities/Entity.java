package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rts.game.gameplay.Camera;
import com.rts.game.screens.ShapeRenderer;
import com.rts.networking.mutual.packets.EntityCreation;
import com.rts.networking.mutual.packets.EntityPosChange;
import com.rts.util.Logger;


/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/11/13
 * Time: 7:53 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Entity {
    /* The interval between debug messages */
    private final float debugThreshold = 1;
    protected float width = 0;
    protected float height = 0;
    /* The angle of the entity IN DEGREES. */
    protected boolean debug = false;
    protected float x;
    protected float y;
    protected float angle;
    protected TextureRegion textureRegion;
    /* The id this particular entity goes by */
    private int id;
    /* The type of the entity */
    private int entityType;
    /* The debug timer which will get compared to the threshold */
    private float debugTimer = 0;
    private int owner = 0;

    /**
     * USE THIS ONLY FOR REGISTERING THE ENTITY! SHOULD NOT BE USED OTHERWISE!
     */
    public Entity() {

    }

    //USE THIS ONLY FOR SENDING NETWORK DETAILS!
    public Entity(int x, int y) {
        this.entityType = EntityList.getEntityType(this);
        this.id = -1;
        this.x = x;
        this.y = y;
    }

    public Entity(EntityCreation entityCreation) {
        this.entityType = entityCreation.entityType;
        this.id = entityCreation.id;
        this.x = entityCreation.x;
        this.y = entityCreation.y;
        this.owner = entityCreation.owner;
        onCreate();
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getEntityType() {
        if (entityType == 0)
            entityType = EntityList.getEntityType(this);
        return entityType;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
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
        if (Camera.isInFocus(x, y, width, height)) {
            implementDraw_1(spriteBatch);
            if (debug) {
                drawDebug();
            }
            if (textureRegion != null) {
                spriteBatch.draw(textureRegion, x + (1 - width), y + (1 - height), width / 2, height / 2, width, height, 1, 1, angle);
                //spriteBatch.draw(textureRegion, x - (1 - width) * 2, y - (1 - height) * 2, width / 2, height / 2, width, height, 1, 1, angle);
            } else {
                Logger.getInstance().debug("Sprite region is null");
            }
        }
    }

    public abstract void implementDraw_1(SpriteBatch spriteBatch);

    protected void drawDebug() {
        ShapeRenderer.drawRectangle((int) x, (int) y, (int) width, (int) height, false);
    }

    public float getDistance(float x, float y) {
        return (float) Math.sqrt((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y));
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
        return "id: " + this.id + " [" + (int) getX() + "," + (int) getY() + "] size: [" + width + "," + height + "]";
    }

    public EntityPosChange getMovePacket() {
        return null;
    }
}















