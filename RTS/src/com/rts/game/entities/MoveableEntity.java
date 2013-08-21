package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.Entity;

/**
 * An entity with the ability to move around.
 */
public abstract class MoveableEntity extends Entity {


    float destX = 0;
    float destY = 0;
    boolean flying;
    float speed = 1;

    public MoveableEntity(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }

    public void update(float delta) {
        boundsPhysics();
        moveStep(delta);
    }

    private static float distance(float x, float y, float x2, float y2) {
        return (float) Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
    }

    public void moveStep(float delta) {

        if (destX != getX() && destY != getY()) {

            face(new float[]{destX, destY});
            float moveX = (float) (speed * Math.sin(getAngleRadians()));
            setX(getX() + moveX * delta);
            float moveY = (float) (speed * Math.cos(getAngleRadians()));
            setY(getY() - moveY * delta);

            if (distance(getX(), getY(), destX, destY) < speed * delta) {
                setDestination(getPosition());
            }

        }

    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(getX(), getY());
        sprite.setRotation(getAngle());
        sprite.draw(batch);
    }

    public void setDestination(float[] pos) {
        destX = pos[0];
        destY = pos[1];
    }


}
