package com.rts.game.multiplayer;

import com.esotericsoftware.kryonet.Listener;
import com.rts.game.entities.*;
import com.rts.networking.client.ClientHandler;
import com.rts.networking.client.ClientListener;
import com.rts.networking.mutual.packets.EntityPosChange;
import com.rts.networking.mutual.packets.EntityRequest;
import com.rts.util.Logger;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 3:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionBridge {
    ClientHandler client;
    private EntityManager entityManager;

    public ConnectionBridge() {
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.client = new ClientHandler(new ClientListener(entityManager));
    }

    public void addEntity(Entity entity) {
        EntityRequest entityRequest = new EntityRequest();
        entityRequest.building = false;
        entityRequest.x = (int) entity.getX();
        entityRequest.y = (int) entity.getY();
        entityRequest.owner = client.getId();
        client.writeMessage(entityRequest);
    }

    public void sendMovement(EntityPosChange entityPosChange) {
        Logger.getInstance().debug("Sending movement!");
        client.writeMessage((entityPosChange));
    }

    public void connect(String ip, int port, ClientEventListener clientEventListener) {
        if (client != null) {
            client.connect(ip, port);
            if (client.getId() == 0) {
                clientEventListener.hostNotFound(ip, port);
                return;
            }
            clientEventListener.connected();
        } else {
            Logger.getInstance().warn("Client not initialized. ");
        }
    }

    public void update() {
/*        while ((packet = client.getPacket()) != null) {
            if (packet instanceof EntityCreationPacket) {
                Entity entity = null;
                if (((EntityCreationPacket) packet).getEntityType() == EntityList.UNIT_TEST_1) {
                    entity = new TestEntity((EntityCreationPacket) packet);
                } else if (((EntityCreationPacket) packet).getEntityType() == EntityList.BUILDING_TEST) {
                    entity = new TestBuilding((EntityCreationPacket) packet);
                }
                if (entity != null)
                    entityManager.createEntity(entity);
                else
                    Logger.getInstance().debug("Trying to add non existent unit");
            } else if (packet instanceof MoveEntityPacket) {
                Logger.getInstance().debug("Receiving movement!");
                ((MovingUnit) entityManager.getEntity(((MoveEntityPacket) packet).getEntityId())).moveEntity((MoveEntityPacket) packet);
            }
        }*/
    }
}








