package com.rts.game.abilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.entities.Unit;
import com.rts.game.gameplay.World;
import com.rts.game.hud.AbilityButton;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 9/8/13
 * Time: 8:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildingSpace extends Ability {
    /** The space the building requires */
    public boolean[][] space;

    /**
     * Constructor
     *
     * @param owner the owner of the ability
     */
    public BuildingSpace(Unit owner) {
        super(owner, 0, null);
    }

    /** fills the spots on the grid so that nothing can move trough it anymore */
    public void create() {
        int length = space.length;
        int width = space[0].length;
        for (int x = 0; x < length; x++) {
            int x2 = (int) owner.getX() - length / 2 + x;
            for (int y = 0; y < width; y++) {
                int y2 = (int) owner.getY() - width / 2 + y;
                if (space[x][y] == true) {
                    World.jps.grid.grid[x2][y2].standing = owner;
                    World.jps.grid.grid[x2][y2].setPassable(false);
                }
            }
        }
    }

    /**
     * Checks if it is possible to build at the owners' position
     *
     * @return true if no obstacles in the way, false otherwise
     */
    public boolean isCreatable() {
        int length = space.length;
        int width = space[0].length;
        boolean canCreate = true;
        outer:
        for (int x = 0; x < length; x++) {
            int x2 = (int) owner.getX() - length / 2 + x;
            for (int y = 0; y < width; y++) {
                int y2 = (int) owner.getY() - width / 2 + y;
                if (x2 < 0 || y2 < 0)   //TODO add maximum parameters
                    return false;
                if (space[x][y] == true) {
                    if (World.jps.grid.grid[x2][y2].standing != null || !World.jps.grid.grid[x2][y2].isPass()) {
                        canCreate = false;
                        break outer;
                    }
                }
            }
        }
        return canCreate;
    }

    /** Removes the building space and makes it possible to move trough again */
    public void remove() {
        int length = space.length;
        int width = space[0].length;
        for (int x = 0; x < length; x++) {
            int x2 = (int) owner.getX() - length / 2 + x;
            for (int y = 0; y < width; y++) {
                int y2 = (int) owner.getY() - width / 2 + y;
                if (space[x][y] == true) {
                    World.jps.grid.grid[x2][y2].standing = null;
                    World.jps.grid.grid[x2][y2].setPassable(true);
                }
            }
        }
    }

    /**
     * Loads the space
     * @param newSpace the new space
     */
    public void loadSpace(boolean[][] newSpace) {
        space = new boolean[newSpace.length][newSpace[0].length];
        for (int x = 0; x < newSpace.length; x++) {
            for (int y = 0; y < newSpace[0].length; y++) {
                space[x][y] = newSpace[x][y];
            }
        }
    }

    /** the action to be executed for the ability */
    @Override
    public void action() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Update function for anything ability related
     *
     * @param deltaT the time that has passed since the previous update
     */
    @Override
    public void update_1(float deltaT) {
        //To change body of implemented methods use File | Settings | File Templates.
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
}
