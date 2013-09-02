package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rts.game.Assets;
import com.rts.networking.packets.game.EntityCreationPacket;

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

    public boolean isSelected() {
        return selected;
    }

    private boolean selected;

    protected SelectableUnit(int x, int y, int entityType) {
        super(x, y, entityType);
    }

    public SelectableUnit(int id, int x, int y, int entityType, TextureRegion sprite) {
        super(id, x, y, entityType, sprite);
    }

    protected SelectableUnit(EntityCreationPacket packet, int entityType, TextureRegion region) {
        super(packet, entityType, region);
        System.out.println("new unit has been made?");
    }

    @Override
    public void onCreate() {
        selectionSprite = Assets.getAssets().getSprite("Special/selection");
        selectionSprite.setColor(1f, 0f, 0f, 1f);
        selectionSprite.setPosition(x, y);
        onCreate_1();
    }

    public abstract void onCreate_1();

    public void implementUpdate_2(float deltaT) {
        implementUpdate_3(deltaT);
    }

    public abstract void implementUpdate_3(float deltaT);

    @Override
    public void implementDraw_2(SpriteBatch spriteBatch) {
        implementDraw_3(spriteBatch);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public abstract void implementDraw_3(SpriteBatch spriteBatch);
}
