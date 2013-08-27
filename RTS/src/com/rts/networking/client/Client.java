package com.rts.networking.client;

import com.rts.game.entities.Entity;
import com.rts.networking.packets.Packet;
import com.rts.networking.packets.game.RequestEntityPacket;
import com.rts.util.Logger;

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
    /* The local connection object. Everything will go trough here */
    private Connection connection;

    /**
     * This function will connect to a target host on a port given.
     *
     * @param ip   the ip of the remove host
     * @param port the port to connect trough
     * @throws IOException if the host can't be found
     */
    public void connect(String ip, int port) throws IOException {
        Socket socket = new Socket(ip, port);
        socket.setTcpNoDelay(true);
        connection = new Connection(socket);
    }

    /**
     * This class will write a packet to the connection class. In the connection
     * class everything else will be handled.
     *
     * @param packet the packet to send
     */
    public void writePacket(Packet packet) {
        connection.writePacket(packet);
    }

    /**
     * This function will get the oldest packet received on the connection. This allows
     * for a integration without any multi-threading problems.
     *
     * @return oldest received packet, or null if there is no connection or packet
     */
    public Packet getPacket() {
        return (connection == null) ? null : connection.getReceivedPacket();
    }

    /**
     * This function will return the amount of packets are available to receive.
     *
     * @return number of packets which can be received
     */
    public int getPacketQueueSize() {
        return connection.getReceivingPacketSize();
    }

    //TODO this doesn't belong here
    public void sendEntityRequest(Entity entity) {
        if (connection != null)
            connection.writePacket(new RequestEntityPacket(0, 1, (int) entity.getX(), (int) entity.getY()));
        else {
            Logger.getInstance().error("Not connected to any server. Game will stop.");
            System.exit(0);
        }
    }
}
