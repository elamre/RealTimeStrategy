package com.rts.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rts.game.Assets;
import com.rts.game.Camera;
import com.rts.networking.packets.system.EntityCreationPacket;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/23/13
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class SelectableUnit extends Unit {
    private Sprite selectionSprite;
    private boolean selected, atLocation = true;
    private float destinationX, destinationY;
    private float speed = 100;

    protected SelectableUnit(int x, int y) {
        super(x, y);
    }

    public SelectableUnit(int id, int x, int y, Sprite sprite) {
        super(id, x, y, sprite);
    }

    protected SelectableUnit(EntityCreationPacket packet, Sprite sprite) {
        super(packet, sprite);
    }

    @Override
    public void onCreate() {
        selectionSprite = Assets.getAssets().getSprite("Units/selected");
        onCreate_1();
    }

    public abstract void onCreate_1();

    @Override
    public void implementUpdate_2(float deltaT) {
        if (!atLocation) {
            if (this.x != destinationX && this.y != destinationY) {
                float deltaY = (float) (Math.sin(Math.toRadians(getAngle() - 90)) * speed * deltaT);
                float deltaX = (float) (Math.cos(Math.toRadians(getAngle() - 90)) * speed * deltaT);
                this.x -= deltaX;
                this.y -= deltaY;
            }
            if (getDistance(destinationX, destinationY) <= speed * deltaT) {
                destinationX = this.x;
                destinationY = this.y;
            }
        }
        selectionSprite.setPosition(getX(), getY());
        implementUpdate_3(deltaT);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDestination(float x, float y) {
        atLocation = false;
        this.destinationX = x;
        this.destinationY = y;
        faceAt(x, y);
    }

    public abstract void implementUpdate_3(float deltaT);

    @Override
    public void implementDraw_2(SpriteBatch spriteBatch) {
        implementDraw_3(spriteBatch);
        if (selected) {
            selectionSprite.draw(spriteBatch);
        }
        if (debug) {
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setProjectionMatrix(Camera.getOrthographicCamera().combined);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.line(x, y, destinationX, destinationY);
            shapeRenderer.end();
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public abstract void implementDraw_3(SpriteBatch spriteBatch);
}
