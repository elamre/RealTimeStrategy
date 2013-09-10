package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.Assets;
import com.rts.game.abilities.BuildingSpace;
import com.rts.networking.packets.game.EntityCreationPacket;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/7/13
 * Time: 8:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestBuilding extends SelectableUnit {
    public TestBuilding(int x, int y) {
        super(x, y, EntityList.BUILDING_TEST);

        BuildingSpace builds = new BuildingSpace(this);

        boolean[][] space = {

                {true, true, true, true},
                {true, false, false, true},
                {false, false, true, true},
                {true, true, true, false}
        };

        builds.loadSpace(space);

        if (builds.isCreatable()) {
            builds.create();
        }


        abilities.add(builds);
    }

    public TestBuilding(EntityCreationPacket packet) {
        super(packet, EntityList.BUILDING_TEST, Assets.getAssets().getTextureRegion("Buildings/building_house"));


    }

    @Override
    public void onCreate_1() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void implementUpdate_3(float deltaT) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void implementDraw_3(SpriteBatch spriteBatch) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
