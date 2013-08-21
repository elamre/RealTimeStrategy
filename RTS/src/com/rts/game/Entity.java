package com.rts.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    protected Sprite sprite;
    private int id;
    private int owner;

    public float getAngle() {
        return angle;
    }

    public float getAngleRadians() {
        return (float) Math.toRadians(angle);
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float face(float[] pos) {
        float a = (float) Math.atan2(getX() - (double) pos[0], getY() - (double) pos[1]);
        a = -(float) Math.toDegrees(a);
        angle = a;
        return a;
    }

    /**
     * The angle of the entity IN DEGREES.
     */
    float angle;

    public Entity(Sprite sprite, int x, int y) {
        this.sprite = sprite;
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
        boundsPhysics();
    }

    public void boundsPhysics() {
        bounds.x = getX();
        bounds.y = getY();
    }

    public void draw(SpriteBatch batch) {
        sprite.setSize(1f, 1f);
        sprite.setOrigin(0, 0);
        sprite.setPosition(getX(), getY());
        sprite.setRotation((float) Math.toDegrees(getAngle()));
        sprite.draw(batch);
    }

    public void delete() {

    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdInteger() {
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

    public float[] getPosition() {
        return new float[]{getX(), getY()};
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
