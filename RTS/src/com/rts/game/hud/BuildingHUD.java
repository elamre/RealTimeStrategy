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
    BuildingHUDButton build;
    BuildingHUDButton cancel;
    BuildingHUDButton deselect;
    BuildingHUDButton move;
    View currentView = View.GENERAL;
    BuildingButton houseButton;
    BuildingButton archeryButton;
    BuildingButton castleButton;
    BuildingButton towerButton;
    BuildingButton sawmillButton;
    BuildingButton citycenterButton;
    private Sprite hudBuildBar;

    BuildingHUD() {
        hudBuildBar = Assets.getAssets().getSprite("UI/build_hud");
        hudBuildBar.flip(false, true);

        hudBuildBar.setPosition(0, Gdx.graphics.getHeight() - hudBuildBar.getHeight());
        build = new BuildingHUDButton(10, 10 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("UI/build_button"), "Build", true, View.GENERAL, new ButtonAble() {
            @Override
            public void action() {
                currentView = View.BUILDINGS;
            }
        });
        move = new BuildingHUDButton(50, 10 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("UI/move_button"), "Move", true, View.GENERAL, new ButtonAble() {
            @Override
            public void action() {
                //move
            }
        });
        deselect = new BuildingHUDButton(90, 10 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("UI/cancel_button"), "Deselect", true, View.GENERAL, new ButtonAble() {
            @Override
            public void action() {
                //move
            }
        });
        cancel = new BuildingHUDButton(170, 80 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("UI/cancel_button"), "Move", true, View.BUILDINGS, new ButtonAble() {
            @Override
            public void action() {
                currentView = View.GENERAL;
            }
        });
        houseButton = new BuildingButton(10, 10 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("UI/house_button"), EntityList.getEntityType(TestBuilding.class), "HOUSE", View.BUILDINGS);
        archeryButton = new BuildingButton(50, 10 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("UI/archery_button"), EntityList.getEntityType(TestBuilding.class), "HOUSE", View.BUILDINGS);
        castleButton = new BuildingButton(90, 10 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("UI/castle_button"), EntityList.getEntityType(TestBuilding.class), "HOUSE", View.BUILDINGS);
        towerButton = new BuildingButton(130, 10 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("UI/tower_button"), EntityList.getEntityType(TestBuilding.class), "HOUSE", View.BUILDINGS);
        citycenterButton = new BuildingButton(170, 10 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("UI/citycenter_button"), EntityList.getEntityType(TestBuilding.class), "HOUSE", View.BUILDINGS);
        sawmillButton = new BuildingButton(10, 50 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("UI/sawmill_button"), EntityList.getEntityType(TestBuilding.class), "HOUSE", View.BUILDINGS);
    }

    private void switchView() {

    }

    public void update() {
        build.update(currentView);
        houseButton.update(currentView);
        move.update(currentView);
        deselect.update(currentView);
        cancel.update(currentView);
        archeryButton.update(currentView);
        castleButton.update(currentView);
        towerButton.update(currentView);
        citycenterButton.update(currentView);
        sawmillButton.update(currentView);
    }

    public void draw() {
        hudBuildBar.draw(Camera.batch);
        houseButton.draw();
        archeryButton.draw();
        castleButton.draw();
        towerButton.draw();
        citycenterButton.draw();
        sawmillButton.draw();
        build.draw();
        move.draw();
        deselect.draw();
        cancel.draw();
    }

    static enum View {
        NOTHING, GENERAL, BUILDINGS;
    }
}
