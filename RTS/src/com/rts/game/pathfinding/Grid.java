package com.rts.game.pathfinding;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/23/13
 * Time: 12:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class Grid {

    Node[][] nodes;

    public boolean walkable(int x, int y) {
        return nodes[x][y].isWalkable();
    }

    public boolean blocked(int x, int y) {
        return nodes[x][y].isBlocked();
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
    public Node[] getNeighbors(Node node, boolean allowDiagonal, boolean dontCrossCorners) {
        int x = node.getX();
        int y = node.getY();
        Node[] neighbors = new Node[8];
        boolean s0 = false, d0 = false,
                s1 = false, d1 = false,
                s2 = false, d2 = false,
                s3 = false, d3 = false;

        // ↑
        if (walkable(x, y - 1)) {
            neighbors[0] = (nodes[y - 1][x]);
            s0 = true;
        }
        // →
        if (walkable(x + 1, y)) {
            neighbors[1] = (nodes[y][x + 1]);
            s1 = true;
        }
        // ↓
        if (walkable(x, y + 1)) {
            neighbors[2] = (nodes[y + 1][x]);
            s2 = true;
        }
        // ←
        if (walkable(x - 1, y)) {
            neighbors[3] = (nodes[y][x - 1]);
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
        if (d0 && walkable(x - 1, y - 1)) {
            neighbors[4] = (nodes[y - 1][x - 1]);
        }
        // ↗
        if (d1 && walkable(x + 1, y - 1)) {
            neighbors[5] = (nodes[y - 1][x + 1]);
        }
        // ↘
        if (d2 && walkable(x + 1, y + 1)) {
            neighbors[6] = (nodes[y + 1][x + 1]);
        }
        // ↙
        if (d3 && walkable(x - 1, y + 1)) {
            neighbors[7] = (nodes[y + 1][x - 1]);
        }

        return neighbors;
    }

    ;


}
