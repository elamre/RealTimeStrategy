package com.rts.game.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.rts.game.entities.EntityList;
import com.rts.game.entities.EntityManager;
import com.rts.game.entities.TestBuilding;
import com.rts.game.entities.Unit;
import com.rts.game.gameplay.Camera;
import com.rts.game.hud.BuildingGhost;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 9/9/13
 * Time: 7:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Build extends TargetedAbility {

    BuildingGhost ghost;

    boolean[][] spacearray = new boolean[][]{

            {false, true, true, false},
            {true, true, true, true},
            {true, true, true, true},
            {false, true, true, false}
    };

    public Build(Unit owner) {
        super(owner);
        key = Input.Keys.M;
        ghost = new BuildingGhost(0, 0);
        ghost.changeEntity(EntityList.getEntityType(new TestBuilding()));
    }

    @Override
    public void update_1(float delta) {

        if (requestClick || waitForNextClick) {
            ghost.update(delta);
            ghost.setWidth(spacearray.length);
            ghost.setHeight(spacearray[0].length);
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && requestClick) {
                removeCursorUse();

                System.out.println("Attempting building place");

                TestBuilding test = new TestBuilding((int) ghost.getX(), (int) ghost.getY());
                BuildingSpace bspace = new BuildingSpace(test);
                bspace.loadSpace(spacearray);
                if (bspace.isCreatable()) {
                    bspace.create();
                    test.abilities.add(bspace);
                    EntityManager.addEntity(test);
                    //TODO: Make this be added to the entity list somehow
                }
            }

        }


    }

    public void draw() {
        if (waitForNextClick || requestClick) {
            //System.out.println("Drawing...");
            ghost.draw(Camera.batch);
        }
    }

}
