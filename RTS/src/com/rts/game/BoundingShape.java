package com.rts.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/12/13
 * Time: 11:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class BoundingShape {

    public boolean square;  //Square shape if true, circle if false.
    float x;    //Center X
    float y;    //Center Y
    float width;    //Also radius if a circle
    float height;

    BoundingShape(float x, float y, float diameter) {
        square = false;
        this.x = x;
        this.y = y;
        this.width = diameter / 2;
    }

    BoundingShape(float[] start, float[] end) {
        square = true;
        x = (start[0] + end[0]) / 2;
        y = (start[1] + end[1]) / 2;
        height = distance(start[0], start[1], start[0], end[1]);
        width = distance(start[0], start[1], end[0], start[1]);
    }

    BoundingShape(float x, float y, float width, float height) {
        square = true;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public ArrayList<Entity> findInContainer(EntityManager ents) {
        ArrayList<Entity> accepted = new ArrayList<Entity>(64);
        for (Map.Entry e : ents.entities.entrySet()) {
            Entity currentEntity = (Entity) e.getValue();

            if (this.contains(currentEntity.bounds)) {
                accepted.add(currentEntity);
            }

        }
        return accepted;
    }

    public boolean contains(BoundingShape b) {
        if (!this.square && !b.square) {
            //Circle-circle collision
            if (this.width + b.width < distance(this.x, this.y, b.x, b.y)) {
                return true;
            }
        } else if (this.square && b.square) {
            if (this.containsPoint(b.x + b.width / 2, b.y + b.height / 2) ||
                    this.containsPoint(b.x + b.width / 2, b.y - b.height / 2) ||
                    this.containsPoint(b.x - b.width / 2, b.y - b.height / 2) ||
                    this.containsPoint(b.x - b.width / 2, b.y + b.height / 2) ||

                    b.containsPoint(this.x + this.width / 2, this.y + this.height / 2) ||
                    b.containsPoint(this.x + this.width / 2, this.y - this.height / 2) ||
                    b.containsPoint(this.x - this.width / 2, this.y - this.height / 2) ||
                    b.containsPoint(this.x - this.width / 2, this.y + this.height / 2)
                    ) {
                //If any rectangle contains another point from the other rectangle
                return true;
            }
        } else {
            if (this.square) {
                float[] point = closestPointFromRectToPoint(this, new float[]{b.x, b.y});
                if (distance(point[0], point[1], this.x, this.y) <= b.width) {
                    return true;
                }
            } else {
                return b.contains(this);
            }
        }

        return false;
    }

    private static float distance(float x, float y, float x2, float y2) {
        return (float) Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
    }

    public boolean containsPoint(float x, float y) {
        if (!this.square) {
            if (distance(this.x, this.y, x, y) <= width) {
                return true;
            }
        } else {
            if (x >= this.x - width / 2 && x <= this.x + width / 2 && y >= this.y - height / 2 && y <= this.y + height / 2) {
                return true;
            }
        }
        return false;
    }

    private static float[] closestPointFromRectToPoint(BoundingShape rect, float[] point) {
        float[] pos = new float[2];

        if (point[0] > rect.x + rect.width) {
            pos[0] = rect.x + rect.width;
        } else if (point[0] < rect.x - rect.width) {
            pos[0] = rect.x - rect.width;
        } else {
            pos[0] = point[0];
        }

        if (point[1] > rect.y + rect.height) {
            pos[1] = rect.y + rect.height;
        } else if (point[1] < rect.y - rect.height) {
            pos[1] = rect.y - rect.height;
        } else {
            pos[1] = point[1];
        }

        return pos;
    }

    public void debugShape(Camera cam) {


        ShapeRenderer box = new ShapeRenderer();

        box.setProjectionMatrix(cam.getCamera().combined);
        box.setColor(1, 0, 1, 0.5f);

        float lx;
        float ly;

        if(square) {
            lx = x - width / 2;
            ly = y - height / 2;
            box.begin(ShapeRenderer.ShapeType.FilledRectangle);
            box.filledRect(lx, ly, width, height);
        }
        else {
            lx = x;
            ly = y;
            box.begin(ShapeRenderer.ShapeType.Circle);
            box.circle(lx, ly, width, 100);
        }

        box.end();
    }



}
