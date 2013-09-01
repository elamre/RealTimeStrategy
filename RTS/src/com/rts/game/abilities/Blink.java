package com.rts.game.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.rts.game.entities.Entity;
import com.rts.game.entities.SelectableUnit;
import com.rts.game.gameplay.Camera;
import com.rts.game.gameplay.Cursor;
import com.rts.game.gameplay.World;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 9/1/13
 * Time: 12:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class Blink extends Ability {

    int range = 70;
    boolean requestClick = false;

    public Blink(Entity owner) {
        super(owner);
    }

    @Override
    public void logic() {

        if (Cursor.abilityRequesting != this) {
            requestClick = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.B) && ((SelectableUnit) owner).isSelected()) {
            requestClick = true;
            Cursor.abilityRequesting = this;
            Cursor.abilityRequested = true;
            System.out.println("Waiting for blink location");
        }

        if (requestClick && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            requestClick = false;
            Cursor.abilityRequesting = null;
            Cursor.abilityRequested = false;

            float x = owner.getX();
            float y = owner.getY();

            int x2 = (int) Camera.getRealWorldX();
            int y2 = (int) Camera.getRealWorldY();

            if (getDistance(x, y, x2, y2) <= range && World.jps.grid.walkable((int) x2, (int) y2)) {
                owner.setX(x2);
                owner.setY(y2);
                System.out.println("Blink in range, going to spot normally");
            } else {

                float tempAngle = (float) Math.atan2(x2 - x, y2 - y);
                tempAngle = -(float) Math.toDegrees(tempAngle);

                int dx = (int) (x - (range * Math.cos(Math.toRadians(tempAngle - 90))));
                int dy = (int) (y - (range * Math.sin(Math.toRadians(tempAngle - 90))));

                System.out.println(tempAngle);
                System.out.println(dx + ", " + dy);

                if (World.jps.grid.walkable((int) dx, (int) dy)) {
                    owner.setX(dx);
                    owner.setY(dy);
                    System.out.println("Blinked to location with a distance of range");
                } else {
                    System.out.println("Invalid location for blink");
                }

            }

        }


    }

    private float getDistance(float x, float y, float x2, float y2) {
        return (float) Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
    }

}
