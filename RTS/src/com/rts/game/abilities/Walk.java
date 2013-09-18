package com.rts.game.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.rts.game.entities.MovingUnit;
import com.rts.game.entities.Unit;
import com.rts.game.gameplay.Camera;
import com.rts.game.gameplay.World;
import com.rts.game.pathfinding.Node;
import com.rts.networking.mutual.packets.EntityPosChange;

import java.util.ArrayList;

/**
 * IDEAS:
 * If the jps pathfinding produces only straight lines at angles
 * with multples of 45 degrees, therefore we can set our current block to be
 * ONLY the next block in that straight line. We consider this next block
 * to be the current block until the next one is selected.
 * <p/>
 * Nodes will store the entities currently taking them up as their current spot
 * Paths may intersect using the following rules.
 * <p/>
 * If a unit in the path of another unit has its current square in the way,
 * but not having the current square being the final square,
 * or the square is somehow filled,
 * The entity trying to move there will pause for a second and try to resume.
 * If the blocking unit has its final set at its current,
 * or if that block is now filled, find a new path.
 * <p/>
 * If multple units are selected, set each one's final destination in
 * A pattern around destination, such as a circle or rectangle
 * <p/>
 * The current square will be considered to be filled
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
    public float dx;
    public float dy;
    /* The angle the entity is walking in */
    float angle = 0f;
    boolean persist;

    //TODO: Make walk follow friendly units
    //TODO: Change JPS so that it will return the path that leads to the closest valid point if target is invalid
    //TODO: Make current square considered to be filled to other units pathfinding

    public Walk(Unit owner) {
        super(owner);
        key = Input.Keys.Z;
        range = 9999999;
    }

    @Override
    public void draw() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updatePath(int x, int y) {

        path = World.getPath((int) owner.getX(), (int) owner.getY(), x, y);

        if (path.size() > 1) {

            nodePos = 1;
            nextNode = path.get(1);
            finalDest = path.get(path.size() - 1);

            updateDeltaSpeed();

        }

        System.out.println("Set path to " + finalDest + ".");
    }

    @Override
    public void update_1(float deltaT) {

        if (requestClick && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            updatePath((int) Camera.getRealWorldX(), (int) Camera.getRealWorldY());

            removeCursorUse();

        }

        if (finalDest != null) {

            //If the distance from the owner to the last node plus their current movement speed is greater than the
            //Distance from the last node to the next node
            if (getDistance(owner.getX(), owner.getY(), path.get(nodePos - 1).getCenterX(), path.get(nodePos - 1).getCenterY())
                    + Math.abs(dx * deltaT * ((MovingUnit) owner).speed) + Math.abs(dy * deltaT * ((MovingUnit) owner).speed)
                    >= getDistance(nextNode.getCenterX(), nextNode.getCenterY(), path.get(nodePos - 1).getCenterX(), path.get(nodePos - 1).getCenterY())) {

                owner.setX(nextNode.getCenterX());
                owner.setY(nextNode.getCenterY());

                if (nodePos < path.size() - 1) {
                    nodePos++;


                    nextNode = path.get(nodePos);
                    updateDeltaSpeed();

                } else {
                    dx = 0;
                    dy = 0;

                    finalDest = null;
                    path = null;

                }
            }

            if (finalDest != null) {


                Node nextTick = World.nodeAt(owner.getX() + dx * deltaT, owner.getY() + dy * deltaT);
                boolean isValid = false;
                boolean isFindNewPath = false;

                System.out.println("-------------------------------");

                System.out.println("Next tick entity: " + nextTick.standing);
                System.out.println("This: " + currentSquare.standing);
                System.out.println("This node: " + currentSquare);
                System.out.println("Next Tick Node: " + nextTick);

                System.out.println("-------------------------------");


                System.out.println(finalDest);

                //If isValid is true, move.
                //If isValid is false, but isFindNewPath is also false, wait until the other unit moves.
                //If isFindNewPath is true, the current path is not working. Find a new one.

                if (currentSquare != nextTick) {

                    if (!nextTick.isPass()) {
                        //If the next node is impassible, find a new path and set to invalid.
                        isValid = false;
                        isFindNewPath = true;
                    } else if (nextTick.standing == null) {
                        //If the next node has no standing
                        isValid = true;
                    } else {
                        if (nextTick.standing instanceof MovingUnit) {
                            //If the next node has a movingUnit
                            if (((MovingUnit) nextTick.standing).walker.dx != 0 &&
                                    ((MovingUnit) nextTick.standing).walker.dy != 0) {
                                //And the unit has a non-zero movement pattern
                                isValid = true;
                            } else {
                                //Else if the unit has a zero movement pattern, find a new path
                                isFindNewPath = true;
                                isValid = false;
                            }
                        }
                    }
                } else {
                    isValid = true;
                }


                if (isValid) {
                    owner.setX(owner.getX() + dx * deltaT);
                    owner.setY(owner.getY() + dy * deltaT);

                } else if (isFindNewPath) {
                    updatePath(finalDest.getX(), finalDest.getY());
                }


            }
        }


        setCurrentSquare();


    }

    public void setCurrentSquare() {
        if (currentSquare != null)
            currentSquare.standing = null;
        currentSquare = World.nodeAt(owner.getX(), owner.getY());
        currentSquare.standing = owner;
    }

    public void updateDeltaSpeed() {
        angle = -(float) Math.atan2(nextNode.getCenterX() - owner.getX(), nextNode.getCenterY() - owner.getY());
        dx = (float) -(((MovingUnit) owner).speed * Math.cos(angle - Math.PI / 2));
        dy = (float) -(((MovingUnit) owner).speed * Math.sin(angle - Math.PI / 2));
    }

    public float getNextAngle() {
        return angle;
    }

    public void interpretPacket(EntityPosChange entityPosChange) {

        path = new ArrayList<Node>(1);

        owner.setX(entityPosChange.x);
        owner.setY(entityPosChange.y);

        path.add(World.nodeAt(entityPosChange.tarX, entityPosChange.tarY));

        nodePos = 0;
        nextNode = path.get(0);
        finalDest = path.get(0);

        updateDeltaSpeed();
    }


}
