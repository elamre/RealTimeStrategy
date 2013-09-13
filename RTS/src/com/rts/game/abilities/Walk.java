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
    /* The angle the entity is walking in */
    float angle = 0f;

    boolean persist;

    public float dx;
    public float dy;

    //TODO: Make walk follow friendly units
    //TODO: Change JPS so that it will return the path that leads to the closest valid point if target is invalid
    //TODO: Make current square considered to be filled to other units pathfinding

    @Override
    public void draw() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Walk(Unit owner) {
        super(owner);
        key = Input.Keys.Z;
        range = 9999999;
    }

    private void updatePath() {

    }

    @Override
    public void update_1(float deltaT) {

        if (requestClick && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            path = World.getPath((int) owner.getX(), (int) owner.getY(), (int) Camera.getRealWorldX(), (int) Camera.getRealWorldY());
            removeCursorUse();

            System.out.println("Owner: " + owner.getX() + ", " + owner.getY());

            for (Node n : path) {
                n.debug();
            }

            if (path.size() > 1) {

                nodePos = 1;
                nextNode = path.get(1);
                finalDest = path.get(path.size() - 1);

                updateDeltaSpeed();

            }

            System.out.println("Set path to " + finalDest + ".");

        }

        if (finalDest != null) {

            //If the distance from the owner to the last node plus their current movement speed is greater than the
            //Distance from the last node to the next node
            if (getDistance(owner.getX(), owner.getY(), path.get(nodePos - 1).getCenterX(), path.get(nodePos - 1).getCenterY())
                    + Math.abs(dx * deltaT * ((MovingUnit) owner).speed) + Math.abs(dy * deltaT * ((MovingUnit) owner).speed)
                    >= getDistance(nextNode.getCenterX(), nextNode.getCenterY(), path.get(nodePos - 1).getCenterX(), path.get(nodePos - 1).getCenterY())) {


                if (nodePos < path.size() - 1) {
                    nodePos++;

                    owner.setX(nextNode.getCenterX());
                    owner.setY(nextNode.getCenterY());

                    nextNode = path.get(nodePos);
                    updateDeltaSpeed();

                } else {
                    dx = 0;
                    dy = 0;

                    finalDest = null;
                    path = null;

                    owner.setX(nextNode.getCenterX());
                    owner.setY(nextNode.getCenterY());


                }
            }

            setCurrentSquare();

            owner.setX(owner.getX() + dx * deltaT);
            owner.setY(owner.getY() + dy * deltaT);
        }
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
