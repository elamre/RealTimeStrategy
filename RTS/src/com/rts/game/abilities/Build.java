package com.rts.game.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.rts.game.entities.EntityList;
import com.rts.game.entities.EntityManager;
import com.rts.game.entities.TestBuilding;
import com.rts.game.entities.Unit;
import com.rts.game.gameplay.Camera;
import com.rts.game.hud.BuildingGhost;
import com.rts.networking.mutual.packets.EntityCreation;

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
            {true, true},
            {true, true}
    };

    public Build(Unit owner) {
        super(owner);
        //System.out.println("Building with owner id: " + owner.getId());
        key = Input.Keys.M;
        ghost = new BuildingGhost();
        ghost.changeEntity(EntityList.getEntityType(new TestBuilding()));
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void update_1(float delta) {
        ghost.update(delta);
        ghost.setScale(spacearray.length, spacearray[0].length);
        TestBuilding test = new TestBuilding(ghost.position);
        BuildingSpace bspace = new BuildingSpace(test);
        bspace.loadSpace(spacearray);
        if (bspace.isCreatable()) {
            ghost.setPossible();
        } else {
            ghost.setUnable();
        }
        if (requestClick || waitForNextClick) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && requestClick) {
                removeCursorUse();
                if (bspace.isCreatable()) {
                    bspace.create();
                    test.abilities.add(bspace);
                    EntityManager.addEntity(test);
                } else {

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
