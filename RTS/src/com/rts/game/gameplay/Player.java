package com.rts.game.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.rts.game.entities.Entity;
import com.rts.game.entities.EntityManager;
import com.rts.game.entities.SelectableUnit;
import com.rts.game.pathfinding.PathfindingDebugger;
import com.rts.util.Logger;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/12/13
 * Time: 11:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player {
    public ArrayList<Entity> currentSelection = new ArrayList<Entity>(64);
    public Entity currentSelect;
    public String name;
    Polygon polygon;
    boolean runningSelection = false;
    private boolean rightPressed = false;
    private Vector2 selectionStart;
    private Vector2 selectionEnd;

    static boolean lastPressedT = false;


    public void create() {
        selectionStart = new Vector2(0, 0);
        selectionEnd = new Vector2(0, 0);
    }

    public void update(float deltaT, EntityManager entityManager) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !runningSelection && !Cursor.abilityRequested) {
            for (int i = 0, l = currentSelection.size(); i < l; i++) {
                ((SelectableUnit) currentSelection.get(i)).setSelected(false);
            }
            currentSelection.clear();
            selectionStart.set(Camera.getRealWorldX(), Camera.getRealWorldY());
            runningSelection = true;
        }
        if (runningSelection) {
            selectionEnd.set(Camera.getRealWorldX(), Camera.getRealWorldY());
        }
        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (runningSelection) {
                runningSelection = false;
                checkSelection(entityManager);
            }
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            if (!rightPressed) {
                moveSelection();
                rightPressed = true;
            }
        } else {
            rightPressed = false;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.T) && !lastPressedT) {
            PathfindingDebugger.set((int) Camera.getRealWorldX(), (int) Camera.getRealWorldY());
            lastPressedT = true;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.T)) {
            lastPressedT = false;
        }


    }

    private void checkSelection(EntityManager entityManager) {
        Rectangle selectionRectangle = new Rectangle((selectionStart.x < selectionEnd.x) ? selectionStart.x : selectionEnd.x,
                (selectionStart.y < selectionEnd.y) ? selectionStart.y : selectionEnd.y,
                Math.abs(selectionStart.x - selectionEnd.x), Math.abs(selectionStart.y - selectionEnd.y));
        //Rectangle selectionRectangle = new Rectangle(selectionStart.x, selectionStart.y, selectionEnd.x - selectionStart.x, selectionEnd.y - selectionStart.y);
        for (Entity unit : entityManager.entities.values()) {
            if (unit instanceof SelectableUnit) {
                if (selectionRectangle.overlaps(unit.getHitBox())) {
                    currentSelection.add(unit);
                    Logger.getInstance().debug("unit: " + unit.toString() + " in area: " + selectionRectangle.toString());
                    ((SelectableUnit) unit).setSelected(true);
                }
            }
        }
    }

    private void moveSelection() {
        for (int i = 0, l = currentSelection.size(); i < l; i++) {
            if (currentSelection.get(i) instanceof SelectableUnit) {
                ((SelectableUnit) currentSelection.get(i)).setDestination((int) Camera.getRealWorldX(), (int) Camera.getRealWorldY());
            }
        }
    }

    public void draw() {
        drawSelectionBox();
    }

    private void drawSelectionBox() {
        if (runningSelection) {

            Camera.batch.end();

            ShapeRenderer box = new ShapeRenderer();
            box.setProjectionMatrix(Camera.getOrthographicCamera().combined);
            box.begin(ShapeRenderer.ShapeType.FilledRectangle);

            box.setColor(1, 0, 1, 0.1f);
            float lx = selectionEnd.x;
            float ly = selectionEnd.y;
            if (selectionStart.x < selectionEnd.x) {
                lx = selectionStart.x;
            }
            if (selectionStart.y < selectionEnd.y) {
                ly = selectionStart.y;
            }
            box.filledRect(lx, ly, Math.abs(selectionStart.x - selectionEnd.x), Math.abs(selectionStart.y - selectionEnd.y));
            box.end();

            Camera.batch.begin();


        }
    }


}
