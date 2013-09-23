package com.rts.game.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.Assets;
import com.rts.game.entities.MovingUnit;
import com.rts.game.entities.Unit;
import com.rts.game.gameplay.Camera;
import com.rts.game.gameplay.World;
import com.rts.game.hud.AbilityButton;
import com.rts.game.pathfinding.Node;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/22/13
 * Time: 7:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Walk extends TargetedAbility {
    /** Path of nodes to take */
    public ArrayList<Node> path;
    /** Position of current node in the arraylist */
    public int currentNode;
    /** Final node. When this node is reached, pathfinding stops for this unit */
    public Node finalNode;
    /** The next node to go towards */
    public Node nextNode;
    /** The square currently being attempted to be occupied */
    public Node currentSquare;
    /** The horizontal speed the unit will be moving at */
    public float dx;
    /** The vertical speed the unit will be moving at */
    public float dy;
    /** The angle the entity is walking in */
    private float angle = 0f;

    /**
     * Constructor
     *
     * @param owner the owner of the ability
     */
    public Walk(Unit owner) {
        super(owner, Input.Keys.M, new AbilityButton(Assets.getAssets().getSprite("move_button"), -1));
    }

    /**
     * Implement this function for the actual action to be executed
     *
     * @param x the x position the ability is called on
     * @param y the y position the ability is called on
     */
    @Override
    public void action_1(float x, float y) {
        updatePath((int) x, (int) y);
    }

    /**
     * Update function for anything ability related
     *
     * @param deltaT the time that has passed since the previous update
     */
    @Override
    public void update_1(float deltaT) {

        if (finalNode != null) {
            //If the distance from the owner to the last node plus their current movement speed is greater than the
            //Distance from the last node to the next node
            if (currentNode > 0) {
                Node tempNode = path.get(currentNode - 1);
                if (getDistance(owner.getX(), owner.getY(), tempNode.getCenterX(), tempNode.getCenterY()) + (deltaT * ((MovingUnit) owner).speed)
                        >= getDistance(nextNode.getCenterX(), nextNode.getCenterY(), tempNode.getCenterX(), tempNode.getCenterY())) {
                    updateNodes();
                }
            } else {
                Node tempNode = path.get(currentNode);
                if (getDistance(owner.getX(), owner.getY(), tempNode.getCenterX(), tempNode.getCenterY()) <= 2 * deltaT * ((MovingUnit) owner).speed) {
                    updateNodes();
                }
            }
            if (finalNode != null) {
                Node nextTick = World.nodeAt(owner.getX() + dx * deltaT, owner.getY() + dy * deltaT);
                boolean isValid = false;
                boolean isFindNewPath = false;
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
                    logger.debug("-------------------------------");
                    logger.debug("Next tick entity: " + nextTick.standing);
                    logger.debug("This: " + currentSquare.standing);
                    logger.debug("This node: " + currentSquare);
                    logger.debug("Next Tick Node: " + nextTick);
                    logger.debug("-------------------------------");
                    logger.debug(finalNode);
                    updatePath(finalNode.getX(), finalNode.getY());
                }
            }
        }
        setCurrentSquare();
    }

    /**
     * General draw function
     *
     * @param spriteBatch the batch to draw everything on
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * This function will update the current path for the owner
     *
     * @param x the x of the target position
     * @param y the y of the target position
     */
    public void updatePath(int x, int y) {
        path = World.getPath((int) owner.getX(), (int) owner.getY(), x, y);
        if (path.size() > 0) {
            currentNode = 0;
            nextNode = path.get(0);
            finalNode = path.get(path.size() - 1);
            updateDeltaSpeed();
        }
        logger.debug("Setting path to: " + finalNode);
    }

    /** This function checks if the owner is done with path finding, and if not it will set the next node */
    private void updateNodes() {
        owner.setX(nextNode.getCenterX());
        owner.setY(nextNode.getCenterY());

        if (currentNode < path.size() - 1) {
            currentNode++;
            nextNode = path.get(currentNode);
            updateDeltaSpeed();
        } else {
            dx = 0;
            dy = 0;
            finalNode = null;
            path = null;
        }
    }

    private boolean atPosition(int x, int y) {
        return false;
    }

    /** Updates the speed taking in account the angle the owner has */
    public void updateDeltaSpeed() {
        angle = -(float) Math.atan2(nextNode.getCenterX() - owner.getX(), nextNode.getCenterY() - owner.getY());
        dx = (float) -(((MovingUnit) owner).speed * Math.cos(angle - Math.PI / 2));
        dy = (float) -(((MovingUnit) owner).speed * Math.sin(angle - Math.PI / 2));
    }

    /** aligns the object to the current node */
    public void setCurrentSquare() {
        if (currentSquare != null)
            currentSquare.standing = null;
        currentSquare = World.nodeAt(owner.getX(), owner.getY());
        currentSquare.standing = owner;
    }

    public float getAngle() {
        return angle;
    }
}
