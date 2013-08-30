package com.rts.game.pathfinding;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/22/13
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Node {
    private int x;
    private int y;
    private boolean blocked;

    float f;
    float g;
    float h;

    boolean opened = false;
    boolean closed = false;

    private Node parent;

    private float fromStart;
    private float toEnd;
    private float startToEnd;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void reset() {
        f = 0;
        g = 0;
        h = 0;
        opened = false;
        closed = false;
        parent = null;
        fromStart = 0;
        toEnd = 0;
        startToEnd = 0;
    }

    public Node(int x, int y, boolean blocked) {
        this.x = x;
        this.y = y;
        this.blocked = blocked;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setPosition(int[] pos) {
        this.x = pos[0];
        this.y = pos[1];
    }

    public int[] getPosition() {
        return new int[]{x, y};
    }

    public float getStartToEnd() {
        return startToEnd;
    }

    public void setStartToEnd(float startToEnd) {
        this.startToEnd = startToEnd;
    }

    public float getToEnd() {
        return toEnd;
    }

    public void setToEnd(float toEnd) {
        this.toEnd = toEnd;
    }

    public float getFromStart() {
        return fromStart;
    }

    public void setFromStart(float fromStart) {
        this.fromStart = fromStart;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setBlocked() {
        this.blocked = true;
    }

    public void setWalkable() {
        this.blocked = false;
    }

    public boolean isWalkable() {
        return !blocked;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
