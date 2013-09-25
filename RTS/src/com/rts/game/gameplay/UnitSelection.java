package com.rts.game.gameplay;

import com.rts.game.entities.Entity;
import com.rts.game.entities.MovingUnit;
import com.rts.game.entities.SelectableUnit;
import com.rts.game.pathfinding.Node;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 9/12/13
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnitSelection {
    /** The current selection of entities */
    private ArrayList<Entity> currentSelection = new ArrayList<Entity>(64);

    /** TODO */
    public void setCurrent() {
        for (int i = 0, l = currentSelection.size(); i < l; i++) {
            ((SelectableUnit) currentSelection.get(i)).setSelected(true);
        }
    }

    /**
     * getter for the current selection
     *
     * @return the entities contained in the selection
     */
    public ArrayList<Entity> getCurrentSelection() {
        return currentSelection;
    }

    /** Deselects all the current units */
    public void disableCurrent() {
        for (int i = 0, l = currentSelection.size(); i < l; i++) {
            ((SelectableUnit) currentSelection.get(i)).setSelected(false);
        }
    }

    /** Clears the current selection */
    public void clear() {
        currentSelection.clear();
    }

    /**
     * Removes one specific entity
     *
     * @param e the entity to be removed
     */
    public void remove(Entity e) {
        currentSelection.remove(e);
    }

    /**
     * Removes an array of units
     *
     * @param removal the array to remove from the current selection
     */
    public void remove(ArrayList<Entity> removal) {
        currentSelection.removeAll(removal);
    }

    /**
     * Adds a single entity
     *
     * @param e the entity to add
     */
    public void add(Entity e) {
        currentSelection.add(e);
    }

    /**
     * Adds an array of entities
     *
     * @param adding the array to add to the current selection
     */
    public void add(ArrayList<Entity> adding) {
        currentSelection.addAll(adding);
    }

    /**
     * Checks if the current selection contains a specific unit
     *
     * @param e the entity to check for
     * @return true if selection contains entity e
     */
    public boolean contains(Entity e) {
        return currentSelection.contains(e);
    }

    /**
     * sends the move command to all the selected units
     *
     * @param x the x vector of the position to move to
     * @param y the y vector of the position to move to
     */
    public void massWalkTo(int x, int y) {

        ArrayList<Node> spots = World.jps.grid.validNearbyNodes(World.nodeAt(x, y), currentSelection.size());

        System.out.println("Spots available: " + spots.size());

        for (int i = 0; i < currentSelection.size(); i++) {
            Entity e = currentSelection.get(i);
            if (e instanceof MovingUnit) {
                ((MovingUnit) e).walker.updatePath(spots.get(i).getX(), spots.get(i).getY());
            }
        }
    }

}
