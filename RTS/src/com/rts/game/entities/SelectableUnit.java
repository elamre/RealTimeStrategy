package com.rts.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rts.game.Assets;
import com.rts.game.gameplay.Camera;
import com.rts.networking.packets.game.MoveEntityPacket;
import com.rts.networking.packets.system.EntityCreationPacket;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/23/13
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class SelectableUnit extends Unit {
    float deltaX, deltaY;
    private boolean netEntity = false;
    private Sprite selectionSprite;
    private boolean selected, atLocation = true;
    private float destinationX, destinationY;
    private float speed = 100;
    private MoveEntityPacket moveEntityPacket;

    protected SelectableUnit(int x, int y, int entityType) {
        super(x, y, entityType);
    }

    public SelectableUnit(int id, int x, int y, int entityType, Sprite sprite) {
        super(id, x, y, entityType, sprite);
    }

    protected SelectableUnit(EntityCreationPacket packet, int entityType, Sprite sprite) {
        super(packet, entityType, sprite);
    }

    @Override
    public void onCreate() {
        selectionSprite = Assets.getAssets().getSprite("Units/selected");
        selectionSprite.setColor(1f, 0f, 0f, 1f);
        onCreate_1();
    }

    public abstract void onCreate_1();

    @Override
    public void implementUpdate_2(float deltaT) {
        if (!atLocation || netEntity) {
            this.x -= deltaX * deltaT * speed;
            this.y -= deltaY * deltaT * speed;
            if (getDistance(destinationX, destinationY) <= speed * deltaT) {
                atLocation = true;
            }
            selectionSprite.setPosition(getX(), getY());
        } else {
            destinationX = this.x;
            destinationY = this.y;
            deltaX = 0;
            deltaY = 0;
        }
        implementUpdate_3(deltaT);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDestination(float x, float y) {
        atLocation = false;
        this.destinationX = x;
        this.destinationY = y;
        faceAt(x, y);
        deltaX = (float) (Math.cos(Math.toRadians(getAngle() - 90)));
        deltaY = (float) (Math.sin(Math.toRadians(getAngle() - 90)));
        moveEntityPacket = new MoveEntityPacket(this.getId(), (int) getX(), (int) getY(), (int) destinationX, (int) destinationY, 0);
    }

    public abstract void implementUpdate_3(float deltaT);

    @Override
    public void implementDraw_2(SpriteBatch spriteBatch) {
        implementDraw_3(spriteBatch);
        if (selected) {
            selectionSprite.draw(spriteBatch, 0.8f);
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

    public void moveEntity(MoveEntityPacket moveEntityPacket) {
        this.x = moveEntityPacket.getX();
        this.y = moveEntityPacket.getY();
        atLocation = false;
        this.destinationX = moveEntityPacket.getTargetX();
        this.destinationY = moveEntityPacket.getTargetY();
        faceAt(moveEntityPacket.getTargetX(), moveEntityPacket.getTargetY());
        deltaX = (float) (Math.cos(Math.toRadians(getAngle() - 90)));
        deltaY = (float) (Math.sin(Math.toRadians(getAngle() - 90)));
    }

    @Override
    public MoveEntityPacket getMovePacket() {
        MoveEntityPacket tempPacket = moveEntityPacket;
        moveEntityPacket = null;
        return tempPacket;
    }

    public abstract void implementDraw_3(SpriteBatch spriteBatch);
}
