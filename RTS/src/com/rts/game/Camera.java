package com.rts.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/12/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Camera {

    private OrthographicCamera camera;
    private float w;
    private float h;
    private float x = 0;
    private float y = 0;
    private float cameraMoveSensitivityMouse = 1.0f;
    private float cameraMoveSensitivityKeys = .004f;
    private float zoom = 1;
    private float zoomSensitivity = 0.01f;

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public void create() {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(1, h / w);
    }

    public void update(float delta) {

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        handleInput(delta);

        camera.position.set(new float[]{-x, y, 0});
        camera.zoom = zoom;
        camera.update();

    }

    private void handleInput(float delta) {
        if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
            x += (Gdx.input.getDeltaX() / w) * cameraMoveSensitivityMouse * zoom;
            y += (Gdx.input.getDeltaY() / h) * cameraMoveSensitivityMouse * zoom;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += cameraMoveSensitivityKeys * zoom;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= cameraMoveSensitivityKeys * zoom;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x += cameraMoveSensitivityKeys * zoom;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x -= cameraMoveSensitivityKeys * zoom;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS) || Gdx.input.isKeyPressed(Input.Keys.Q)) {
            zoom *= (1 + zoomSensitivity);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS) || Gdx.input.isKeyPressed(Input.Keys.E)) {
            zoom *= (1 - zoomSensitivity);
        }

    }

    public float[] getRealWorldPosition() {
        Vector3 vec = new Vector3(x, y, 0);
        camera.unproject(vec);
        float[] pos = new float[]{vec.x, vec.y};
        return pos;
        //TODO: Fix mouse coordinates
    }

    public boolean isInHUD() {
        return false;
    }
}
