package com.rts.game.hud;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rts.game.abilities.Ability;
import com.rts.game.gameplay.Camera;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/20/13
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbilityButton extends Button {
    private int preferredPlace = -1;

    public AbilityButton(Sprite sprite, int preferredPlace) {
        super(0, 0, sprite, "", false);
        this.preferredPlace = preferredPlace;
    }

    public int getPreferredPlace() {
        return preferredPlace;
    }

    @Override
    public void mouseOver(Button button) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void buttonPressed(Button button) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void buttonReleased(Button button) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void buttonHold(Button button) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
