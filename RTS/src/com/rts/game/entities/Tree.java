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
        super(entityCreation, Assets.getAssets().getTextureRegion("tree"));
        setWidth(2f);
        setHeight(2f);
    }

    public Tree() {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Override
    public void onCreate_1() {
        World.nodeAt(getX(), getY()).standing = this;
        World.nodeAt(getX() - 1, getY()).standing = this;
        World.nodeAt(getX(), getY() - 1).standing = this;
        World.nodeAt(getX() - 1, getY() - 1).standing = this;
        World.nodeAt(getX(), getY()).setPassable(false);
        World.nodeAt(getX() - 1, getY()).setPassable(false);
        World.nodeAt(getX(), getY() - 1).setPassable(false);
        World.nodeAt(getX() - 1, getY() - 1).setPassable(false);

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
