package com.rts.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/12/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
class Camera {
    public static SpriteBatch batch;
    private static OrthographicCamera orthographicCamera;
    private static OrthographicCamera hudOrthographicCamera;
    private static int w;
    private static int h;
    private static float x = 0f;
    private static float y = 0f;
    private static float cameraMoveSensitivityMouse = 1;
    private static float cameraMoveSensitivityKeys = 1;
    private static float zoom = 1f;
    private static float zoomSensitivity = 0.01f;

    public static void draw() {
    }

    public static OrthographicCamera getOrthographicCamera() {
        return orthographicCamera;
    }

    public static void makeHUDBatch() {
        batch.end();
        batch.setProjectionMatrix(hudOrthographicCamera.combined);
        batch.begin();
    }

    public static void makeWorldBatch() {
        batch.end();
        batch.setProjectionMatrix(getOrthographicCamera().combined);
        batch.begin();
    }

    public static void create() {

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        orthographicCamera = new OrthographicCamera(1, h / (float) w);
        orthographicCamera.setToOrtho(false);

        hudOrthographicCamera = new OrthographicCamera(1, h / (float) w);
        hudOrthographicCamera.setToOrtho(false);

        batch = new SpriteBatch();
    }

    public static void update(float delta) {

        if (w != Gdx.graphics.getWidth() || h != Gdx.graphics.getHeight()) {
            create();
        }


        orthographicCamera.update();
        hudOrthographicCamera.update();

        batch.setProjectionMatrix(getOrthographicCamera().combined);

        batch.begin();

        cameraMoveSensitivityMouse = w / h * 200;
        cameraMoveSensitivityKeys = w / h * 2;

        handleInput(delta);

        orthographicCamera.position.set(new float[]{x, y, 0});
        orthographicCamera.zoom = zoom;

        // System.out.println(x + ", " + y);


    }

    public static void finishBatches() {
        batch.end();
    }

    private static void handleInput(float delta) {
        if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
            x -= (Gdx.input.getDeltaX() / (float) w) * cameraMoveSensitivityMouse * zoom;
            y -= (Gdx.input.getDeltaY() / (float) h) * cameraMoveSensitivityMouse * zoom;
        } else if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            System.out.println("On screen: " + Gdx.input.getX() + " : " + Gdx.input.getY());
            System.out.println("Rea world:: " + (Gdx.input.getX() + x) / 10 + " : " + (Gdx.input.getY() + y) / 10);


        }


        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += cameraMoveSensitivityKeys * zoom;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= cameraMoveSensitivityKeys * zoom;
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

    public static float[] getRealWorldPosition() {
        return new float[]{getRealWorldX(), getRealWorldY()};
    }

    public static float getRealWorldX() {
        float cx = (x);
        cx = cx + Cursor.x * zoom - (w / 2) * zoom;
        return cx;
    }

    public static float getRealWorldY() {
        float cy = (y);
        cy = cy + Cursor.y * zoom - (h / 2) * zoom;
        return cy;
    }

    public static boolean isInHUD() {
        return false;
    }
}
