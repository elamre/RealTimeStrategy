package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.rts.game.gameplay.Camera;
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
    protected int width = 0, height = 0;
    /* The angle of the entity IN DEGREES. */
    protected boolean debug = false;
    protected float x, y;
    private Sprite sprite;
    private Rectangle hitBox = new Rectangle(0, 0, 1, 1);
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
        sprite.setPosition(x, y);
        implementUpdate_1(deltaT);
        hitBox.set(x, y, width, height);
    }

    public abstract void implementUpdate_1(float deltaT);

    public void draw(SpriteBatch spriteBatch) {
        if (debug)
            drawDebug(spriteBatch);
        implementDraw_1(spriteBatch);
    }

    public abstract void implementDraw_1(SpriteBatch spriteBatch);

    protected void drawDebug(SpriteBatch spriteBatch) {
        ShapeRenderer box = new ShapeRenderer();
        box.begin(ShapeRenderer.ShapeType.FilledRectangle);
        box.setProjectionMatrix(Camera.getOrthographicCamera().combined);
        box.setColor(1, 0, 1, 0.1f);
        box.filledRect(x, y, hitBox.getWidth(), hitBox.getHeight());
        box.end();
        //TODO draw rectangle or sumtin
    }

    public float getDistance(float x, float y) {
        return (float) Math.sqrt((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y));
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getOriginX() {
        return x + width / 2;
    }

    public float getOriginY() {
        return y + height / 2;
    }

    public Sprite getSprite() {
        return sprite;
    }

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
        //this.width = (int) sprite.getWidth();
        //this.height = (int) sprite.getHeight();
        this.width = 1;
        this.height = 1;
        sprite.setSize(1, 1);
    }

    public String toString() {
        return "id: " + this.id + " [" + (int) getX() + "," + (int) getY() + "]";
    }

    public MoveEntityPacket getMovePacket() {
        return null;
    }
}















