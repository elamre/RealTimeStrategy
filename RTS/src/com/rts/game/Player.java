package com.rts.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/12/13
 * Time: 11:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player {

    public HashMap<Integer, Entity> ownedEntities = new HashMap<Integer, Entity>(256);
    public ArrayList<Entity> currentSelection = new ArrayList<Entity>(64);
    public Entity currentSelect;
    public String name;
    Camera cam;
    boolean runningSelection = false;
    float[] selectionStart = new float[]{0, 0};
    float[] selectionEnd = new float[]{0, 0};
    BoundingShape selectBounds;

    public void create() {
        cam = new Camera();
        cam.create();
        selectBounds = new BoundingShape(1, 1, 1, 1);
    }

    public void update(float delta, EntityManager ents) {
        cam.update(delta);
        if ((Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !cam.isInHUD()) || runningSelection) {
            selection(ents);
        }
    }

    //TODO: Debug all selection and bounding box problems
    private void selection(EntityManager entities) {


        if (!runningSelection) {
            selectionStart = cam.getRealWorldPosition();
        }

        runningSelection = true;

        selectionEnd = cam.getRealWorldPosition();

        selectBounds = new BoundingShape(selectionStart, selectionEnd);


        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            currentSelection.clear();
            runningSelection = false;
            currentSelection.addAll(selectBounds.findInContainer(entities));
            System.out.println(currentSelection.size());
        }


    }

    public void cameraUpdates() {
        cam.update(Gdx.graphics.getDeltaTime());
    }

    public void draw() {
        ShapeRenderer box = new ShapeRenderer();

        box.setProjectionMatrix(cam.getCamera().combined);

        box.begin(ShapeRenderer.ShapeType.FilledRectangle);
        box.setColor(1, 0, 1, 0.5f);
        box.filledRect(selectionStart[0], selectionStart[1], 0.01f, .01f);
        box.filledRect(selectionEnd[0], selectionEnd[1], 0.01f, .01f);
        box.filledRect(selectionStart[0], selectionEnd[1], 0.01f, .01f);
        box.filledRect(selectionEnd[0], selectionStart[1], 0.01f, .01f);
        box.filledRect(selectBounds.x, selectBounds.y, 0.01f, .01f);
        box.end();
    }


}