package com.rts.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.networking.packets.Packet;
import com.rts.networking.packets.PacketListener;
import com.rts.networking.packets.system.EntityCreationPacket;
import com.rts.util.Configuration;
import com.rts.util.Logger;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/12/13
 * Time: 1:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class EntityManager {
    /**
     * The list containing all Entities from the network be added.
     */
    //private static ArrayList<EntityCreationPacket> addNetworkList = new ArrayList<EntityCreationPacket>(16);
    /**
     * The entity map to be used for all standard Entities in-game.
     */
    public HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>(256);
    /**
     * The list containing all Entity values to be removed.
     */
    private ArrayList<Entity> removeList = new ArrayList<Entity>(16);
    /**
     * The list containing all ID values of Entities to be removed.
     */
    private ArrayList<Integer> removeListIds = new ArrayList<Integer>(16);
    /**
     * The list containing all Entities to be added.
     */
    private ArrayList<Entity> addList = new ArrayList<Entity>(16);
    /**
     * The network client to send data to
     */
    private ConnectionBridge connectionBridge;

    public EntityManager(ConnectionBridge connectionBridge) {
        this.connectionBridge = connectionBridge;
    }

    public void createEntity(Entity entity) {
        addList.add(entity);
    }

    /**
     * Run the update function for all Entities, and do basic entity management.
     */
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            EntityTest entityTest = new EntityTest(Gdx.input.getX(), Gdx.input.getY());
            //entityTest.create();
            addEntity(entityTest);
        }

        addEntities();
        removeEntities();

        for (Map.Entry<Integer, Entity> entry : entities.entrySet()) {
            entry.getValue().update(delta);
        }

    }

    /**
     * Removes an Entity.
     *
     * @param e Entity to be removed from the world.
     */
    public void remove(Entity e) {
        removeList.add(e);
    }

    /**
     * Removes an Entity.
     *
     * @param id ID of the Entity to be removed
     */
    public void remove(Integer id) {
        removeListIds.add(id);
    }

    /**
     * Removes an Entity.
     *
     * @param id ID of the Entity to be removed
     */
    public void remove(int id) {
        removeListIds.add(new Integer(id));
    }

    /**
     * Adds an Entity to the world when possible.
     *
     * @param e The Entity to be added.
     */
    public void addEntity(Entity e) {
        connectionBridge.addEntity(e);
        // addList.add(e);
    }

    public void draw(Camera cam) {
        for (Map.Entry<Integer, Entity> entry : entities.entrySet()) {
            entry.getValue().draw(cam.batch);
            entry.getValue().bounds.debugShape(cam);
        }
    }

    /**
     * Removes all unwanted Entities. Two lists are used.
     */
    private void removeEntities() {

        //We use two lists- one with IDs, and one with the Entities. This is only to make it easier to delete an object.

        for (int i = 0; i < removeList.size(); i++) {
            entities.remove(new Integer(removeList.get(i).getId()));
        }
        removeList.clear();

        for (int i = 0; i < removeList.size(); i++) {
            entities.remove(removeListIds.get(i));
        }
        removeListIds.clear();
    }

    /**
     * Adds all wanted Entities. Uses the addList List.
     */
    private void addEntities() {
        for (int i = 0; i < addList.size(); i++) {
            entities.put(new Integer(addList.get(i).getId()), addList.get(i));
        }
        addList.clear();
    }

}
