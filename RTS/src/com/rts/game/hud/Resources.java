package com.rts.game.hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rts.game.Assets;
import com.rts.game.gameplay.Camera;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/7/13
 * Time: 8:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Resources {
    private Sprite resourceBar;
    private static Resource wood;
    private static Resource food;
    private static Resource gold;
    private static Resource iron;
    private static Resource stone;
    private BitmapFont bitmapFont;
    private ArrayList<Resource> resources = new ArrayList<Resource>();

    Resources() {
        resourceBar = Assets.getAssets().getSprite("resources_hud");
        resourceBar.flip(false, true);
        bitmapFont = new BitmapFont(true);
        wood = new Resource("Wood", 70, 10, 100);
        food = new Resource("Wood", 160, 10, 100);
        stone = new Resource("Wood", 250, 10, 100);
        gold = new Resource("Wood", 340, 10, 100);
        iron = new Resource("Wood", 430, 10, 100);
        resources.add(wood);
        resources.add(food);
        resources.add(gold);
        resources.add(iron);
        resources.add(stone);
    }

    public static int getWood() {
        return wood.getValue();
    }

    public static void setWood(int value) {
        wood.setValue(value);
    }

    public static int getFood() {
        return food.getValue();
    }

    public static void setFood(int value) {
        food.setValue(value);
    }

    public static int getGold() {
        return gold.getValue();
    }

    public static void setGold(int value) {
        gold.setValue(value);
    }

    public static int getStone() {
        return stone.getValue();
    }

    public static void setStone(int value) {
        stone.setValue(value);
    }

    public static int getIron() {
        return iron.getValue();
    }

    public static void setIron(int value) {
        iron.setValue(value);
    }

    public void draw() {
        resourceBar.draw(Camera.batch);
        for (int i = 0, l = resources.size(); i < l; i++) {
            resources.get(i).draw(bitmapFont);
        }
    }

    class Resource {
        private int value;
        private int x, y;

        public Resource(String name, int x, int y, int initialValue) {
            this.x = x;
            this.y = y;
            this.value = initialValue;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public void draw(BitmapFont bitmapFont) {
            bitmapFont.draw(Camera.batch, "" + value, x, y);

        }
    }
}
