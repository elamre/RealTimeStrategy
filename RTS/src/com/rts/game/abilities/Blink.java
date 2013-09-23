package com.rts.game.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.entities.Unit;
import com.rts.game.gameplay.Camera;
import com.rts.game.gameplay.World;
import com.rts.game.hud.AbilityButton;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 9/1/13
 * Time: 12:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class Blink extends TargetedAbility {

    public Blink(Unit owner, AbilityButton abilityButton) {
        super(owner, Input.Keys.B, abilityButton);
    }

    @Override
    public void update_1(float delta) {

    }

    /**
     * General draw function
     *
     * @param spriteBatch the batch to draw everything on
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Implement this function for the actual action to be executed
     *
     * @param x the x position the ability is called on
     * @param y the y position the ability is called on
     */
    @Override
    public void action_1(float x, float y) {
        float x1 = owner.getX();
        float y1 = owner.getY();
        float x2 = 0.5f + (int) Camera.getRealWorldX();
        float y2 = 0.5f + (int) Camera.getRealWorldY();
        float tempAngle = -(float) Math.atan2(x2 - x1, y2 - y1);
        if (getDistance(x1, y1, x2, y2) <= range && World.jps.grid.walkable((int) x2, (int) y2)) {
            owner.setX(x2);
            owner.setY(y2);
            logger.debug("Blink in range, going to spot normally");
        } else {
            int dx = (int) (x1 - (range * Math.cos(tempAngle - Math.PI / 2)));
            int dy = (int) (y1 - (range * Math.sin(tempAngle - Math.PI / 2)));
            if (World.jps.grid.walkable(dx, dy)) {
                owner.setX(dx + 0.5f);
                owner.setY(dy + 0.5f);
                logger.debug("Blinked to location with a distance of range");
            } else {
                logger.debug("Invalid location for blink");
            }
        }
    }
}
