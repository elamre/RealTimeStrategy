package com.rts.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rts.game.gameplay.*;
import com.rts.game.gameplay.Cursor;
import com.rts.util.Logger;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/7/13
 * Time: 8:44 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Button {
    int x, y;
    Sprite sprite;
    String text;
    boolean mouseOver;
    boolean enabled = true;
    boolean pressed = false;
    Rectangle hitBox;

    public Button(int x, int y, Sprite sprite, String text, boolean mouseOver) {
        this.mouseOver = mouseOver;
        this.sprite = sprite;
        this.text = text;
        this.x = x;
        this.y = y;
        sprite.setPosition(x, y);
        sprite.flip(true,true);
        hitBox = new Rectangle(x, y, (int) sprite.getWidth(), (int) sprite.getHeight());
    }

    public void update() {
        if (enabled) {
            Rectangle mouseHitBox = new Rectangle((int) Cursor.x, (int) Cursor.y, 1, 1);
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                if (mouseHitBox.intersects(hitBox)) {
                    buttonHold(this);
                    if (!pressed) {
                        buttonPressed(this);
                        Logger.getInstance().debug("Button hit");
                        pressed = true;
                    }
                } else {
                    if (pressed) {
                        pressed = false;
                        buttonReleased(this);
                    }
                }
            }
        }
    }

    public void draw() {
        if (enabled) {
            if (mouseOver) {
                Rectangle mouseHitBox = new Rectangle((int) Cursor.x, (int) Cursor.y, 1, 1);
                if (mouseHitBox.intersects(hitBox)) {
                    mouseOver(this);
                }
            }
            sprite.draw(Camera.batch);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public abstract void mouseOver(Button button);

    public abstract void buttonPressed(Button button);

    public abstract void buttonReleased(Button button);

    public abstract void buttonHold(Button button);
}
