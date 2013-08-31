package com.rts.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
public abstract class SelectableUnit extends MovingUnit {
    private boolean netEntity = false;
    private Sprite selectionSprite;
    private boolean selected, atFinalLocation = false;
    private int nextDestinationX, nextDestinationY, finalDestinationX, finalDestinationY;
    private float speed = 3;
    private MoveEntityPacket moveEntityPacket;
    ArrayList<Node> path = new ArrayList<Node>(64);
    int currentNode = 0;

    protected SelectableUnit(int x, int y, int entityType) {
        super(x, y, entityType);
        nextDestinationX = x;
        nextDestinationY = y;
        finalDestinationX = x;
        finalDestinationY = y;

    }

    public SelectableUnit(int id, int x, int y, int entityType, Sprite sprite) {
        super(id, x, y, entityType, sprite);
        nextDestinationX = x;
        nextDestinationY = y;
        finalDestinationX = x;
        finalDestinationY = y;
    }

    protected SelectableUnit(EntityCreationPacket packet, int entityType, Sprite sprite) {
        super(packet, entityType, sprite);
    }

    protected SelectableUnit(EntityCreationPacket packet, int entityType, TextureRegion region, int frames, float speed) {
        super(packet, entityType, region, frames, speed);
        finalDestinationX = (int) x;
        finalDestinationY = (int) y;
        nextDestinationX = (int) x;
        nextDestinationY = (int) y;
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

    public void implementUpdate_3(float deltaT) {

        if (!atFinalLocation || netEntity) {

            faceAt(nextDestinationX, nextDestinationY);
            setDirection();

            if (getDistance(nextDestinationX, nextDestinationY) <= speed * deltaT) {


                if (getDistance(finalDestinationX, finalDestinationY) <= speed * deltaT) {

                    atFinalLocation = true;
                    path = null;
                    deltaX = 0;
                    deltaY = 0;

                } else {
                    if (currentNode < path.size() - 1)
                        currentNode++;

                    nextDestinationX = path.get(currentNode).getX();
                    nextDestinationY = path.get(currentNode).getY();

                    faceAt(nextDestinationX, nextDestinationY);
                    setDirection();

                    moveEntityPacket = new MoveEntityPacket(this.getId(), (int) getX(), (int) getY(), (int) nextDestinationX, (int) nextDestinationY, 0);

                }
            }

            selectionSprite.setPosition(getX(), getY());
        } else {
            finalDestinationY = (int) this.y;
            finalDestinationX = (int) this.x;
            nextDestinationX = (int) this.x;
            nextDestinationY = (int) this.y;
            deltaX = 0;
            deltaY = 0;
        }
        implementUpdate_4(deltaT);
        //To change body of implemented methods use File | Settings | File Templates.
    }
    public abstract void implementUpdate_4(float deltaT);
    private void setDirection() {
        deltaX = (float) (Math.cos(Math.toRadians(getAngle() - 90)));
        deltaY = (float) (Math.sin(Math.toRadians(getAngle() - 90)));
    }

    public void setDestination(int x, int y) {

        System.out.println("Goal: " + x + ", " + y);
        System.out.println("Current: " + this.x + ", " + this.y);

        path = World.getPath((int) getX(), (int) getY(), x, y);
        //path = World.getPath(0, 0, 100, 100);
        System.out.println("Finding path...");


        if (path != null && path.size() >= 2) {

            System.out.println("Node size: " + path.size());
            for (Node n : path) {
                System.out.println("Pathing Node: " + n.getX() + ", " + n.getY());
            }

            atFinalLocation = false;
            this.finalDestinationX = x;
            this.finalDestinationY = y;

            this.nextDestinationX = path.get(1).getX();
            this.nextDestinationY = path.get(1).getY();
            //Set to 1 because index 0 is the start area, and to move to the start area would be useless or would make unnatural movement patterns.

            currentNode = 1;

            moveEntityPacket = new MoveEntityPacket(this.getId(), (int) getX(), (int) getY(), nextDestinationX, nextDestinationY, 0);


        } else {
            this.nextDestinationX = (int) getX();
            this.nextDestinationY = (int) getY();
            this.finalDestinationX = (int) getX();
            this.finalDestinationY = (int) getY();

            System.out.println("No path");
        }
    }

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

            shapeRenderer.setColor(1f, 0, 0, 0.5f);

            if (path != null && path.size() > 1) {
                for (int i = 0; i < path.size() - 1; i++) {
                    shapeRenderer.line(path.get(i).getX(), path.get(i).getY(), path.get(i + 1).getX(), path.get(i + 1).getY());
                }
            }

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
