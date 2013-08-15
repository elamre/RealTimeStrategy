package com.rts.networking.client;

import com.rts.game.Entity;
import com.rts.networking.packets.system.RequestEntityPacket;
import com.rts.util.Configuration;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 2:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class Client {
    private static Client client;
    Connection connection;

    public static Client getClient() {
        if (client == null)
            client = new Client();
        return client;
    }

    //client.connect("127.0.0.1", Configuration.TCP_PORT);

    public void connect(String ip, int port) throws UnknownHostException, IOException {
        Socket socket = new Socket(ip, port);
        connection = new Connection(socket);
    }

    public void sendEntityRequest(Entity entity) {
        connection.writePacket(new RequestEntityPacket(0, 1, (int) entity.getX(), (int) entity.getY()));
    }
}
