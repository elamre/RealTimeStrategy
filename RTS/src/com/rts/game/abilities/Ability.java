package com.rts.game.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.entities.SelectableUnit;
import com.rts.game.entities.Unit;
import com.rts.game.hud.AbilityButton;
import com.rts.util.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/22/13
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Ability {
    /** The owner of the ability */
    protected Unit owner;
    /** The button related to the ability */
    protected AbilityButton abilityButton;
    /** The hot-key that can be used for this ability */
    protected int hotKey = -1;
    /** If the ability is triggered */
    protected boolean trigger = false;
    /** If the ability is enabled */
    protected boolean enabled = true;
    /** Logger instance useful for debugging */
    protected Logger logger = Logger.getInstance();
    /** The ability type */
    private int abilityType = -1;

    /**
     * Constructor
     *
     * @param owner         the owner of the ability
     * @param hotKey        the hot-key used to activate this ability
     * @param abilityButton the button related to the ability
     */
    public Ability(Unit owner, int hotKey, AbilityButton abilityButton) {
        this.abilityButton = abilityButton;
        this.hotKey = hotKey;
        this.owner = owner;
    }

    /** the action to be executed for the ability */
    public abstract void action();

    /**
     * Update function which checks for the hotkey pressed and updates the ability
     *
     * @param deltaT the time that has passed since the previous update
     */
    public void update(float deltaT) {
        boolean tempButton = false;
        boolean tempSelected = true;
        if(owner instanceof SelectableUnit){
            tempSelected = ((SelectableUnit) owner).isSelected();
        }
        if (abilityButton != null) {
            tempButton = abilityButton.isPressed();
        }
        if ((Gdx.input.isKeyPressed(hotKey) || tempButton) && !trigger && enabled && tempSelected) {
            trigger = true;
            action();
            logger.debug("Ability action!");
        } else {
            if (!Gdx.input.isKeyPressed(hotKey) && !tempButton) {
                trigger = false;
            }
        }
        update_1(deltaT);
    }

    /**
     * Update function for anything ability related
     *
     * @param deltaT the time that has passed since the previous update
     */
    public abstract void update_1(float deltaT);

    /**
     * General draw function
     *
     * @param spriteBatch the batch to draw everything on
     */
    public abstract void draw(SpriteBatch spriteBatch);

    /**
     * if the button is enabled or not
     *
     * @return enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Setter for enabled
     *
     * @param enabled new enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * returns the hot-key this ability listens to
     *
     * @return current hot-key
     */
    public int getHotKey() {
        return hotKey;
    }

    /**
     * Setter for the hot-key
     *
     * @param hotKey new hot-key
     */
    public void setHotKey(int hotKey) {
        this.hotKey = hotKey;
    }

    /**
     * returns the type of the ability which is set in the constructor TODO
     *
     * @return entityType
     */
    public int getAbilityType() {
        return abilityType;
    }

    /**
     * returns a button if set
     *
     * @return the ability button
     */
    public AbilityButton getAbilityButton() {
        return abilityButton;
    }
}
