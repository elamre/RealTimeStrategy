package com.rts.game.abilities;

import com.badlogic.gdx.Input;
import com.rts.game.entities.Unit;
import com.rts.game.pathfinding.Node;
import com.rts.networking.packets.game.MoveEntityPacket;

import java.util.ArrayList;

/**
 * IDEAS:
 * <p/>
 * If the jps pathfinding produces only straight lines at angles
 * with multples of 45 degrees, therefore we can set our current block to be
 * ONLY the next block in that straight line. We consider this next block
 * to be the current block until the next one is selected.
 * <p/>
 * Nodes will store the entities currently taking them up as their current spot
 * <p/>
 * Paths may intersect using the following rules.
 * <p/>
 * If a unit in the path of another unit has its current square in the way,
 * but not having the current square being the final square,
 * The entity trying to move there will pause for a second and try to resume.
 * <p/>
 * If one unit's path is interrupted by another unit getting in the way of its
 * path and being at its final destination, recalculate the path
 * when the next square is being blocked by the 2nd's final
 * <p/>
 * If a unit already exists with the same final destination,
 * choose the nearest empty block to that as the final destination
 * <p/>
 * If multple units are selected, set each one's final destination in
 * A pattern around destination, such as a circle or rectangle
 */
public class Walk extends TargetedAbility {

    //Path of nodes to take
    public ArrayList<Node> path;
    //Position of current node in the arraylist
    public int nodePos;
    //Final destination. When this is reached, pathfinding stops for this unit
    public Node finalDest;
    //The next node to go towards
    public Node nextNode;
    //The square currently being attempted to be occupied
    public Node currentSquare;

    //TODO: Make walk follow friendly units

    public Walk(Unit owner) {
        super(owner);
        key = Input.Keys.Z;
    }

    @Override
    public void update_1(float deltaT) {

        // owner.x -= deltaX * deltaT * speed;
        // owner.y -= deltaY * deltaT * speed;


    }

    public void walkTo(int x, int y) {

    }

    public void interpretPacket(MoveEntityPacket packet) {

    }

    /*



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







        if (selected) {
            selectionSprite.draw(spriteBatch, 0.8f);
        }
        if (debug) {

            Camera.batch.end();

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

            Camera.batch.begin();


        }


     */


}
