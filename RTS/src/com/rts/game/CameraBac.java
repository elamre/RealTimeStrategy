package com.rts.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/12/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class CameraBac {
    private static CameraBac camera;
    private OrthographicCamera orthographicCamera;
    private int w;
    private int h;
    private float x = 0f;
    private float y = 0f;
    private float cameraMoveSensitivityMouse = 0;
    private float cameraMoveSensitivityKeys = 0;
    private float zoom = 0.1f;
    private float zoomSensitivity = 0.01f;

    public CameraBac() {
        create();
    }

    public static CameraBac getCamera() {
        if (camera == null)
            camera = new CameraBac();
        return camera;
    }

/*    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }*/

    public OrthographicCamera getOrthographicCamera() {
        return orthographicCamera;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    private void create() {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        orthographicCamera = new OrthographicCamera(w, h);
        orthographicCamera.setToOrtho(true);
    }

    public void update(float delta) {

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();


        cameraMoveSensitivityMouse = w / h * 200;
        cameraMoveSensitivityKeys = w / h * 2;

        handleInput(delta);

        orthographicCamera.position.set(new float[]{x, y, 0});
        orthographicCamera.zoom = zoom;
        orthographicCamera.update();

    }

    private void handleInput(float delta) {
        if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
            x -= (Gdx.input.getDeltaX() / (float) w) * cameraMoveSensitivityMouse * zoom;
            y += (Gdx.input.getDeltaY() / (float) h) * cameraMoveSensitivityMouse * zoom;
        } else if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            System.out.println("On secreen: " + Gdx.input.getX() + " : " + Gdx.input.getY());
            System.out.println("Rea world:: " + (Gdx.input.getX() + x) / 10 + " : " + (Gdx.input.getY() + y) / 10);


        }


        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y -= cameraMoveSensitivityKeys * zoom;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y += cameraMoveSensitivityKeys * zoom;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= cameraMoveSensitivityKeys * zoom;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += cameraMoveSensitivityKeys * zoom;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS) || Gdx.input.isKeyPressed(Input.Keys.Q)) {
            zoom *= (1 + zoomSensitivity);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS) || Gdx.input.isKeyPressed(Input.Keys.E)) {
            zoom *= (1 - zoomSensitivity);
        }

    }

    public float[] getRealWorldPosition() {
        float cx = (x) + Gdx.input.getX() * zoom;
        float cy = (y) + Gdx.input.getY() * zoom;
        float[] pos = new float[]{cx, cy};
        return pos;
        //TODO: Fix mouse coordinates
    }

    public boolean isInHUD() {
        return false;
    }
}
