package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.Assets;
import com.rts.networking.mutual.packets.EntityCreation;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/7/13
 * Time: 8:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestBuilding extends SelectableUnit {
    public TestBuilding() {
        super();
    }

    public TestBuilding(int x, int y) {
        super(x, y);
    }

    public TestBuilding(EntityCreation entityCreation) {
        // super(entityCreation, Assets.getAssets().getTextureRegion("building_house"), 1, 0.1f);
        super(entityCreation, Assets.getAssets().getTextureRegion("house"));
        setWidth(2f);
        setHeight(2f);

        if (getId() != 0) {
            System.out.println("Building creating shadow");
            EntityManager.addShadow(new ShadowEntity(Assets.getAssets().getTextureRegion("house_shadow"), getId()));
        }

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
