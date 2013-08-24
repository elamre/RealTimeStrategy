package com.rts.game.pathfinding;

import java.util.ArrayList;

/**
 * Uses
 * https://github.com/qiao/PathFinding.js
 * as reference
 */
public class JumpPoint {

    Grid grid;

    public ArrayList<int[]> search(int startX, int startY, int endX, int endY) {

        Node endNode = grid.nodes[endX][endY];

        ArrayList<Node> list = new ArrayList<Node>(32);

        list.add(grid.nodes[startX][startY]);

        // set the `g` and `f` value of the start node to be 0
        grid.nodes[startX][startY].g = 0;
        grid.nodes[startX][startY].f = 0;

        // push the start node into the open list
        list.add(grid.nodes[startX][startY]);
        grid.nodes[startX][startY].opened = true;


        // while the open list is not empty
        while (!list.isEmpty()) {
            // pop the position of node which has the minimum `f` value.
            Node node;
            float lowF = list.get(0).f;
            int lowFPos = 0;
            //TODO: Make removal/popping more efficient
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).f <= lowF) {
                    lowF = list.get(i).f;
                    lowFPos = i;
                }
            }
            node = list.get(lowFPos);
            list.remove(lowFPos);

            node.closed = true;

            if (node.getX() == endNode.getX() && node.getY() == endNode.getY()) {
                return backtrace(endNode);
            }

            identifySuccessors(node, endNode);
        }

        // fail to find the path
        return null;
    }

    private ArrayList<int[]> backtrace(Node n) {
        ArrayList<int[]> path = new ArrayList<int[]>(16);
        path.add(n.getPosition());
        Node node = n;
        while (node.getParent() != null) {
            node = node.getParent();
            path.add(node.getPosition());
        }
        return path;
    }

    /**
     * returns all nodes jumped from given node
     *
     * @param node
     * @return all nodes jumped from given node
     */
    public Node[] identifySuccessors(Node node, Node endNode) {
        Node[] successors = new Node[8];                //empty successors list to be returned
        int[][] neighbors = findNeighbors(node);    //all neighbors after pruned
        for (int i = 0; i < neighbors.length; i++) { //for each of these neighbors
            int[] temp = jump(neighbors[i][0], neighbors[i][1], node.getX(), node.getY(), endNode); //get next jump point
            Node jump = grid.nodes[temp[0]][temp[1]];
            if (jump != null) {
                //if that point is not null( {-1,-1} )
                int x = temp[0];
                int y = temp[1];

                if (!jump.closed) {

                    float d = Heuristic.get(Math.abs(x - node.getX()), Math.abs(y - node.getY()));
                    float ng = (grid.distance(x, y, node.getX(), node.getY()) + node.g);   //get the distance from start

                    if (!jump.opened || ng < jump.g) {
                        jump.g = ng;
                        if (jump.h == 0)
                            jump.h = Heuristic.get(Math.abs(x - endNode.getX()), Math.abs(y - endNode.getY()));
                        jump.f = jump.g + jump.h;
                    }

                }
            }
        }
        return successors;  //finally, successors is returned
    }

    /**
     * Search recursively in the direction (parent -> child), stopping only when a
     * jump point is found.
     *
     * @return {Array.<[number, number]>} The x, y coordinate of the jump point
     *         found, or null if not found
     * @protected
     */
    public int[] jump(int x, int y, int px, int py, Node end) {
        int dx = x - px, dy = y - py;
        int[] jx, jy;

        if (grid.isBlockedAt(x, y)) {
            return null;
        }

        /*
            if(this.trackJumpRecursion === true) {
        grid.getNodeAt(x, y).tested = true;
    }
         */

        if (x == end.getX() && y == end.getY()) {
            return new int[]{x, y};
        }

        // check for forced neighbors
        // along the diagonal
        if (dx != 0 && dy != 0) {
            if ((grid.isWalkableAt(x - dx, y + dy) && grid.isBlockedAt(x - dx, y)) ||
                    (grid.isWalkableAt(x + dx, y - dy) && grid.isBlockedAt(x, y - dy))) {
                return new int[]{x, y};
            }
        }
        // horizontally/vertically
        else {
            if (dx != 0) { // moving along x
                if ((grid.isWalkableAt(x + dx, y + 1) && grid.isBlockedAt(x, y + 1)) ||
                        (grid.isWalkableAt(x + dx, y - 1) && grid.isBlockedAt(x, y - 1))) {
                    return new int[]{x, y};
                }
            } else {
                if ((grid.isWalkableAt(x + 1, y + dy) && grid.isBlockedAt(x + 1, y)) ||
                        (grid.isWalkableAt(x - 1, y + dy) && grid.isBlockedAt(x - 1, y))) {
                    return new int[]{x, y};
                }
            }
        }

        // when moving diagonally, must check for vertical/horizontal jump points
        if (dx != 0 && dy != 0) {
            jx = this.jump(x + dx, y, x, y, end);
            jy = this.jump(x, y + dy, x, y, end);
            if (jx != null && jy != null) {
                return new int[]{x, y};
            }
        }

        // moving diagonally, must make sure one of the vertical/horizontal
        // neighbors is open to allow the path
        if (grid.isWalkableAt(x + dx, y) || grid.isWalkableAt(x, y + dy)) {
            return this.jump(x + dx, y + dy, x, y, end);
        } else {
            return null;
        }
    }

    public int[][] findNeighbors(Node node) {

        Node parent = node.getParent();

        int x = node.getX();
        int y = node.getY();

        int px, py, nx, ny, dx, dy;

        int[][] neighbors = new int[5][2];

        if (parent != null) {
            px = parent.getX();
            py = parent.getY();

            dx = (x - px) / Math.max(Math.abs(x - px), 1);
            dy = (y - py) / Math.max(Math.abs(y - py), 1);

            if (dx != 0 && dy != 0) {
                if (grid.isWalkableAt(x, y + dy)) {
                    neighbors[0] = new int[]{x, y + dy};
                }
                if (grid.isWalkableAt(x + dx, y)) {
                    neighbors[1] = new int[]{x + dx, y};
                }
                if (grid.isWalkableAt(x, y + dy) || grid.isWalkableAt(x + dx, y)) {
                    neighbors[2] = new int[]{x + dx, y + dy};
                }
                if (grid.isBlockedAt(x - dx, y) && grid.isWalkableAt(x, y + dy)) {
                    neighbors[3] = new int[]{x - dx, y + dy};
                }
                if (grid.isBlockedAt(x, y - dy) && grid.isWalkableAt(x + dx, y)) {
                    neighbors[4] = new int[]{x + dx, y - dy};
                }
            }
            // search horizontally/vertically
            else {
                if (dx == 0) {
                    if (grid.isWalkableAt(x, y + dy)) {
                        if (grid.isWalkableAt(x, y + dy)) {
                            neighbors[0] = new int[]{x, y + dy};
                        }
                        if (grid.isBlockedAt(x + 1, y)) {
                            neighbors[1] = new int[]{x + 1, y + dy};
                        }
                        if (grid.isBlockedAt(x - 1, y)) {
                            neighbors[2] = new int[]{x - 1, y + dy};
                        }
                    }
                } else {
                    if (grid.isWalkableAt(x + dx, y)) {
                        if (grid.isWalkableAt(x + dx, y)) {
                            neighbors[0] = new int[]{x + dx, y};
                        }
                        if (grid.isBlockedAt(x, y + 1)) {
                            neighbors[1] = new int[]{x + dx, y + 1};
                        }
                        if (grid.isBlockedAt(x, y - 1)) {
                            neighbors[2] = new int[]{x + dx, y - 1};
                        }
                    }
                }
            }
        }
        // return all neighbors
        else {
            neighbors = grid.getNeighborsInts(node, true, true);
        }

        return neighbors;


    }

}