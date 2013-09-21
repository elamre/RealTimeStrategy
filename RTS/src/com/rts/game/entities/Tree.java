package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.Assets;
import com.rts.game.gameplay.World;
import com.rts.networking.mutual.packets.EntityCreation;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 9/21/13
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class Tree extends Resource {


    public Tree(int x, int y) {
        super(x, y);
    }

    public Tree(EntityCreation entityCreation) {
        super(entityCreation);
        textureRegion = Assets.getAssets().getTextureRegion("tree");
    }

    public Tree() {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Override
    public void onCreate() {
        World.nodeAt(getX(), getY()).standing = this;
    }

    @Override
    public void implementUpdate_1(float deltaT) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void implementDraw_1(SpriteBatch spriteBatch) {
    }
}
