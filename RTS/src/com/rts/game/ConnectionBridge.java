package com.rts.game;

import com.rts.networking.client.Client;
import com.rts.networking.packets.PacketListener;

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

    public ConnectionBridge() {
        client = new Client();
    }

    public static void addEntity(Entity entity) {
        Client.getClient().sendEntityRequest(entity);
    }

    public void connect(String ip, int port, ClientEventListener clientEventListener, PacketListener packetListener) {
        try {
            client.connect(ip, port, packetListener);
        } catch (IOException e) {
            clientEventListener.hostNotFound(ip, port);
            return;
        }
        clientEventListener.connected();
    }
}
