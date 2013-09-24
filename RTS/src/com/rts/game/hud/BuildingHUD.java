package com.rts.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rts.game.Assets;
import com.rts.game.gameplay.Camera;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/20/13
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildingHUD {
    /** List containing all the buttons displayed */
    private ArrayList<BuildingHUDGrid> gridButtons = new ArrayList<BuildingHUDGrid>(15);
    /** An array containing all the possible id's */
    private int[] possibleIds = new int[15];
    /** The hud all the abilities will be drawn upon */
    private Sprite abilityHUD;

    /** This is the default constructor. It will initialize all the buttons and set up the id's for them */
    public BuildingHUD() {
        abilityHUD = Assets.getAssets().getSprite("HUD");
        abilityHUD.flip(false, true);
        abilityHUD.setPosition(0, Gdx.graphics.getHeight() - abilityHUD.getHeight());
        int id = 0;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 5; x++) {
                possibleIds[id] = id;
                gridButtons.add(new BuildingHUDGrid(id, 10 + x * 42, (int) abilityHUD.getY() + 12 + y * 42));
                id++;
            }
        }
    }

    /**
     * This function will check if the hud contains a certain point. Useful to check if a point is on the HUD.
     *
     * @param x the x vector of the point
     * @param y the y vector of the point
     * @return true if the hud contains the point. false otherwise
     */
    public boolean contains(int x, int y) {
        boolean contains = false;
        if (x < abilityHUD.getWidth() && y > abilityHUD.getY())
            contains = true;
        return contains;
    }

    /**
     * Use this function to register an ability onto the ability hud
     *
     * @param abilityButton the button to register
     * @param preferred     the preferred place, -1 if don't care
     * @return the id this button has been registered to
     */
    public int registerAbilityButton(AbilityButton abilityButton, int preferred) {
        if (abilityButton == null)
            return -1;
        int tempId = -1;
        int tries = -1;
        if (preferred > -1) {
            for (int i = 0; i < possibleIds.length; i++) {
                if (possibleIds[i] == preferred) {
                    possibleIds[i] = -1;
                    Arrays.sort(possibleIds);
                    tempId = preferred;
                }
            }
        }
        while (tempId == -1) {
            tries++;
            tempId = possibleIds[tries];
            System.out.println("TempId: " + tempId);
            if (tries > 15) {
                System.out.println("Not enough space to place button");
                return -1;
            }
        }
        if (tries > -1 && tries < 16) {
            System.out.println("Setting index: " + tries + " to -1");
            possibleIds[tries] = -1;
        }
        Arrays.sort(possibleIds);
        for (int b = 0, l = gridButtons.size(); b < l; b++) {
            if (gridButtons.get(b).getId() == tempId) {
                System.out.println("Button set at tempId: " + tempId);
                gridButtons.get(b).setButton(abilityButton);
                break;
            }
        }
        return tempId;
    }

    /**
     * this function should update the mouseover and more,
     * Not sure what to do with this function. TODO figure out
     */
    public void update() {
        for (int i = 0, l = gridButtons.size(); i < l; i++) {
            if (!gridButtons.get(i).isFree())
                gridButtons.get(i).update();
        }
    }

    /** This function will draw the hud itself, and all the buttons on it */
    public void draw() {
        abilityHUD.draw(Camera.batch);
        for (int i = 0, l = gridButtons.size(); i < l; i++) {
            if (!gridButtons.get(i).isFree())
                gridButtons.get(i).draw();
        }
    }

    /**
     * This function will remove one button from the bar
     *
     * @param id the id on which the button is registered
     * @return true if successful, false if there was no button with the id
     */
    public boolean removeAbilityButton(int id) {
        int i = -1;
        while (possibleIds[i++] != -1 || i > 15) ;
        possibleIds[i] = id;
        Arrays.sort(possibleIds);
        for (int i2 = 0; i2 < gridButtons.size(); i2++) {
            if (gridButtons.get(i2).getId() == id) {
                gridButtons.get(i2).releaseButton();
                return true;
            }
        }
        return false;
    }

    /** This function will remove all the buttons in the list and reset the id's as well */
    public void removeAllAbilityButtons() {
        gridButtons.clear();
        int id = 0;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 5; x++) {
                possibleIds[id] = id;
                gridButtons.add(new BuildingHUDGrid(id, 10 + x * 42, (int) abilityHUD.getY() + 12 + y * 42));
                id++;
            }
        }
    }

    /** This class is for all the ability buttons */
    class BuildingHUDGrid {
        /** If the spot is free, or if there is a button on the spot */
        private boolean free = true;
        /** The position of the button */
        private int x, y;
        /** The id of the button */
        private int id;
        /** The button attached to this slot */
        private Button button;

        public BuildingHUDGrid(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        public int getId() {
            return id;
        }

        public boolean isFree() {
            return free;
        }

        public void setButton(Button button) {
            if (button != null) {
                free = false;
                this.button = button;
                this.button.setPosition(x, y);
                this.button.setEnabled(true);
            } else {
                System.out.println("button is null");
            }
        }

        public void releaseButton() {
            free = true;
            this.button = null;
        }

        public void update() {
            button.update();
        }

        public void draw() {
            button.draw();
        }
    }
}
