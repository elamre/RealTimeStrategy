package com.rts.game.pathfinding;

import java.util.ArrayList;

/**
 * Holds a Node[][] 2d array "grid" for path-finding tests, all drawing is done through here.
 *
 * @author Clint Mullins
 */
public class Grid {
    public Node[][] grid;
    private Heap heap;
    public boolean addFirstNode = true;


    public ArrayList<Node> validNearbyNodes(Node target, int amount) {
        //Start with a destination point
        //Consider the point as an array with 1 value
        //For each entity:
        //For each point in the array, assign to a "valid points" array if valid
        //When the last point in the array is reached, clear array
        //For each last value, add its neighbors if the neighbor is both passable and not added to valid points

        //Attempt to assign these points to entities so that each can make space for others
        //For example, assign points closer to the start of the valid array to entities closer to the group center

        int count = 0;

        Node start = target;
        if (start.isPass()) {
            start = nearestValidPoint(start);
        }


        ArrayList<Node> valid = new ArrayList<Node>(32);
        ArrayList<Node> used = new ArrayList<Node>(32);

        valid.add(start);
        count++;

        outer:
        while (count < amount) {
            ArrayList<Node> newNearest = new ArrayList<Node>(32);
            inner:
            for (Node n : valid) {
                if (count >= amount) {
                    break inner;
                }
                if (!used.contains(n)) {
                    used.add(n);
                    for (int i = -1; i < 1; i++) {
                        for (int b = -1; b < 1; b++) {
                            Node test = getNode(n.x + i, n.y + b);
                            if (test.isPass())
                                if (!newNearest.contains(test) && !valid.contains(test)) {
                                    newNearest.add(test);
                                    count++;
                                    if (count >= amount) {
                                        break inner;
                                    }
                                }
                        }
                    }
                }
            }
            valid.addAll(newNearest);
        }


        return valid;

    }

    public Node nearestValidPoint(Node target) {
        //Start with a destination point
        //Consider the point as an array with 1 value
        //For each point in the array, return if valid
        //If invalid, ignore
        //When the last point in the array is reached, clear array
        //For each value in the array previously, add its neighbors

        //TODO: Debug this

        if (target.isPass()) {
            return target;
        }

        ArrayList<Node> nearest = new ArrayList<Node>(32);
        ArrayList<Node> used = new ArrayList<Node>(32);

        nearest.add(target);

        outer:
        while (true) {
            ArrayList<Node> newNearest = new ArrayList<Node>(32);
            for (Node n : nearest) {
                used.add(n);
                for (int i = -1; i < 1; i++) {
                    for (int b = -1; b < 1; b++) {
                        Node test = getNode(n.x + i, n.y + b);
                        if (test.isPass()) {
                            return test;
                        }
                        if (!used.contains(test)) {
                            newNearest.add(test);
                        }
                    }
                }
            }
            nearest = newNearest;
        }

    }


    /**
     * Grid is created, Land is generated in either uniform or random fashion, landscape 'Map' is created in printed.
     *
     * @param xMax - (int) maximum x coordinate
     * @param yMax - (int) maximum y coordinate
     */
    public Grid(int xMax, int yMax) {
        grid = new Node[xMax][yMax];
        heap = new Heap();
        initGrid();
    }

    private void initGrid() {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                grid[x][y] = new Node(x, y);
            }
        }
    }

    /**
     * returns all adjacent nodes that can be traversed
     *
     * @param node (Node) finds the neighbors of this node
     * @return (int[][]) list of neighbors that can be traversed
     */
    public int[][] getNeighbors(Node node) {
        int[][] neighbors = new int[8][2];
        int x = node.x;
        int y = node.y;
        boolean d0 = false; //These booleans are for speeding up the adding of nodes.
        boolean d1 = false;
        boolean d2 = false;
        boolean d3 = false;

        if (walkable(x, y - 1)) {
            neighbors[0] = (tmpInt(x, y - 1));
            d0 = d1 = true;
        }
        if (walkable(x + 1, y)) {
            neighbors[1] = (tmpInt(x + 1, y));
            d1 = d2 = true;
        }
        if (walkable(x, y + 1)) {
            neighbors[2] = (tmpInt(x, y + 1));
            d2 = d3 = true;
        }
        if (walkable(x - 1, y)) {
            neighbors[3] = (tmpInt(x - 1, y));
            d3 = d0 = true;
        }
        if (d0 && walkable(x - 1, y - 1)) {
            neighbors[4] = (tmpInt(x - 1, y - 1));
        }
        if (d1 && walkable(x + 1, y - 1)) {
            neighbors[5] = (tmpInt(x + 1, y - 1));
        }
        if (d2 && walkable(x + 1, y + 1)) {
            neighbors[6] = (tmpInt(x + 1, y + 1));
        }
        if (d3 && walkable(x - 1, y + 1)) {
            neighbors[7] = (tmpInt(x - 1, y + 1));
        }
        return neighbors;
    }

    /**
     * Tests an x,y node's ability to be walked on
     *
     * @param x (int) node's x coordinate
     * @param y (int) node's y coordinate
     * @return (boolean) true if the node is obstacle free and on the map, false otherwise
     */
    public boolean walkable(int x, int y) {
        try {
            return getNode(x, y).pass;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Node> reversePath(Node node, int startx, int starty) {
        ArrayList<Node> path = new ArrayList<Node>();
        System.out.println("Tracing Back Path...");

        while (node.parent != null) {
            path.add(0, node);
            node = node.parent;
        }


        if (addFirstNode) {
            path.add(0, getNode(startx, starty));
        }

        System.out.println("Path Trace Complete!");
        return path;
    }

    /**
     * Adds a node's (x,y,f) to the heap. The heap is sorted by 'f'.
     *
     * @param node (Node) node to be added to the heap
     */
    public void heapAdd(Node node) {
        float[] tmp = {node.x, node.y, node.f};
        heap.add(tmp);
    }

    /**
     * @return (int) size of the heap
     */
    public int heapSize() {
        return heap.getSize();
    }

    /**
     * @return (Node) takes data from popped float[] and returns the correct node
     */
    public Node heapPopNode() {
        float[] tmp = heap.pop();
        return getNode((int) tmp[0], (int) tmp[1]);
    }

    /**
     * Encapsulates x,y in an int[] for returning. A helper method for the jump method
     *
     * @param x (int) point's x coordinate
     * @param y (int) point's y coordinate
     * @return ([]int) bundled x,y
     */
    public int[] tmpInt(int x, int y) {
        return new int[]{x, y};
    }

    /**
     * getFunc - Node at given x, y
     *
     * @param x (int) desired node x coordinate
     * @param y (int) desired node y coordinate
     * @return (Node) desired node
     */
    public Node getNode(int x, int y) {
        try {
            return grid[x][y];
        } catch (Exception e) {
            return null;
        }
    }
}

