package com.rts.networking.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.rts.networking.mutual.Network;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/11/13
 * Time: 9:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerHandler {
    Server server;

    public ServerHandler() {
        server = new Server() {
            protected Connection newConnection() {
                return new PlayerConnection();
            }
        };
        Network.register(server);
    }

    public void setListener(ServerListener serverListener) {
        server.addListener(serverListener);
    }

    public void sendAll(Object object, int exceptionId, boolean udp) {
        if (udp) {
            if (exceptionId != -1)
                server.sendToAllExceptUDP(exceptionId, object);
            else
                server.sendToAllUDP(object);
        } else {
            if (exceptionId != -1)
                server.sendToAllExceptTCP(exceptionId, object);
            else
                server.sendToAllTCP(object);
        }
    }

    public int host(int port) {
        try {
            server.bind(port);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return -1;
    }
}
