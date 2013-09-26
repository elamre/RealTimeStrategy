package com.rts.game.abilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.entities.Entity;
import com.rts.game.entities.Unit;
import com.rts.game.hud.AbilityButton;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/26/13
 * Time: 9:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class GatherAbility extends TargetedAbility {
    /** The entity to get resources of */
    private Entity resourceEntity;
    /** The entity to return to with resources */
    private Entity dropEntity;
    /** The state of the entity */
    private GatheringState gatheringState;

    /**
     * Constructor
     *
     * @param owner the owner of the ability
     */
    public GatherAbility(Unit owner) {
        super(owner, Input.Keys.G, null);
    }

    /**
     * Implement this function for the actual action to be executed
     *
     * @param x the x position the ability is called on
     * @param y the y position the ability is called on
     */
    @Override
    public void action_1(float x, float y) {
        /** TODO set the resource and look  for the closest drop point */
    }

    /**
     * Update function for anything ability related
     *
     * @param deltaT the time that has passed since the previous update
     */
    @Override
    public void update_1(float deltaT) {
        switch (gatheringState) {
            case IDLE:
                /** TODO not sure what to do here */
                break;
            case GATHERING:
                /** TODO increment timer */
                break;
            case RETURN_TO_RESOURCE:
                /** TODO check if the resource is still there, if not choose nearest visible resource equally to the one
                 * TODO gathered from previously. Also check if we arrived at the resource yet   */
                break;
            case RETURN_TO_DROP:
                /** TODO check if the dropping site is still there, if not choose nearest allied dropping site
                 * TODO Also check if we arrived at the dropping site yet   */
                break;
        }
    }

    /**
     * General draw function
     *
     * @param spriteBatch the batch to draw everything on
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        /** TODO draw progress if we are gathering */
    }

    private enum GatheringState {
        IDLE, GATHERING, RETURN_TO_RESOURCE, RETURN_TO_DROP
    }
}
