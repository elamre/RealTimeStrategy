package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.Assets;
import com.rts.networking.packets.game.EntityCreationPacket;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/23/13
 * Time: 5:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestEntity extends SelectableUnit {
    public TestEntity(int x, int y) {
        super(x, y, EntityList.UNIT_TEST_1);
    }

    public TestEntity(EntityCreationPacket packet) {
        super(packet, EntityList.UNIT_TEST_1, Assets.getAssets().getTextureRegion("Units/worker_wood"), 4, 0.1f);
    }

    @Override
    public void onCreate_1() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void implementUpdate_4(float deltaT) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void implementDraw_3(SpriteBatch spriteBatch) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
