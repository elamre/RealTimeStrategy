package com.rts.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rts.game.Assets;
import com.rts.game.gameplay.Camera;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/2/13
 * Time: 8:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShapeRenderer {
    private static Sprite rectangle;
    private static Color color;

    static {
        rectangle = Assets.getAssets().getSprite("UI/button");
        setColor(Color.BLACK);
    }

    public static void setColor(Color color1) {
        color = color1;

        rectangle.setColor(color);
    }

    public static void drawRectangle(float x, float y, float width, float height, boolean filled) {
        if (!filled) {
            drawLine(x, y, x + width, y);
            drawLine(x, y, x, y + height);
            drawLine(x, y + height, x + width, y + height);
            drawLine(x + width, y, x + height, y + height);
        } else {
            rectangle.setPosition(x, y);
            rectangle.setSize(width, height);
            rectangle.draw(Camera.batch);
        }
    }

    public static void drawLine(float x1, float y1, float x2, float y2) {
        rectangle.setSize(x2 - x1 + 1, y2 - y1 + 1);
        rectangle.setPosition(x1, y1);
        rectangle.draw(Camera.batch);
    }

    public static void drawCircle(int centerX, int centerY, int radius, boolean filled) {

    }
}
