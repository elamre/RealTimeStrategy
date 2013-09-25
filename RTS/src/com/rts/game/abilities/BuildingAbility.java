package com.rts.game.abilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.entities.Unit;
import com.rts.game.hud.AbilityButton;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/25/13
 * Time: 9:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildingAbility extends TargetedAbility {
    /**
     * Constructor
     *
     * @param owner         the owner of the ability
     * @param hotKey        the hot-key used to activate this ability
     * @param abilityButton the button related to the ability
     */
    public BuildingAbility(Unit owner, int hotKey, AbilityButton abilityButton) {
        super(owner, hotKey, abilityButton);
    }

    /**
     * Implement this function for the actual action to be executed
     *
     * @param x the x position the ability is called on
     * @param y the y position the ability is called on
     */
    @Override
    public void action_1(float x, float y) {

    }

    /**
     * Update function for anything ability related
     *
     * @param deltaT the time that has passed since the previous update
     */
    @Override
    public void update_1(float deltaT) {

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
}
