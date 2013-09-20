package com.rts.game.abilities;

import com.rts.game.entities.Unit;
import com.rts.game.gameplay.World;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 9/8/13
 * Time: 8:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildingSpace extends Ability {

    public boolean[][] space;

    public BuildingSpace(Unit owner) {
        super(owner);

    }

    @Override
    public void logic(float delta) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void draw() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

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

    public boolean isCreatable() {

        int length = space.length;
        int width = space[0].length;

        boolean canCreate = true;

        outer:
        for (int x = 0; x < length; x++) {

            int x2 = (int) owner.getX() - length / 2 + x;

            for (int y = 0; y < width; y++) {
                int y2 = (int) owner.getY() - width / 2 + y;
                if (x2 < 0 || y2 < 0)
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

    public void loadSpace(boolean[][] newspace) {

        space = new boolean[newspace.length][newspace[0].length];

        for (int x = 0; x < newspace.length; x++) {
            for (int y = 0; y < newspace[0].length; y++) {
                space[x][y] = newspace[x][y];
            }
        }

    }
}
