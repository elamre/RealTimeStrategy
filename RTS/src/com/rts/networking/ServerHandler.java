package com.rts.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

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
    }

    public void host(int port) {

    }
}
