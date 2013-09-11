package com.rts.game.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.rts.game.entities.SelectableUnit;
import com.rts.game.entities.Unit;
import com.rts.game.gameplay.Cursor;
import com.rts.game.gameplay.Player;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 9/1/13
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class TargetedAbility extends Ability {

    boolean requestClick = false;
    int range = 70;
    boolean waitForNextClick;
    boolean wasLastClicked;
    boolean saveSelection;


    public TargetedAbility(Unit owner) {
        super(owner);
        saveSelection = true;
    }

    public void requestCursorUse(boolean waitForNextClick) {
        requestClick = true;
        this.waitForNextClick = waitForNextClick;
        this.wasLastClicked = waitForNextClick;
        if (!waitForNextClick) {
            Cursor.abilityRequesting = this;
            Cursor.abilityRequested = true;
            if (saveSelection) {
                Player.preserveSelection = true;
            }
        }
        if (saveSelection) {
            Player.preserveSelection = true;
        }
    }

    public void removeCursorUse() {
        requestClick = false;
        Cursor.abilityRequesting = null;
        Cursor.abilityRequested = false;
    }

    public void logic(float delta) {
        if (Cursor.abilityRequesting != this) {
            requestClick = false;
        }

        if (!wasLastClicked && Gdx.input.isButtonPressed(Input.Buttons.LEFT) && waitForNextClick) {
            requestCursorUse(false);
        }

        if (wasLastClicked && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            wasLastClicked = false;
        }

        if (Gdx.input.isKeyPressed(key) && ((SelectableUnit) owner).isSelected()) {
            requestCursorUse(false);
            System.out.println("Ability target requesting");
        }

        update_1(delta);
    }

    public abstract void update_1(float delta);

    protected float getDistance(float x, float y, float x2, float y2) {
        return (float) Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
    }


}
