package com.rts.game.pathfinding;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/23/13
 * Time: 12:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class Grid {

    public Node[][] nodes;

    public boolean isWalkableAt(int x, int y) {
        if (valid(x, y))
            return nodes[x][y].isWalkable();
        else return false;
    }

    public boolean isBlockedAt(int x, int y) {
        System.out.println(x + ", " + y);
        if (valid(x, y)) {
            System.out.println("Works?");
            return nodes[x][y].isBlocked();
        }
        System.out.println("Works 2?");
        return true;
    }

    public void setWalkable(int x, int y) {
        nodes[x][y].setWalkable();
    }

    public void setBlocked(int x, int y) {
        nodes[x][y].setBlocked();
    }

    public boolean valid(int x, int y) {
        if (x >= 0 && y >= 0 && x < nodes.length && y < nodes[0].length) {
            return true;
        }
        return false;
    }

    public void buildNodes(int width, int height) {
        nodes = new Node[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                nodes[x][y] = new Node(x, y);
            }
        }
    }

    public void buildNodes(int width, int height, boolean[][] blocked) {
        nodes = new Node[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                nodes[x][y] = new Node(x, y, blocked[x][y]);
            }
        }
    }

    /**
     * Get the neighbors of the given node.
     * offsets      diagonalOffsets:
     * +---+---+---+    +---+---+---+
     * |   | 0 |   |    | 0 |   | 1 |
     * +---+---+---+    +---+---+---+
     * | 3 |   | 1 |    |   |   |   |
     * +---+---+---+    +---+---+---+
     * |   | 2 |   |    | 3 |   | 2 |
     * +---+---+---+    +---+---+---+
     * When allowDiagonal is true, if offsets[i] is valid, then
     * diagonalOffsets[i] and
     * diagonalOffsets[(i + 1) % 4] is valid.
     *
     * @param {Node}    node
     * @param {boolean} allowDiagonal
     * @param {boolean} dontCrossCorners
     */
    public ArrayList<Node> getNeighbors(Node node, boolean allowDiagonal, boolean dontCrossCorners) {
        int x = node.getX();
        int y = node.getY();
        ArrayList<Node> neighbors = new ArrayList<Node>(8);
        boolean s0 = false, d0 = false,
                s1 = false, d1 = false,
                s2 = false, d2 = false,
                s3 = false, d3 = false;

        // ↑
        if (isWalkableAt(x, y - 1)) {
            neighbors.add(nodes[y - 1][x]);
            s0 = true;
        }
        // →
        if (isWalkableAt(x + 1, y)) {
            neighbors.add(nodes[y][x + 1]);
            s1 = true;
        }
        // ↓
        if (isWalkableAt(x, y + 1)) {
            neighbors.add(nodes[y + 1][x]);
            s2 = true;
        }
        // ←
        if (isWalkableAt(x - 1, y)) {
            neighbors.add(nodes[y][x - 1]);
            s3 = true;
        }

        if (!allowDiagonal) {
            return neighbors;
        }

        if (dontCrossCorners) {
            d0 = s3 && s0;
            d1 = s0 && s1;
            d2 = s1 && s2;
            d3 = s2 && s3;
        } else {
            d0 = s3 || s0;
            d1 = s0 || s1;
            d2 = s1 || s2;
            d3 = s2 || s3;
        }

        // ↖
        if (d0 && isWalkableAt(x - 1, y - 1)) {
            neighbors.add(nodes[y - 1][x - 1]);
        }
        // ↗
        if (d1 && isWalkableAt(x + 1, y - 1)) {
            neighbors.add(nodes[y - 1][x + 1]);
        }
        // ↘
        if (d2 && isWalkableAt(x + 1, y + 1)) {
            neighbors.add(nodes[y + 1][x + 1]);
        }
        // ↙
        if (d3 && isWalkableAt(x - 1, y + 1)) {
            neighbors.add(nodes[y + 1][x - 1]);
        }

        return neighbors;
    }

    public float distance(float x, float y, float x2, float y2) {
        return (float) Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
    }


}
