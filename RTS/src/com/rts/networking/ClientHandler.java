package com.rts.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import com.rts.game.entities.EntityManager;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/11/13
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientHandler {
    Client client;

    ClientHandler(Listener listener) {
        client = new Client();
        client.start();
        client.addListener(listener);
    }

    public void connect(String host, int port) {
        try {
            client.connect(200, host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMessage(Object object){
        client.sendTCP(object);
    }
}
