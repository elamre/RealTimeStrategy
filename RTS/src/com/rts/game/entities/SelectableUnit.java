package com.rts.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rts.game.Assets;
import com.rts.game.gameplay.Camera;
import com.rts.game.gameplay.World;
import com.rts.game.pathfinding.Node;
import com.rts.networking.packets.game.EntityCreationPacket;
import com.rts.networking.packets.game.MoveEntityPacket;

import java.util.ArrayList;

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
    private boolean selected, atFinalLocation = true;
    private int nextDestinationX, nextDestinationY, finalDestinationX, finalDestinationY;
    private float speed = 10;
    private MoveEntityPacket moveEntityPacket;
    ArrayList<Node> path;
    int currentNode = 0;

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

        if (!atFinalLocation || netEntity) {

            this.x -= deltaX * deltaT * speed;
            this.y -= deltaY * deltaT * speed;

            if (getDistance(nextDestinationX, nextDestinationY) <= speed * deltaT) {


                if (getDistance(nextDestinationX, nextDestinationY) <= speed * deltaT) {
                    atFinalLocation = true;
                    path = null;
                    deltaX = 0;
                    deltaY = 0;
                } else {
                    currentNode++;

                    nextDestinationX = path.get(currentNode).getX();
                    nextDestinationY = path.get(currentNode).getY();

                    faceAt(nextDestinationX, nextDestinationY);
                    deltaX = (float) (Math.cos(Math.toRadians(getAngle() - 90)));
                    deltaY = (float) (Math.sin(Math.toRadians(getAngle() - 90)));
                }

                moveEntityPacket = new MoveEntityPacket(this.getId(), (int) getX(), (int) getY(), (int) nextDestinationX, (int) nextDestinationY, 0);

            }
            selectionSprite.setPosition(getX(), getY());
        } else {
            nextDestinationX = (int) this.x;
            nextDestinationY = (int) this.y;
            deltaX = 0;
            deltaY = 0;
        }
        implementUpdate_3(deltaT);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDestination(float x, float y) {
        atFinalLocation = false;
        this.finalDestinationX = (int) x;
        this.finalDestinationY = (int) y;

        path = World.jps.search((int) this.getX(), (int) this.getY(), (int) finalDestinationX, (int) finalDestinationY);

        if (path.size() >= 2) {
            this.nextDestinationX = path.get(1).getX();
            this.nextDestinationY = path.get(1).getY();
            //Set to 1 because index 0 is the start area, and to move to the start area would be useless or would make unnatural movement patterns.

            currentNode = 1;

            for (Node n : path) {
                System.out.println("Node:" + n.getX() + ", " + n.getY());
            }

        } else {
            this.nextDestinationX = (int) getX();
            this.nextDestinationY = (int) getY();

            System.out.println("No path");
        }

        moveEntityPacket = new MoveEntityPacket(this.getId(), (int) getX(), (int) getY(), (int) nextDestinationX, (int) nextDestinationY, 0);
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
            shapeRenderer.line(x, y, nextDestinationX, nextDestinationY);
            shapeRenderer.end();
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void moveEntity(MoveEntityPacket moveEntityPacket) {
        this.x = moveEntityPacket.getX();
        this.y = moveEntityPacket.getY();
        atFinalLocation = false;
        this.nextDestinationX = moveEntityPacket.getTargetX();
        this.nextDestinationY = moveEntityPacket.getTargetY();
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
