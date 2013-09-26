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
    /** The hitbox for the mouse */
    Rectangle mouseHitBox = new Rectangle((int) Cursor.x, (int) Cursor.y, 1, 1);
    /** The position of the button */
    int x, y;
    /** The sprite of the button */
    Sprite sprite;
    /** Some text to show TODO figure out when and why */
    String text;
    /** If the mouse is hovering over the button */
    boolean mouseOver;
    /** If the button is enabled or not */
    boolean enabled = true;
    /** This is a trigger for the button. Makes sure that the button only get pressed once on mousebutton */
    boolean pressed = false;
    /** The hit box of the button */
    Rectangle hitBox;

    /**
     * constructor for the button
     *
     * @param x         the x position
     * @param y         the y position
     * @param sprite    the sprite to draw
     * @param text      some text TODO do something with this
     * @param mouseOver if mouse over feature should be enabled or not
     */
    public Button(int x, int y, Sprite sprite, String text, boolean mouseOver) {
        this.mouseOver = mouseOver;
        this.sprite = sprite;
        this.text = text;
        this.x = x;
        this.y = y;
        this.sprite.flip(true, false);
        this.sprite.setRotation(180);
        hitBox = new Rectangle(x, y, (int) sprite.getWidth(), (int) sprite.getHeight());
    }

    /**
     * Sets the position and the hit-box of the button
     *
     * @param x new x position
     * @param y new y position
     */
    public void setPosition(int x, int y) {
        if (sprite != null) {
            sprite.setPosition(x, y);
            hitBox.setBounds(x, y, (int) sprite.getWidth(), (int) sprite.getHeight());
        } else {
            hitBox.setBounds(x, y, 24, 24);
        }
    }

    /**
     * Updates the button. If the button is enabled it will if the mouse hits the boundaries. It will also call all the abstract buttons
     * when necessary
     */
    public void update() {
        if (enabled) {
            mouseHitBox.setLocation((int) Cursor.x, (int) Cursor.y);
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

    /** Draws the button on the proper place. Also draws the mouse over TODO fix the mouseOver */
    public void draw() {
        if (enabled) {
            if (mouseOver) {
                mouseHitBox.setLocation((int) Cursor.x, (int) Cursor.y);
                if (mouseHitBox.intersects(hitBox)) {
                    mouseOver(this);
                }
            }
            sprite.draw(Camera.batch);
        }
    }

    /**
     * checks if the left mouse button is pressed and if the mouse is positioned on the button
     *
     * @return true if the button is pressed, false otherwise
     */
    public boolean isPressed() {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            mouseHitBox.setLocation((int) Cursor.x, (int) Cursor.y);
            if (mouseHitBox.intersects(hitBox)) {
                return true;
            }
        }
        return false;
    }

    /**
     * getter
     *
     * @return enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * setter
     *
     * @param enabled new enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * The abstract mouse over function. If mouseOver is true it wil fire as soon as the mouse hovers over the button
     * TODO implement
     *
     * @param button the button instance which this function is called from
     */
    public abstract void mouseOver(Button button);

    /**
     * This function will get called as soon as the button is pressed once
     *
     * @param button the button instance which this function is called from
     */
    public abstract void buttonPressed(Button button);

    /**
     * Thus function will get called as soon as the button is released when it was pressed before
     *
     * @param button the button instance which this function is called from
     */
    public abstract void buttonReleased(Button button);

    /**
     * This function will get called if the mouse is still pressed on the button
     *
     * @param button the button instance which this function is called from
     */
    public abstract void buttonHold(Button button);
}
