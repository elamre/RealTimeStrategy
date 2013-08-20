package com.rts.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rts.networking.packets.system.EntityCreationPacket;


/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/11/13
 * Time: 7:53 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Entity {
    public BoundingShape bounds;
    Sprite[] sprites;
    private int id;
    private int owner;

    public Entity(Sprite sprite[], int x, int y) {
        sprites = sprite;
        setUpBoundaries();
        setX(x);
        setY(y);
        create();
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public void create() {

    }

    protected void setUpBoundaries() {
        bounds = new BoundingShape(0, 0, 1f, 1f);
        bounds = new BoundingShape(0, 0, 1f);
    }

    public void update(float delta) {
        bounds.x = getX();
        bounds.y = getY();
    }

    public void draw(SpriteBatch batch) {

    }

    public void delete() {

    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id.intValue();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInteger() {
        return new Integer(id);
    }

    public float getY() {
        return bounds.y;
    }

    public void setY(float y) {
        this.bounds.y = y;
    }

    public float getX() {
        return bounds.x;
    }

    public void setX(float x) {
        this.bounds.x = x;
    }

    public void setNetworkDetails(EntityCreationPacket packet) {
        this.bounds.x = packet.getX();
        this.bounds.y = packet.getY();
        this.id = packet.getEntityId();
        System.out.println(toString());
    }

    public String toString() {
        return "[" + getX() + "," + getY() + "]" + " id : " + this.id;
    }

}
