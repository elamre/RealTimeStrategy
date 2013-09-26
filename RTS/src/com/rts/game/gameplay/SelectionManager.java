package com.rts.game.gameplay;

import com.rts.game.entities.Entity;
import com.rts.game.entities.MovingUnit;
import com.rts.game.entities.SelectableUnit;
import com.rts.game.entities.Unit;
import com.rts.game.pathfinding.Node;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/25/13
 * Time: 9:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class SelectionManager {
    /** All the saved selections */
    private HashMap<Integer, UnitSelection> keyToUnit = new HashMap<Integer, UnitSelection>();
    /** Current selection */
    private ArrayList<Entity> currentSelection = new ArrayList<Entity>();

    /**
     * Registers a group of units to a number
     *
     * @param group the number representing the units
     * @param units the units to associate to the number
     */
    public void registerGroup(int group, ArrayList<Entity> units) {
        UnitSelection unitSelection = new UnitSelection();
        unitSelection.add(units);
        keyToUnit.put(group, unitSelection);
    }

    /**
     * simple getter for the group and an number
     *
     * @param group the number of which the group is wanted
     * @return either the group, or null if the group doesn't exist
     */
    public ArrayList<Entity> getGroup(int group) {
        if (keyToUnit.containsKey(group))
            return keyToUnit.get(group).getCurrentSelection();
        return new ArrayList<Entity>(0);
    }

    /**
     * This function returns the selection object
     *
     * @param selection the selection id
     * @return either the selection, or null if the selection doesn't exist
     */
    public UnitSelection getSelection(int selection) {
        if (keyToUnit.containsKey(selection))
            return keyToUnit.get(selection);
        return new UnitSelection();
    }

    /**
     * Adds a single entity to the current selection
     *
     * @param entity the entity to add
     */
    public void addCurrentSelection(Entity entity) {
        currentSelection.add(entity);
    }

    /**
     * getter
     *
     * @return current selection
     */
    public ArrayList<Entity> getCurrentSelection() {
        return currentSelection;
    }

    /**
     * Sets the current selection of units
     *
     * @param currentSelection the new selection. A copy will be made
     */
    public void setCurrentSelection(ArrayList<Entity> currentSelection) {
        clearCurrentSelection();
        for (int i = 0; i < currentSelection.size(); i++) {
            this.currentSelection.add(currentSelection.get(i));
            ((SelectableUnit) this.currentSelection.get(i)).setSelected(true);
        }
    }

    /** Clears the current selection and deselects all the units */
    public void clearCurrentSelection() {
        for (int i = 0; i < this.currentSelection.size(); i++) {
            ((SelectableUnit) this.currentSelection.get(i)).setSelected(false);
        }
        currentSelection.clear();
    }

    /**
     * Checks if a entity already exists in the current selection (don't want to add the same entity twice)
     *
     * @param entity the entity to check for
     * @return true if it already exists, false otherwise
     */
    public boolean currentSelectionContains(Entity entity) {
        return currentSelection.contains(entity);
    }

    /**
     * sends the move command to all the selected units
     *
     * @param x the x vector of the position to move to
     * @param y the y vector of the position to move to
     */
    public void walkCurrentSelectionTo(int x, int y) {
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
