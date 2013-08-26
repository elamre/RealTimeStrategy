package com.rts.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.rts.game.multiplayer.ConnectionBridge;
import com.rts.game.gameplay.Camera;
import com.rts.networking.packets.Packet;
import com.rts.networking.packets.game.MoveEntityPacket;

import java.util.ArrayList;
import java.util.HashMap;
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
     * The entity map to be used for all standard Entities in-game.
     */
    public HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>(256);
    /**/
    private ArrayList<Packet> packetsToSend = new ArrayList<Packet>();
    /**
     * The list containing all Entities from the network be added.
     */
    //private static ArrayList<EntityCreationPacket> addNetworkList = new ArrayList<EntityCreationPacket>(16);
    private boolean oPressed = false;
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
        entity.setDebug(true);
        addList.add(entity);
    }

    /**
     * Run the update function for all Entities, and do basic entity management.
     */
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.O) && !oPressed) {
            TestEntity testEntity = new TestEntity((int) Camera.getRealWorldX(), (int) Camera.getRealWorldY());
            addEntity(testEntity);
            oPressed = true;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.O)) {
            oPressed = false;
        }

        addEntities();
        removeEntities();

        MoveEntityPacket moveEntityPacket;
        for (Map.Entry<Integer, Entity> entry : entities.entrySet()) {
            Entity entity = entry.getValue();
            entity.update(delta);
            if ((moveEntityPacket = entity.getMovePacket()) != null) {
                packetsToSend.add(moveEntityPacket);
            }
        }
        for (int i = 0, l = packetsToSend.size(); i < l; i++) {
            connectionBridge.sendMovement((MoveEntityPacket) packetsToSend.get(i));
        }
        packetsToSend.clear();
    }

    public Entity getEntity(int id) {
        return entities.get(id);
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
        removeListIds.add(id);
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

    public void draw() {
        Color color = Color.BLUE;

        for (Map.Entry<Integer, Entity> entry : entities.entrySet()) {
            entry.getValue().draw(Camera.batch);
            //entry.getValue().bounds.debugShape();
        }
    }

    /**
     * Removes all unwanted Entities. Two lists are used.
     */
    private void removeEntities() {

        //We use two lists- one with IDs, and one with the Entities. This is only to make it easier to delete an object.

        for (Entity aRemoveList : removeList) {
            entities.remove(new Integer(aRemoveList.getId()));
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
        for (Entity anAddList : addList) {
            entities.put(new Integer(anAddList.getId()), anAddList);
        }
        addList.clear();
    }

}
