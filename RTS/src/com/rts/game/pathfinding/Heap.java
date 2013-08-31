package com.rts.game.pathfinding;

import java.util.Iterator;
import java.util.LinkedList;
//This is actually just a priority queue, should be a heap when implemented for game use
//in order to improve runtime for adding an element

public class Heap {
    private LinkedList<float[]> list;

    public Heap() {
        list = new LinkedList<float[]>();
    }

    public void add(float[] newXY) {
        if (list.size() > 0) {
            Iterator<float[]> listIterator = list.iterator();
            float[] tmp;
            int count = 0;
            while (true) {
                tmp = listIterator.next();
                if (tmp[2] > newXY[2]) {
                    list.add(count, newXY);
                    break;
                } else {
                    count++;
                }
                if (!listIterator.hasNext()) {
                    list.add(count, newXY);
                    break;
                }
            }
        } else {
            list.add(newXY);
        }
    }

    public float[] pop() {
        try {
            return list.pop();
        } catch (Exception e) {
            System.out.println("List is Empty!!");
            return null;
        }
    }

    public int getSize() {
        return list.size();
    }
}
