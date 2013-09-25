package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rts.game.Assets;
import com.rts.game.abilities.Deselect;
import com.rts.game.hud.AbilityButton;
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
    private TextureRegion selectionRegion;
    private boolean selected;

    /** USE THIS ONLY FOR REGISTERING THE ENTITY! SHOULD NOT BE USED OTHERWISE! */
    protected SelectableUnit() {
        super();
    }

    protected SelectableUnit(int x, int y) {
        super(x, y);
    }

    protected SelectableUnit(EntityCreation entityCreation, TextureRegion region) {
        super(entityCreation, region);
        abilities.add(new Deselect(this));
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void onCreate() {
        selectionRegion = Assets.getAssets().getTextureRegion("selection");
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
        if (selected) {
            spriteBatch.draw(selectionRegion, x + (1 - width), y + (1 - height), width / 2, height / 2, width, height, 1, 1, 0);
            spriteBatch.setColor(.7f, .7f, .7f, .7f);
            spriteBatch.draw(selectionRegion, x - (1 - width) * 2, y - (1 - height) * 2, width / 2, height / 2, width, height, 1, 1, 0);
            spriteBatch.setColor(1, 1, 1, 1);
        }
    }

    public abstract void implementDraw_3(SpriteBatch spriteBatch);
}
