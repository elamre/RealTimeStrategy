package com.rts.game.abilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.entities.Unit;
import com.rts.game.hud.AbilityButton;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/22/13
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Deselect extends Ability {

    /**
     * Constructor
     *
     * @param owner         the owner of the ability
     * @param abilityButton the button related to the ability
     */
    public Deselect(Unit owner, AbilityButton abilityButton) {
        super(owner, Input.Keys.P, abilityButton);
    }

    /** the action to be executed for the ability */
    @Override
    public void action() {
        //owner.setSelected(false);
    }

    /**
     * Update function for anything ability related
     *
     * @param deltaT the time that has passed since the previous update
     */
    @Override
    public void update_1(float deltaT) {
        //To change body of implemented methods use File | Settings | File Templates.
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
