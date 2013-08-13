package com.rts.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.ArrayList;
import java.util.HashMap;

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
    float[] selectionStart;
    float[] selectionEnd;

    public void create() {
        cam = new Camera();
        cam.create();
    }

    public void update(float delta, EntityManager ents) {
        cam.update(delta);
        if ((Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !cam.isInHUD()) || runningSelection) {
            selection(ents);
            System.out.println("S: " + currentSelection.size());
        }
    }

    //TODO: Debug all selection and bounding box problems
    private void selection(EntityManager entities) {
        if (!runningSelection) {
            selectionStart = cam.getRealWorldPosition();
        }

        System.out.println(cam.getRealWorldPosition()[0]);

        runningSelection = true;

        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            runningSelection = false;

            selectionEnd = cam.getRealWorldPosition();

            System.out.println(selectionStart[0] + ", " + selectionStart[1]);
            System.out.println(selectionEnd[0] + ", " + selectionEnd[1]);


            BoundingShape bounds = new BoundingShape(selectionStart, selectionEnd);
            currentSelection.addAll(bounds.findInContainer(entities));
        }

    }

    public void cameraUpdates() {
        cam.update(Gdx.graphics.getDeltaTime());
    }


}
