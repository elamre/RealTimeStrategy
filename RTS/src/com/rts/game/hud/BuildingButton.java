package com.rts.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rts.game.abilities.Ability;
import com.rts.game.abilities.Build;
import com.rts.game.entities.Entity;
import com.rts.game.entities.Unit;
import com.rts.game.screens.InGame;
import com.rts.game.screens.ShapeRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/8/13
 * Time: 9:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildingButton extends Button {
    BuildingHUD.View view;
    int id;

    public BuildingButton(int x, int y, Sprite sprite, int id, String text, BuildingHUD.View view) {
        super(x, y, sprite, text, true);
        this.view = view;
        this.id = id;
    }

    @Override
    public void mouseOver(Button button) {
        //TODO show some text
        ShapeRenderer.setColor(Color.BLUE);
        ShapeRenderer.drawRectangle(button.x, button.y, 32, 32, true);
        ShapeRenderer.setColor(Color.BLACK);
        ShapeRenderer.drawRectangle(button.x, button.y, 72, 32, false);
    }

    @Override
    public void buttonPressed(Button button) {
        //TODO play a sound

        outer:
        for (Entity e : InGame.player.selection.currentSelection) {
            for (Ability a : ((Unit) e).abilities) {
                if (a instanceof Build) {
                    ((Build) a).requestCursorUse(true);
                    break outer;
                }
            }
        }
    }

    @Override
    public void buttonReleased(Button button) {

    }

    @Override
    public void buttonHold(Button button) {
        //TODO figure out something
    }

    public void update(BuildingHUD.View currentView) {
        if (currentView == view) {
            setEnabled(true);
            update();
        } else {
            setEnabled(false);
        }
    }
}
