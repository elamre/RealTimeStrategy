package com.rts.networking.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.rts.networking.mutual.Network;
import com.rts.networking.mutual.packets.ChatMessage;
import com.rts.util.Configuration;

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

    public ClientHandler(Listener listener) {
        client = new Client();
        Network.register(client);
        client.start();
        client.addListener(listener);
        client.updateReturnTripTime();
        client.getReturnTripTime();

    }

    public static void main(String[] args) throws InterruptedException {
        ClientHandler clientHandler;

        clientHandler = new ClientHandler(new Listener() {
            /**
             * Called when an object has been received from the remote end of the connection. This will be invoked on the same thread as
             * {@link com.esotericsoftware.kryonet.Client#update(int)} and {@link com.esotericsoftware.kryonet.Server#update(int)}. This method should not block for long periods as other network
             * activity will not be processed until it returns.
             */
            @Override
            public void received(Connection connection, Object object) {
                super.received(connection, object);    //To change body of overridden methods use File | Settings | File Templates.
                if (object instanceof ChatMessage) {
                    System.out.println(((ChatMessage) object).message);
                }
            }
        });
        clientHandler.connect("127.0.0.1", Configuration.TCP_PORT);
        while (true) {
            Thread.sleep(0x3e8);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.message = "Hallo";
            chatMessage.id = 0;
            clientHandler.writeMessage(chatMessage);
        }
    }

    public int getPing() {
        return client.getReturnTripTime();
    }

    public int getId() {
        return client.getID();
    }

    public void connect(String host, int port) {
        try {
            client.connect(200, host, port);
            client.getID();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMessage(Object object) {
        client.sendTCP(object);
    }
}
