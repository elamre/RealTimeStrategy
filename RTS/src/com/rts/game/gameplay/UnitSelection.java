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

    public ArrayList<Entity> currentSelection = new ArrayList<Entity>(64);

    public void setCurrent() {
        for (int i = 0, l = currentSelection.size(); i < l; i++) {
            ((SelectableUnit) currentSelection.get(i)).setSelected(true);
        }
    }

    public void disableCurrent() {
        for (int i = 0, l = currentSelection.size(); i < l; i++) {
            ((SelectableUnit) currentSelection.get(i)).setSelected(false);
        }
    }

    public void clear() {
        currentSelection.clear();
    }

    public void remove(Entity e) {
        currentSelection.remove(e);
    }

    public void remove(ArrayList<Entity> removal) {
        currentSelection.removeAll(removal);
    }

    public void add(Entity e) {
        currentSelection.add(e);
    }

    public void add(ArrayList<Entity> adding) {
        currentSelection.addAll(adding);
    }

    public boolean contains(Entity e) {
        return currentSelection.contains(e);
    }

    public void massWalkTo(int x, int y) {

        ArrayList<Node> spots = World.jps.grid.validNearbyNodes(World.nodeAt(x, y), currentSelection.size());

        for (int i = 0; i < spots.size(); i++) {
            Entity e = currentSelection.get(i);
            if (e instanceof MovingUnit) {
                ((MovingUnit) e).walker.updatePath(spots.get(i).getX(), spots.get(i).getY());
            }
        }


    }

}
