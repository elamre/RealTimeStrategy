package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rts.game.Assets;
import com.rts.networking.mutual.packets.EntityCreation;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/23/13
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class SelectableUnit extends Unit {
    private boolean netEntity = false;
    private Sprite selectionSprite;
    private boolean selected;

    /**
     * USE THIS ONLY FOR REGISTERING THE ENTITY! SHOULD NOT BE USED OTHERWISE!
     */
    protected SelectableUnit() {
        super();
    }

    protected SelectableUnit(int x, int y) {
        super(x, y);
    }

    protected SelectableUnit(EntityCreation entityCreation, TextureRegion region) {
        super(entityCreation, region);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void onCreate() {
        selectionSprite = Assets.getAssets().getSprite("selection");
        selectionSprite.setPosition(x, y);
        selectionSprite.setScale(0.8f, 0.8f);
        onCreate_1();
    }

    public abstract void onCreate_1();

    public void implementUpdate_2(float deltaT) {
        implementUpdate_3(deltaT);
        selectionSprite.setPosition(x, y);
    }

    public abstract void implementUpdate_3(float deltaT);

    @Override
    public void implementDraw_2(SpriteBatch spriteBatch) {
        //selectionSprite.draw(spriteBatch);
        implementDraw_3(spriteBatch);
    }

    public abstract void implementDraw_3(SpriteBatch spriteBatch);
}
