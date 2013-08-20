package com.rts.networking.client;

import com.rts.game.Entity;
import com.rts.networking.packets.Packet;
import com.rts.networking.packets.system.RequestEntityPacket;

import java.io.IOException;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 2:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class Client {
    private Connection connection;
    //client.connect("127.0.0.1", Configuration.TCP_PORT);

    public void connect(String ip, int port) throws IOException {
        Socket socket = new Socket(ip, port);
        connection = new Connection(socket);
    }

    public void writePacket(Packet packet) {
        connection.writePacket(packet);
    }

    public Packet getPacket() {
        return (connection == null) ? null : connection.getReceivedPacket();
    }

    public int getPacketQueueSize() {
        return connection.getReceivingPacketSize();
    }

    public void sendEntityRequest(Entity entity) {
        connection.writePacket(new RequestEntityPacket(0, 1, (int) entity.getX(), (int) entity.getY()));
    }
}
