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
public class Camera {
    public SpriteBatch batch;
    private OrthographicCamera orthographicCamera;
    private OrthographicCamera hudOrthographicCamera;
    private int w;
    private int h;
    private float x = 0f;
    private float y = 0f;
    private float cameraMoveSensitivityMouse = 1;
    private float cameraMoveSensitivityKeys = 1;
    private float zoom = 1f;
    private float zoomSensitivity = 0.01f;
    private Cursor cursor;


    public Camera() {
        create();
    }

    public void draw() {
        makeHUDBatch();
        cursor.draw(batch, this);
    }

    public OrthographicCamera getOrthographicCamera() {
        return orthographicCamera;
    }

    public int getW() {
        return w;
    }

    private void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    private void setH(int h) {
        this.h = h;
    }

    public void makeHUDBatch() {
        batch.end();
        batch.setProjectionMatrix(hudOrthographicCamera.combined);
        batch.begin();
    }


    public void makeWorldBatch() {
        batch.end();
        batch.setProjectionMatrix(getOrthographicCamera().combined);
        batch.begin();
    }

    private void create() {

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        orthographicCamera = new OrthographicCamera(1, h / (float) w);
        orthographicCamera.setToOrtho(true);

        hudOrthographicCamera = new OrthographicCamera(1, h / (float) w);
        hudOrthographicCamera.setToOrtho(true);

        cursor = new Cursor();
        cursor.create();

        batch = new SpriteBatch();
    }

    public void update(float delta) {

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

        System.out.println(x + ", " + y);

        cursor.update(delta);

    }

    public void finishBatches() {
        batch.end();
    }

    private void handleInput(float delta) {
        if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
            x -= (Gdx.input.getDeltaX() / (float) w) * cameraMoveSensitivityMouse * zoom;
            y -= (Gdx.input.getDeltaY() / (float) h) * cameraMoveSensitivityMouse * zoom;
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
        float cx = (x);
        cx = cx + cursor.x * zoom - (w / 2) * zoom;

        float cy = (y);
        cy = cy + cursor.y * zoom - (h / 2) * zoom;

        float[] pos = new float[]{cx, cy};
        return pos;
        //TODO: Fix mouse coordinates
    }

    public boolean isInHUD() {
        return false;
    }
}
