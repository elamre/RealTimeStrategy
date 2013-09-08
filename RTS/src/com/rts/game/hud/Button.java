package com.rts.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rts.util.Logger;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/7/13
 * Time: 8:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class Button {
    int x, y;
    Sprite sprite;
    String text;
    boolean mouseOver;
    boolean pressed = false;
    Rectangle hitBox;
    ButtonAble buttonAble;

    public Button(int x, int y, Sprite sprite, String text, ButtonAble buttonAble, boolean mouseOver) {
        this.buttonAble = buttonAble;
        this.mouseOver = mouseOver;
        this.sprite = sprite;
        this.text = text;
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(x, y, (int) sprite.getWidth(), (int) sprite.getHeight());
    }

    public void update() {
        Rectangle mouseHitBox = new Rectangle(Gdx.input.getX(), Gdx.input.getY(), 1, 1);
        if (mouseOver) {
            buttonAble.mouseOver(this);
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (mouseHitBox.intersects(hitBox)) {
                if (!pressed) {
                    buttonAble.buttonPressed(this);
                    Logger.getInstance().debug("Button hit");
                    pressed = true;
                }
            } else {
                if (pressed) {
                    pressed = false;
                    buttonAble.buttonReleased(this);
                }
            }
        }
    }
}
