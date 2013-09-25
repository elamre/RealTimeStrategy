package com.rts.game.abilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.game.Assets;
import com.rts.game.entities.EntityList;
import com.rts.game.entities.EntityManager;
import com.rts.game.entities.TestBuilding;
import com.rts.game.entities.Unit;
import com.rts.game.gameplay.Camera;
import com.rts.game.gameplay.World;
import com.rts.game.hud.AbilityButton;
import com.rts.game.hud.BuildingGhost;
import com.rts.networking.mutual.packets.EntityCreation;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/22/13
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Build extends TargetedAbility {
    /** If the building ghost has to be displayed or not (should only be displayed when ability active ) */
    private boolean showGhost = false;
    /** The building host for the building. TODO make static (would be a waste if we draw this > 1 time */
    private BuildingGhost ghost;
    /** The area which this building occupies TODO load from a list */
    private boolean[][] requiredSpace = new boolean[][]{
            {true, true},
            {true, true}
    };
    /** This building space will be acquired to the building. And will also be used to check if it's possible to build */
    private BuildingSpace buildingSpace;
    /** The building that will be built */
    private Unit temporaryUnit;

    /**
     * Constructor
     *
     * @param owner the owner of the ability
     */
    public Build(Unit owner) {
        super(owner, Input.Keys.B, new AbilityButton(Assets.getAssets().getSprite("build_button"), -1));
        ghost = new BuildingGhost();
        ghost.changeEntity(EntityList.getEntityType(new TestBuilding()));
        temporaryUnit = new TestBuilding(ghost.position);
        buildingSpace = new BuildingSpace(temporaryUnit);
        buildingSpace.loadSpace(requiredSpace);
    }

    /**
     * This function changes the building to be built
     *
     * @param entityType the building type
     */
    public void switchEntity(int entityType) {
        ghost.changeEntity(entityType);
        try {
            temporaryUnit = (Unit) EntityList.getEntity(entityType).getConstructor(EntityCreation.class).newInstance(ghost.position);
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //TODO load the requiredSpace
        buildingSpace.loadSpace(requiredSpace);
    }

    /**
     * Implement this function for the actual action to be executed
     *
     * @param x the x position the ability is called on
     * @param y the y position the ability is called on
     */
    @Override
    public void action_1(float x, float y) {
        if (buildingSpace.isCreatable()) {
            buildingSpace.create();
            temporaryUnit.abilities.add(buildingSpace);
            EntityManager.addEntity(temporaryUnit);
        } else {
            //TODO play some SOUNDDD
        }
    }

    /**
     * Update function for anything ability related
     *
     * @param deltaT the time that has passed since the previous update
     */
    @Override
    public void update_1(float deltaT) {
        showGhost = isActive();
        if (showGhost) {
            ghost.update(deltaT);
            ghost.setScale(requiredSpace.length, requiredSpace[0].length);
            temporaryUnit.setX(ghost.position.x);
            temporaryUnit.setY(ghost.position.y);
            if (buildingSpace.isCreatable()) {
                ghost.setPossible();
            } else {
                ghost.setUnable();
            }
        }
    }

    /**
     * General draw function
     *
     * @param spriteBatch the batch to draw everything on
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        if (showGhost)
            ghost.draw(Camera.batch);
    }
}
