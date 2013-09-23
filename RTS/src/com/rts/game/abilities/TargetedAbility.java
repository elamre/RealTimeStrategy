package com.rts.game.abilities;

import com.rts.game.entities.Unit;
import com.rts.game.gameplay.Cursor;
import com.rts.game.hud.AbilityButton;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/22/13
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class TargetedAbility extends Ability {
    /** The maximum range from unit to target in which this ability will function */
    protected int range = 10000;
    /** If the ability is active at the moment */
    private boolean active = false;

    /**
     * Constructor
     *
     * @param owner         the owner of the ability
     * @param hotKey        the hot-key used to activate this ability
     * @param abilityButton the button related to the ability
     */
    public TargetedAbility(Unit owner, int hotKey, AbilityButton abilityButton) {
        super(owner, hotKey, abilityButton);
    }

    /** This function will change the mouse-cursor */
    @Override
    public void action() {
        logger.debug("Setting ability of the cursor");
        Cursor.abilityRequesting = this;
        active = true;
    }

    /** This function will set all the parameters back to default so that this ability can start over again */
    public void deselect() {
        active = false;
        Cursor.abilityRequesting = null;
    }

    /**
     * This function and will be once there is a mouse button press detected, and when the ability is active
     *
     * @param x the x position the ability is called on
     * @param y the y position the ability is called on
     */
    public void fireAction(float x, float y) {
        if (!trigger) {
            if (inRange(x, y) || range == -1) {
                action_1(x, y);
            }
            deselect();
        }
    }

    /**
     * Implement this function for the actual action to be executed
     *
     * @param x the x position the ability is called on
     * @param y the y position the ability is called on
     */
    public abstract void action_1(float x, float y);

    /**
     * This function will check if the position on which the ability is called on is in range
     *
     * @param x the x of the position
     * @param y the y of the position
     * @return true if the position is in range, false if otherwise
     */
    public boolean inRange(float x, float y) {
        int tempRange = 0;
        tempRange = (int) getDistance(owner.getX(), owner.getY(), x, y);
        if (tempRange <= range)
            return true;
        return false;
    }

    /**
     * Function which will return the distance between 2 points given
     *
     * @param x  x vector of point 1
     * @param y  y vector of point 1
     * @param x2 x vector of point 2
     * @param y2 y vector of point 2
     * @return distance between point 1 and 2
     */
    protected float getDistance(float x, float y, float x2, float y2) {
        return (float) Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
    }

    /**
     * This function will return active. If active is true, then the mouse has this ability captured
     *
     * @return active
     */
    public boolean isActive() {
        return active;
    }
}
