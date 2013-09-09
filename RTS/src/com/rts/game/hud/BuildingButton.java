package com.rts.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rts.game.screens.ShapeRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/8/13
 * Time: 9:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildingButton extends Button {
    BuildingGhost buildingGhost;
    int id;

    public BuildingButton(int x, int y, Sprite sprite, int id, String text, BuildingGhost buildingGhost) {
        super(x, y, sprite, text, true);
        this.buildingGhost = buildingGhost;
        this.id = id;
    }

    @Override
    public void mouseOver(Button button) {
        //TODO show some text
        ShapeRenderer.setColor(Color.BLUE);
        ShapeRenderer.drawRectangle(button.x,button.y,32,32,true);
        ShapeRenderer.setColor(Color.BLACK);
        ShapeRenderer.drawRectangle(button.x,button.y,72,32,false);
    }

    @Override
    public void buttonPressed(Button button) {
        //TODO play a sound
    }

    @Override
    public void buttonReleased(Button button) {
        buildingGhost.changeEntity(id);
    }

    @Override
    public void buttonHold(Button button) {
        //TODO figure out something
    }
}
