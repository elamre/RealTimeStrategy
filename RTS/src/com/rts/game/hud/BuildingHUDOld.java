package com.rts.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rts.game.Assets;
import com.rts.game.entities.EntityList;
import com.rts.game.entities.TestBuilding;
import com.rts.game.gameplay.Camera;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/8/13
 * Time: 11:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildingHUDOld {
    public static ButtonSet buttonSet;
    private ArrayList<BuildingHUDButton> buttons = new ArrayList<BuildingHUDButton>();
    private HashMap<Integer, BuildingHUDButton> idToButton = new HashMap<Integer, BuildingHUDButton>();
    private BuildingHUDButton build;
    private BuildingHUDButton cancel;
    private BuildingHUDButton deselect;
    private BuildingHUDButton move;
    private View currentView = View.GENERAL;
    private BuildingButton houseButton;
    private BuildingButton archeryButton;
    private BuildingButton castleButton;
    private BuildingButton towerButton;
    private BuildingButton sawmillButton;
    private BuildingButton citycenterButton;
    private Sprite hudBuildBar;

    BuildingHUDOld() {
        hudBuildBar = Assets.getAssets().getSprite("HUD");
        hudBuildBar.flip(false, true);

        hudBuildBar.setPosition(0, Gdx.graphics.getHeight() - hudBuildBar.getHeight());
        build = new BuildingHUDButton(10, 12 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("build_button"), "Build", true, View.GENERAL, new ButtonAble() {
            @Override
            public void action() {
                currentView = View.BUILDINGS;
            }
        });
        move = new BuildingHUDButton(52, 12 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("move_button"), "Move", true, View.GENERAL, new ButtonAble() {
            @Override
            public void action() {
                //move
            }
        });
        deselect = new BuildingHUDButton(94, 12 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("cancel_button"), "Deselect", true, View.GENERAL, new ButtonAble() {
            @Override
            public void action() {
                //move
            }
        });
        cancel = new BuildingHUDButton(178, 96 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("cancel_button"), "Move", true, View.BUILDINGS, new ButtonAble() {
            @Override
            public void action() {
                currentView = View.GENERAL;
            }
        });
        houseButton = new BuildingButton(10, 12 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("house_button"), EntityList.getEntityType(new TestBuilding()), "HOUSE", View.BUILDINGS);
        archeryButton = new BuildingButton(52, 12 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("archery_button"), 0, "HOUSE", View.BUILDINGS);
        castleButton = new BuildingButton(94, 12 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("castle_button"), 0, "HOUSE", View.BUILDINGS);
        towerButton = new BuildingButton(136, 12 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("tower_button"), 0, "HOUSE", View.BUILDINGS);
        citycenterButton = new BuildingButton(178, 12 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("citycenter_button"), 0, "HOUSE", View.BUILDINGS);
        sawmillButton = new BuildingButton(10, 54 + (int) hudBuildBar.getY(), Assets.getAssets().getSprite("sawmill_button"), 0, "HOUSE", View.BUILDINGS);
    }

    private void switchView() {

    }

    public void update() {
        if (buttonSet == ButtonSet.WORKER) {
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
        } else if (buttonSet == ButtonSet.SOLDIER) {
            move.draw();
            deselect.draw();
        }
    }

    public void draw() {
        hudBuildBar.draw(Camera.batch);
        if (buttonSet == ButtonSet.WORKER) {
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
        } else if (buttonSet == ButtonSet.SOLDIER) {
            move.draw();
            deselect.draw();
        }
    }

    static enum View {
        NOTHING, GENERAL, BUILDINGS;
    }

    public static enum ButtonSet {
        WORKER, SOLDIER, NONE;
    }
}
