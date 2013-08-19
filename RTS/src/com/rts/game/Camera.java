package com.rts.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/19/13
 * Time: 6:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Camera {
    private static Camera camera;
    private int x, y;
    private OrthographicCamera orthographicCamera;

    Camera() {
        orthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        orthographicCamera.setToOrtho(true);
    }

    public static Camera getCamera() {
        if (camera == null)
            camera = new Camera();
        return camera;
    }

    public void update(float deltaT) {
        orthographicCamera.update();
        orthographicCamera.position.set(x, y, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += 1;
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            System.out.println("On secreen: " + Gdx.input.getX() + " : " + Gdx.input.getY());
            System.out.println("Rea world:: " + (Gdx.input.getX() + x) + " : " + (Gdx.input.getY() + y));
        }
    }

    public int getRealWorldX() {
        return x;
    }

    public int getRealWorldY() {
        return y;
    }

    public OrthographicCamera getOrthographicCamera() {
        return orthographicCamera;
    }
}
