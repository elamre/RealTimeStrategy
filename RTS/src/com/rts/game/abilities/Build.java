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

    public Build(Unit owner) {
        super(owner);
        key = Input.Keys.M;
        ghost = new BuildingGhost(0, 0);
        ghost.changeEntity(EntityList.getEntityType(TestBuilding.class));
    }

    @Override
    public void update_1(float delta) {

        if (requestClick) {
            ghost.update(delta);
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                removeCursorUse();

                TestBuilding test = new TestBuilding(0, 0);
                BuildingSpace bspace = new BuildingSpace(test);
                bspace.loadSpace(new boolean[][]{

                        {true, true, true, true},
                        {true, false, false, true},
                        {false, false, true, true},
                        {true, true, true, false}
                });
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
        if (requestClick) {
            System.out.println("Drawing...");
            ghost.draw(Camera.batch);
        }
    }

}
