package com.rts.game.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/12/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Camera {
    public static SpriteBatch batch;
    private static OrthographicCamera orthographicCamera;
    private static OrthographicCamera hudOrthographicCamera;
    private static int w;
    private static int h;
    private static float x = 0f;
    private static float y = 0f;
    private static float cameraMoveSensitivityMouse = 3;
    private static float cameraMoveSensitivityKeys = 1;
    private static float zoom = 1f;
    private static float zoomSensitivity = 0.02f;
    private static Rectangle viewPort;

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
        //orthographicCamera = new OrthographicCamera(w, h);
        orthographicCamera.setToOrtho(true);

        hudOrthographicCamera = new OrthographicCamera(1, h / (float) w);
        //hudOrthographicCamera = new OrthographicCamera(w, h);
        hudOrthographicCamera.setToOrtho(true);

        batch = new SpriteBatch();
        viewPort = new Rectangle((int) x, (int) y, w, h);
    }

    public static void update(float delta) {

        if (w != Gdx.graphics.getWidth() || h != Gdx.graphics.getHeight()) {
            create();
            //Redo the camera if the window size is changed
        }


        orthographicCamera.update();
        hudOrthographicCamera.update();

        batch.setProjectionMatrix(getOrthographicCamera().combined);

        batch.begin();

        cameraMoveSensitivityMouse = w / h * 200;
        cameraMoveSensitivityKeys = w / h * 2;
        //TODO: Make the sensitivity system work

        handleInput(delta);

        orthographicCamera.position.set(new float[]{x, y, 0});
        orthographicCamera.zoom = zoom;
        viewPort.set(getRealWorldCameraX(), getRealWorldCameraY(), w, h); //TODO implement width and height
    }

    public static void finishBatches() {
        batch.end();
    }

    private static void handleInput(float delta) {
        if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
            x -= (Gdx.input.getDeltaX() / (float) w) * cameraMoveSensitivityMouse * zoom;
            y -= (Gdx.input.getDeltaY() / (float) h) * cameraMoveSensitivityMouse * zoom;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y += cameraMoveSensitivityKeys * zoom;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
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
        //return Gdx.input.getX() - Gdx.graphics.getWidth() / 2 + x;
        float cx = (x);
        cx = cx + Cursor.x * zoom - (w / 2) * zoom;
        return cx;
    }

    public static float getRealWorldCameraX() {
        float cx = (x);
        cx = cx - (w / 2) * zoom;
        return cx;
    }

    public static float getRealWorldCameraY() {
        float cy = (y);
        cy = cy - (h / 2) * zoom;
        return cy;
    }

    public static float getRealWorldY() {
        //return Gdx.input.getY() - Gdx.graphics.getHeight() / 2 + y;
        float cy = (y);
        cy = cy + Cursor.y * zoom - (h / 2) * zoom;
        return cy;
    }

    public static boolean isInHUD() {
        return false;
    }

    public static int getWidth() {
        return w;
    }

    public static int getHeight() {
        return h;
    }

    public static float getY() {
        return y;
    }

    public static float getX() {
        return x;
    }

    //TODO IMPROVE
    public static boolean isInFocus(float x, float y, float width, float height) {
        Rectangle rectangle = new Rectangle(x, y, width, height);
        if (viewPort.overlaps(rectangle))
            return true;
        return false;
    }
}
