package com.rts.game.abilities;

import com.badlogic.gdx.Gdx;
import com.rts.game.entities.SelectableUnit;
import com.rts.game.entities.Unit;
import com.rts.game.gameplay.Cursor;

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

    public TargetedAbility(Unit owner) {
        super(owner);
    }

    public void requestCursorUse() {
        requestClick = true;
        Cursor.abilityRequesting = this;
        Cursor.abilityRequested = true;
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

        if (Gdx.input.isKeyPressed(key) && ((SelectableUnit) owner).isSelected()) {
            requestCursorUse();
            System.out.println("Ability target requesting");
        }

        update_1(delta);
    }

    public abstract void update_1(float delta);

    protected float getDistance(float x, float y, float x2, float y2) {
        return (float) Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
    }


}
