package com.rts.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rts.game.Assets;
import com.rts.game.entities.EntityList;
import com.rts.game.entities.TestBuilding;
import com.rts.game.gameplay.Camera;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/8/13
 * Time: 11:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildingHUD {
    BuildingButton houseButton;
    private Sprite hudBuildBar;

    BuildingHUD() {
        hudBuildBar = Assets.getAssets().getSprite("UI/build_hud");
        hudBuildBar.flip(false, true);

        hudBuildBar.setPosition(0, Gdx.graphics.getHeight() - hudBuildBar.getHeight());

        houseButton = new BuildingButton(10, 10 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("UI/house_button"), EntityList.getEntityType(TestBuilding.class), "HOUSE");
    }

    public void update() {
        houseButton.update();
    }

    public void draw() {
        hudBuildBar.draw(Camera.batch);
        houseButton.draw();
    }
}
