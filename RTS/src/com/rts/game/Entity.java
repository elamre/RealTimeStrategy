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

    private float x;
    private float y;

    Sprite[] sprites;

    public Entity() {
        create();
    }

    public void create() {

    }

    public void update() {

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
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

}
