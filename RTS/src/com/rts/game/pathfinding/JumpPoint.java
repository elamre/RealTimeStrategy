package com.rts.game.pathfinding;

import java.util.ArrayList;

/**
 * @author Clint Mullins
 *         Modified by Jacob Laurendeau for temporary use on a game project
 */

public class JumpPoint {
    public Grid grid;
    private int startX;
    private int startY;
    private int endX;
    private int endY;  //variables for reference grid

    boolean returnWhenNotFinal = true;

    //TODO: Fix bug where using the same X or Y in the start or end will miss a path
    //TODO: Improve paths returned on a failed tile

    /**
     * Initializer; sets up variables, creates reference grid and actual grid, gets start and end points, initiates search
     *
     * @param xMax (int) maximum x value on map + 1 (if xMax==100, actual x maximum is 99)
     * @param yMax (int) maximum y value on map + 1 (if yMax==100, actual y maximum is 99)
     */
    public JumpPoint(int xMax, int yMax) {
        grid = new Grid(xMax, yMax);  //preMadeGrid is passed in because there CAN BE ONLY ONE GRID
        Heuristic.setCurrentHeuristic(Heuristic.MANHATTAN);
    }

    /**
     * Orchestrates the Jump Point Search; it is explained further in comments below.
     */
    public ArrayList<Node> search(int sx, int sy, int ex, int ey) {

        this.startX = sx;   //the start point x value
        this.startY = sy;      //the start point y value
        this.endX = ex;      //the end point x value
        this.endY = ey;      //the end point y value

        if (!grid.getNode(endX, endY).isPass()) {
            Node n = grid.nearestValidPoint(grid.getNode(endX, endY));
            endX = n.getX();
            endY = n.getY();
            System.out.println("Target node invalid, using node at " + endX + ", " + endY);
        }

        long timeStart = System.currentTimeMillis();

        Node backup = grid.getNode(startX, startY);
        int backupDist = 999999;


        System.out.println("Jump Point Search\n----------------");
        System.out.println("Start X: " + startX + " Y: " + startY);  //Start and End points are printed for reference
        System.out.println("End   X: " + endX + " Y: " + endY);
        grid.getNode(startX, startY).update(0, 0, null);
        grid.heapAdd(grid.getNode(startX, startY));  //Start node is added to the heap
        while (true) {
            Node cur = grid.heapPopNode();
            if (cur.x == endX && cur.y == endY) {        //if the end node is found
                System.out.println("Path Found!");  //print "Path Found!"
                ArrayList<Node> trail = grid.reversePath(cur, startX, startY);

                long timeEnd = System.currentTimeMillis();
                System.out.println("Time: " + (timeEnd - timeStart) + " ms");

                return trail;
            }
            Node[] possibleSuccess1 = identifySuccessors(cur);
            for (Node possibleSuccess : possibleSuccess1) {     //for each one of them
                if (possibleSuccess != null) {                //if it is not null
                    grid.heapAdd(possibleSuccess);        //add it to the heap for later use (a possible future cur)
                }
            }

            //Save a backup node
            if (getDistance(cur.x, cur.y, endX, endY) < backupDist) {
                backupDist = (int) getDistance(cur.x, cur.y, endX, endY);
                backup = cur;
            }

            if (grid.heapSize() == 0) {                        //if the grid size is 0, and we have not found our end, the end is unreachable
                System.out.println("No Path....");            //print "No Path...." to (lolSpark) notify user
                if (!returnWhenNotFinal)
                    break;                                        //loop is done
                else {
                    //Return backup if needed
                    return grid.reversePath(backup, startX, startY);
                }
            }
        }


        return null;

    }

    protected float getDistance(float x, float y, float x2, float y2) {
        return (float) Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
    }

    /**
     * returns all nodes jumped from given node
     *
     * @param node The node
     * @return all nodes jumped from given node
     */
    Node[] identifySuccessors(Node node) {
        Node[] successors = new Node[8];
        int[][] neighbors = getNeighborsPrune(node);
        for (int i = 0; i < neighbors.length; i++) { //for each of these neighbors
            int[] tmpXY = jump(neighbors[i][0], neighbors[i][1], node.x, node.y);
            if (tmpXY[0] != -1) {                                //if that point is not null( {-1,-1} )
                int x = tmpXY[0];
                int y = tmpXY[1];
                float ng = Heuristic.get(Math.abs(x - node.x), Math.abs(y - node.y)) + node.g;
                if (grid.getNode(x, y).f <= 0 || grid.getNode(x, y).g > ng) {  //if this node is not already found, or we have a shorter distance from the current node
                    grid.getNode(x, y).update(ng, Heuristic.get(Math.abs(x - endX), Math.abs(y - endY)), node); //then update the rest of it
                    successors[i] = grid.getNode(x, y);  //add this node to the successors list to be returned
                }
            }
        }
        return successors;  //finally, successors is returned
    }

    /**
     * jump method recursively searches in the direction of parent (px,py) to child, the current node (x,y).
     * It will stop and return its current position in three situations:
     * 1) The current node is the end node. (endX, endY)
     * 2) The current node is a forced neighbor.
     * 3) The current node is an intermediate step to a node that satisfies either 1) or 2)
     *
     * @param x  (int) current node's x
     * @param y  (int) current node's y
     * @param px (int) current.parent's x
     * @param py (int) current.parent's y
     * @return (int[] x, y) node which satisfies one of the conditions above, or null if no such node is found.
     */
    int[] jump(int x, int y, int px, int py) {
        int[] jx; //used to later check if full or null
        int[] jy; //used to later check if full or null
        int dx = (x - px) / Math.max(Math.abs(x - px), 1); //because parents aren't always adjacent, this is used to find parent -> child direction (for x)
        int dy = (y - py) / Math.max(Math.abs(y - py), 1); //because parents aren't always adjacent, this is used to find parent -> child direction (for y)

        if (!grid.walkable(x, y)) { //if this space is not grid.walkable, return a null.
            return tmpInt(-1, -1); //in this system, returning a {-1,-1} equates to a null and is ignored.
        }
        if (x == this.endX && y == this.endY) {   //if end point, return that point. The search is over!
            return tmpInt(x, y);
        }
        if (dx != 0 && dy != 0) {  //if x and y both changed, we are on a diagonally adjacent square: here we check for forced neighbors on diagonals
            if ((grid.walkable(x - dx, y + dy) && !grid.walkable(x - dx, y)) || //we are moving diagonally, we don't check the parent, or our next diagonal step, but the other diagonals
                    (grid.walkable(x + dx, y - dy) && !grid.walkable(x, y - dy))) {  //if we find a forced neighbor here, we are on a jump point, and we return the current position
                return tmpInt(x, y);
            }
        } else { //check for horizontal/vertical
            if (dx != 0) { //moving along x
                if ((grid.walkable(x + dx, y + 1) && !grid.walkable(x, y + 1)) || //we are moving along the x axis
                        (grid.walkable(x + dx, y - 1) && !grid.walkable(x, y - 1))) {  //we check our side nodes to see if they are forced neighbors
                    return tmpInt(x, y);
                }
            } else {
                if ((grid.walkable(x + 1, y + dy) && !grid.walkable(x + 1, y)) ||  //we are moving along the y axis
                        (grid.walkable(x - 1, y + dy) && !grid.walkable(x - 1, y))) {     //we check our side nodes to see if they are forced neighbors
                    return tmpInt(x, y);
                }
            }
        }

        if (dx != 0 && dy != 0) { //when moving diagonally, must check for vertical/horizontal jump points
            jx = jump(x + dx, y, x, y);
            jy = jump(x, y + dy, x, y);
            if (jx[0] != -1 || jy[0] != -1) {
                return tmpInt(x, y);
            }
        }
        if (grid.walkable(x + dx, y) || grid.walkable(x, y + dy)) { //moving diagonally, must make sure one of the vertical/horizontal neighbors is open to allow the path
            if (x + dx == x && y + dy == y && x == px && y == py) {
                return tmpInt(-1, -1);
                //Stop endless recursion loops
            }
            return jump(x + dx, y + dy, x, y);
        } //if we are trying to move diagonally but we are blocked by two touching corners of adjacent nodes, we return a null
        return tmpInt(-1, -1);

    }

    /**
     * Encapsulates x,y in an int[] for returning. A helper method for the jump method
     *
     * @param x (int) point's x coordinate
     * @param y (int) point's y coordinate
     * @return ([]int) bundled x,y
     */
    int[] tmpInt(int x, int y) {
        return new int[]{x, y};
    }

    /**
     * Returns nodes that should be jumped based on the parent location in relation to the given node.
     *
     * @param node (Node) node which has a parent (not the start node)
     * @return (ArrayList Node) list of nodes that will be jumped
     */
    int[][] getNeighborsPrune(Node node) {
        Node parent = node.parent;    //the parent node is retrieved for x,y values
        int x = node.x;
        int y = node.y;
        int px, py, dx, dy;
        int[][] neighbors = new int[5][2];
        //directed pruning: can ignore most neighbors, unless forced
        if (parent != null) {
            px = parent.x;
            py = parent.y;
            //get the normalized direction of travel
            dx = (x - px) / Math.max(Math.abs(x - px), 1);
            dy = (y - py) / Math.max(Math.abs(y - py), 1);
            //search diagonally
            if (dx != 0 && dy != 0) {
                if (grid.walkable(x, y + dy)) {
                    neighbors[0] = (tmpInt(x, y + dy));
                }
                if (grid.walkable(x + dx, y)) {
                    neighbors[1] = (tmpInt(x + dx, y));
                }
                if (grid.walkable(x, y + dy) || grid.walkable(x + dx, y)) {
                    neighbors[2] = (tmpInt(x + dx, y + dy));
                }
                if (!grid.walkable(x - dx, y) && grid.walkable(x, y + dy)) {
                    neighbors[3] = (tmpInt(x - dx, y + dy));
                }
                if (!grid.walkable(x, y - dy) && grid.walkable(x + dx, y)) {
                    neighbors[4] = (tmpInt(x + dx, y - dy));
                }
            } else {
                if (dx == 0) {
                    if (grid.walkable(x, y + dy)) {
                        if (grid.walkable(x, y + dy)) {
                            neighbors[0] = (tmpInt(x, y + dy));
                        }
                        if (!grid.walkable(x + 1, y)) {
                            neighbors[1] = (tmpInt(x + 1, y + dy));
                        }
                        if (!grid.walkable(x - 1, y)) {
                            neighbors[2] = (tmpInt(x - 1, y + dy));
                        }
                    }
                } else {
                    if (grid.walkable(x + dx, y)) {
                        if (grid.walkable(x + dx, y)) {
                            neighbors[0] = (tmpInt(x + dx, y));
                        }
                        if (!grid.walkable(x, y + 1)) {
                            neighbors[1] = (tmpInt(x + dx, y + 1));
                        }
                        if (!grid.walkable(x, y - 1)) {
                            neighbors[2] = (tmpInt(x + dx, y - 1));
                        }
                    }
                }
            }
        } else {//return all neighbors
            return grid.getNeighbors(node); //adds initial nodes to be jumped from!
        }

        return neighbors; //this returns the neighbors, you know
    }
}
