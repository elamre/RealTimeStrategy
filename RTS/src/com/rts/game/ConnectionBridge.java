package com.rts.game;

import com.rts.game.entities.Entity;
import com.rts.game.entities.TestEntity;
import com.rts.networking.client.Client;
import com.rts.networking.packets.Packet;
import com.rts.networking.packets.system.EntityCreationPacket;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 3:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionBridge {
    Client client;
    private EntityManager entityManager;

    public ConnectionBridge() {
        client = new Client();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void addEntity(Entity entity) {
        client.sendEntityRequest(entity);
    }

    public void connect(String ip, int port, ClientEventListener clientEventListener) {
        try {
            client.connect(ip, port);
        } catch (IOException e) {
            clientEventListener.hostNotFound(ip, port);
            return;
        }
        clientEventListener.connected();
    }

    public void update() {
        Packet packet = client.getPacket();
        if (packet instanceof EntityCreationPacket) {
            Entity entity = new TestEntity((EntityCreationPacket) packet);
            entityManager.createEntity(entity);
        }
    }
}








