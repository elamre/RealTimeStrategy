package com.rts.game.hud;

import com.rts.game.gameplay.Camera;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/7/13
 * Time: 8:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class HUD {
    private Resources resources;
    private BuildingHUD buildingHUD;

    public HUD() {
        resources = new Resources();
        buildingHUD = new BuildingHUD();
    }

    public void update() {
        buildingHUD.update();
    }

    public BuildingHUD getBuildingHUD() {
        return buildingHUD;
    }

    public void draw() {
        Camera.makeHUDBatch();
        resources.draw();
        buildingHUD.draw();
        Camera.makeWorldBatch();
    }

    public boolean isOnHud(int x, int y) {
        return buildingHUD.contains(x, y);
    }
}
