package com.rts.game.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.rts.game.entities.Unit;
import com.rts.game.gameplay.Camera;
import com.rts.game.gameplay.World;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 9/1/13
 * Time: 12:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class Blink extends TargetedAbility {

    public Blink(Unit owner) {
        super(owner);
        key = Input.Keys.B;
    }

    @Override
    public void update_1(float delta) {

        if (requestClick && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            removeCursorUse();

            float x = owner.getX();
            float y = owner.getY();

            int x2 = (int) Camera.getRealWorldX();
            int y2 = (int) Camera.getRealWorldY();

            float tempAngle = -(float) Math.atan2(x2 - x, y2 - y);

            if (getDistance(x, y, x2, y2) <= range && World.jps.grid.walkable(x2, y2)) {
                owner.setX(x2);
                owner.setY(y2);
                System.out.println("Blink in range, going to spot normally");
            } else {

                int dx = (int) (x - (range * Math.cos(tempAngle - Math.PI / 2)));
                int dy = (int) (y - (range * Math.sin(tempAngle - Math.PI / 2)));

                if (World.jps.grid.walkable(dx, dy)) {
                    owner.setX(dx);
                    owner.setY(dy);
                    System.out.println("Blinked to location with a distance of range");
                } else {
                    System.out.println("Invalid location for blink");
                }

            }

        }

    }

}
