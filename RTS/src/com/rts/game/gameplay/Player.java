package com.rts.game.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.rts.game.Assets;
import com.rts.game.entities.Entity;
import com.rts.game.entities.EntityManager;
import com.rts.game.entities.MovingUnit;
import com.rts.game.entities.SelectableUnit;
import com.rts.game.hud.HUD;
import com.rts.game.pathfinding.Node;
import com.rts.game.pathfinding.PathfindingDebugger;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/12/13
 * Time: 11:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player {
    static boolean lastPressedT = false;
    public ArrayList<Entity> currentSelection = new ArrayList<Entity>(64);
    public String name;
    Polygon polygon;
    boolean runningSelection = false;
    BuildingButtons buildingButtons;
    private HUD hud;
    private Sprite hudBuildBar;
    private boolean rightPressed = false;
    private Vector2 selectionStart;
    private Vector2 selectionEnd;

    public void create() {
        hud = new HUD();
        selectionStart = new Vector2(0, 0);
        selectionEnd = new Vector2(0, 0);
        hudBuildBar = Assets.getAssets().getSprite("UI/build_hud");
        hudBuildBar.flip(false, true);

        hudBuildBar.setPosition(0, Gdx.graphics.getHeight() - hudBuildBar.getHeight());
        buildingButtons = new BuildingButtons(0, (int) (Gdx.graphics.getHeight() - hudBuildBar.getHeight()));

    }

    public void update(float deltaT, EntityManager entityManager) {
        hud.update();
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
        for (int x = (int) selectionStart.x; x <= (int) selectionEnd.x; x++) {
            for (int y = (int) selectionStart.y; y <= (int) selectionEnd.y; y++) {
                Node n = World.nodeAt(x, y);
                if (n != null) {
                    n.debug();
                    if (n.standing != null && !currentSelection.contains(n.standing)) {
                        currentSelection.add(n.standing);
                        ((SelectableUnit) n.standing).setSelected(true);
                    }
                }
            }
        }
    }

    private void moveSelection() {
        for (int i = 0, l = currentSelection.size(); i < l; i++) {
            if (currentSelection.get(i) instanceof SelectableUnit) {
                ((MovingUnit) currentSelection.get(i)).setDestination((int) Camera.getRealWorldX(), (int) Camera.getRealWorldY());
            }
        }
    }

    public void draw() {
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

        Camera.makeHUDBatch();
        //hudBuildBar.draw(Camera.batch);
        //buildingButtons.draw(Camera.batch);
        Camera.makeWorldBatch();
        hud.draw();
    }

    class Resources {
        int food, wood, gold, stone;

        public void draw(SpriteBatch spriteBatch) {

        }
    }

    class BuildingButtons {
        private ArrayList<BuildingButton> buildingButtons = new ArrayList<BuildingButton>();

        /**
         * Constructor for the buildingbuttons initialisation. Position should be relative to that of the build bar
         *
         * @param x the x the buildbar is positioned at
         * @param y the y the buildbar is positioned at
         */
        BuildingButtons(int x, int y) {
            buildingButtons.add(new BuildingButton(1, Assets.getAssets().getSprite("UI/house_button"), x + 10, y + 10, "House"));
        }

        public void draw(SpriteBatch spriteBatch) {
            for (int i = 0, l = buildingButtons.size(); i < l; i++) {
                buildingButtons.get(i).draw(spriteBatch);
            }
        }

        public int getEntityType(int x, int y) {
            //if can build
            return -1;
        }

        class BuildingButton {
            int entityType = 0;
            Sprite sprite;
            String name;

            BuildingButton(int entityType, Sprite sprite, int x, int y, String name) {
                this.entityType = entityType;
                this.sprite = sprite;
                sprite.flip(false, true);
                this.name = name;
                sprite.setPosition(x, y);
            }

            public void draw(SpriteBatch spriteBatch) {
                sprite.draw(spriteBatch);
            }
        }
    }
}
