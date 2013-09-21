package com.rts.game.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.rts.game.entities.EntityManager;
import com.rts.game.entities.SelectableUnit;
import com.rts.game.entities.TestEntity;
import com.rts.game.hud.BuildingHUD;
import com.rts.game.hud.HUD;
import com.rts.game.pathfinding.Node;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/12/13
 * Time: 11:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player {
    //Temporarily disables the selection for one click
    public static boolean preserveSelection;
    public String name;
    public UnitSelection selection;
    boolean runningSelection = false;
    private HUD hud;
    private boolean rightPressed = false;
    private Vector2 selectionStart;
    private Vector2 selectionEnd;

    public void create() {
        hud = new HUD();
        selectionStart = new Vector2(0, 0);
        selectionEnd = new Vector2(0, 0);

        selection = new UnitSelection();

    }

    public void update(float deltaT, EntityManager entityManager) {
        hud.update();
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !runningSelection && !Cursor.abilityRequested) {
            if (!preserveSelection) {
                selection.disableCurrent();
                selection.clear();
                selectionStart.set(Camera.getRealWorldX(), Camera.getRealWorldY());
            }
            runningSelection = true;
        }
        if (runningSelection) {
            selectionEnd.set(Camera.getRealWorldX(), Camera.getRealWorldY());
        }
        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (runningSelection) {
                runningSelection = false;
                if (!preserveSelection)
                    checkSelection(entityManager);
                preserveSelection = false;
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

    }

    private void checkSelection(EntityManager entityManager) {
        for (int x = (int) selectionStart.x; x <= (int) selectionEnd.x; x++) {
            for (int y = (int) selectionStart.y; y <= (int) selectionEnd.y; y++) {
                Node n = World.nodeAt(x, y);
                if (n != null) {
                    if (n.standing instanceof SelectableUnit)
                        if (n.standing != null && !selection.contains(n.standing)) {
                            if (n.standing instanceof TestEntity) {
                                hud.setSelection(BuildingHUD.ButtonSet.WORKER);
                            }
                            selection.add(n.standing);
                            ((SelectableUnit) n.standing).setSelected(true);
                        }
                }
            }
        }

        System.out.println(selection.currentSelection.size());

        if (selectionStart.x == selectionEnd.x && selectionStart.y == selectionEnd.y) {
            System.out.println("Unit at this spot is " + World.nodeAt(selectionEnd.x, selectionEnd.y).standing);
        }


    }

    private void moveSelection() {
        selection.massWalkTo((int) Camera.getRealWorldX(), (int) Camera.getRealWorldY());
    }

    public void draw() {
        if (!preserveSelection)
            drawSelectionBox();
        drawHUD();
    }

    private void drawSelectionBox() {
        if (runningSelection) {
            Camera.batch.end();

            Gdx.gl.glEnable(GL10.GL_BLEND);
            Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

            ShapeRenderer box = new ShapeRenderer();
            box.setProjectionMatrix(Camera.getOrthographicCamera().combined);
            box.begin(ShapeRenderer.ShapeType.FilledRectangle);

            box.setColor(0, 1, 0, 0.2f);
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

            box.begin(ShapeRenderer.ShapeType.Rectangle);
            box.setColor(0, 1, 0, 0.5f);
            box.rect(lx, ly, Math.abs(selectionStart.x - selectionEnd.x), Math.abs(selectionStart.y - selectionEnd.y));
            box.end();

            Gdx.gl.glDisable(GL10.GL_BLEND);
            Camera.batch.begin();
        }
    }

    private void drawHUD() {
        hud.draw();
    }
}
