package com.rts.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;


/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/11/13
 * Time: 7:53 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Entity {
    private int id;

    Sprite[] sprites;

    public BoundingShape bounds;

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    private int owner;

    public Entity() {
        create();
    }

    public void create() {

    }

    protected void setUpBoundaries() {
        //bounds = new BoundingShape(0, 0, 0.05f, 0.05f);
        bounds = new BoundingShape(0, 0, 0.05f);
    }

    public void update(float delta) {

    }

    public void draw(SpriteBatch batch) {

    }

    public void delete() {

    }


    public int getId() {
        return id;
    }

    public int getIdInteger() {
        return new Integer(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id.intValue();
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

}
