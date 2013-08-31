package com.rts.game.pathfinding;

public class Node {
    int x;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPassable(boolean pass) {
        this.pass = pass;
    }

    int y;
    float g;
    float f;  //g = from start; f = distance from start and end
    boolean pass;
    Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.pass = true;
    }

    public void update(float g, float h, Node parent) {
        this.parent = parent;
        this.g = g;
        f = g + h;
    }

}
