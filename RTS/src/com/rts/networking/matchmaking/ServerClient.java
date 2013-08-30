package com.rts.networking.matchmaking;

import com.rts.networking.AbstractServerClient;
import com.rts.networking.packets.Packet;

import java.io.IOException;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/30/13
 * Time: 8:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerClient extends AbstractServerClient {

    /**
     * The main constructor for the server client. This will set everything up, disable Naggle's tcp algorithm
     * and start the thread.
     *
     * @param socket  the socket this serverclient connected trough
     * @param timeOut the max duration between packets before the client will get kicked
     * @param id      id identifying the serverclient
     * @throws java.io.IOException when the socket is null
     */
    public ServerClient(Socket socket, int timeOut, int id) throws IOException {
        super(socket, timeOut, id);
    }

    /**
     * This function will get executed if a player disconnects or times out.
     *
     * @param reason the reason the player disconnected {DisconnectPacket.java}
     */
    @Override
    protected void disconnect(int reason) {

    }

    /**
     * This function will get executed if a packet is received and need to know what to do with it.
     *
     * @param packet the packet received
     */
    @Override
    protected void processPacket(Packet packet){

    }
}
