package com.rts.game;

import com.rts.networking.client.Client;
import com.rts.networking.packets.Packet;
import com.rts.networking.packets.PacketListener;
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
            Entity entity = new EntityTest(((EntityCreationPacket) packet).getX(), ((EntityCreationPacket) packet).getY());
            entity.setNetworkDetails((EntityCreationPacket) packet);
            entityManager.createEntity(entity);
        }
    }
}
