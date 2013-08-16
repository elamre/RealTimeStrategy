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
public class Camera {

    private OrthographicCamera camera;
    private int w;
    private int h;
    private float x = 0f;
    private float y = 0f;
    private float cameraMoveSensitivityMouse = .6f;
    private float cameraMoveSensitivityKeys = .04f;
    private float zoom = 10;
    private float zoomSensitivity = 0.01f;

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
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

    public void create() {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(1, h / (float) w);
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
            x += (Gdx.input.getDeltaX() / (float) w) * cameraMoveSensitivityMouse * zoom;
            y += (Gdx.input.getDeltaY() / (float) h) * cameraMoveSensitivityMouse * zoom;
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
        float cx = vec.x;
        cx += ((float) Gdx.input.getX() / w ) * zoom;
        float cy = vec.y;
        cy -= ((float) Gdx.input.getY() / w) * zoom;
        float[] pos = new float[]{cx, cy};
        return pos;
        //TODO: Fix mouse coordinates
    }

    public boolean isInHUD() {
        return false;
    }
}
