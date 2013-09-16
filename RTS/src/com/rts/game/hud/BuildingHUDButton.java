package com.rts.game.hud;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/16/13
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildingHUDButton extends Button {
    ButtonAble buttonAble;
    BuildingHUD.View view;

    public BuildingHUDButton(int x, int y, Sprite sprite, String text, boolean mouseOver, BuildingHUD.View view, ButtonAble buttonAble) {
        super(x, y, sprite, text, mouseOver);
        this.buttonAble = buttonAble;
        this.view = view;
    }

    @Override
    public void mouseOver(Button button) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void buttonPressed(Button button) {
        buttonAble.action();
    }

    @Override
    public void buttonReleased(Button button) {
    }

    @Override
    public void buttonHold(Button button) {
    }

    public void update(BuildingHUD.View currentView) {
        if (view == currentView) {
            setEnabled(true);
            update();
        } else {
            setEnabled(false);
        }
    }
}
