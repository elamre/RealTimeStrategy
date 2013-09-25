package com.rts.game.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.rts.game.abilities.Ability;
import com.rts.game.entities.*;
import com.rts.game.hud.HUD;
import com.rts.game.pathfinding.Node;
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
    //Temporarily disables the selection for one click
    public static boolean preserveSelection;
    public static String name;
    //  public UnitSelection selection;
    private SelectionManager selectionManager;
    private HUD hud;
    private boolean runningSelection = false;
    private boolean rightPressed = false;
    private Vector2 selectionStart;
    private Vector2 selectionEnd;

    public void cancel() {
        hud.getBuildingHUD().removeAllAbilityButtons();
        selectionManager.clearCurrentSelection();
        //selection.clear();
    }

    public void create() {
        hud = new HUD();
        selectionStart = new Vector2(0, 0);
        selectionEnd = new Vector2(0, 0);
        // selection = new UnitSelection();
        selectionManager = new SelectionManager();
    }

    public void update(float deltaT, EntityManager entityManager) {
        hud.update();
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !runningSelection && !Cursor.abilityRequested) {
            if (!hud.isOnHud((int) Cursor.x, (int) Cursor.y)) {
                //   selection.disableCurrent();
                //  selection.clear();
                cancel();
                selectionStart.set(Camera.getRealWorldX(), Camera.getRealWorldY());
                runningSelection = true;
            }
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
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            for (int i = 0; i < 10; i++) {
                if (Gdx.input.isKeyPressed(i + Input.Keys.NUM_0)) {
                    Logger.getInstance().debug("new group with: " + selectionManager.getCurrentSelection().size() + " entities.");
                    selectionManager.registerGroup(i + Input.Keys.NUM_0, selectionManager.getCurrentSelection());
                }
            }
        } else if (!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            for (int i = 0; i < 10; i++) {
                if (Gdx.input.isKeyPressed(i + Input.Keys.NUM_0)) {
                    cancel();
                    selectionManager.setCurrentSelection(selectionManager.getGroup(i + Input.Keys.NUM_0));
                }
            }
        }
    }

    private void checkSelection(EntityManager entityManager) {
        hud.getBuildingHUD().removeAllAbilityButtons();
        for (int x = (int) selectionStart.x; x <= (int) selectionEnd.x; x++) {
            for (int y = (int) selectionStart.y; y <= (int) selectionEnd.y; y++) {
                Node n = World.nodeAt(x, y);
                if (n != null) {
                    Entity entity = n.standing;
                    if (entity instanceof SelectableUnit) {
                        if (!selectionManager.currentSelectionContains(entity)) {
                            selectionManager.addCurrentSelection(entity);
                            if (entity instanceof Unit) {
                                ArrayList<Ability> abilities;
                                abilities = ((Unit) entity).getAbilities();
                                if (abilities != null) {
                                    for (int i = 0; i < abilities.size(); i++) {
                                        if (abilities.get(i).getAbilityButton() != null) {
                                            hud.getBuildingHUD().registerAbilityButton(abilities.get(i).getAbilityButton(), abilities.get(i).getAbilityButton().getPreferredPlace());
                                            System.out.println("Ability type: " + abilities.get(i).getAbilityType());
                                        }
                                    }
                                }
                                ((SelectableUnit) entity).setSelected(true);
                            }
                        }
                    }
                }
//                System.out.println("selected amount: " + selection.getCurrentSelection().size());

                if (selectionStart.x == selectionEnd.x && selectionStart.y == selectionEnd.y) {
                    System.out.println("Unit at this spot is " + World.nodeAt(selectionEnd.x, selectionEnd.y).standing);
                }
            }
        }
    }

    private void moveSelection() {
        //TODO selection.massWalkTo((int) Camera.getRealWorldX(), (int) Camera.getRealWorldY());
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
