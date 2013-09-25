package com.rts.game.abilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.Assets;
import com.rts.game.entities.SelectableUnit;
import com.rts.game.entities.Unit;
import com.rts.game.gameplay.Cursor;
import com.rts.game.gameplay.Player;
import com.rts.game.hud.AbilityButton;
import com.rts.game.screens.InGame;

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
     * @param owner the owner of the ability
     */
    public Deselect(Unit owner) {
        super(owner, Input.Keys.P, new AbilityButton(Assets.getAssets().getSprite("cancel_button"), 14));
    }

    /** the action to be executed for the ability */
    @Override
    public void action() {
        if (owner instanceof SelectableUnit) {
            ((SelectableUnit) owner).setSelected(false);
        }
        Cursor.abilityRequesting = null;
        InGame.player.cancel();
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
