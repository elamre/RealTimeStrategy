package com.rts.networking_old.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 2:05 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Packet {
    /* The id which sent the packet */
    protected int connectionId;
    /* The type of packet */
    private int packetId;

    /**
     * Default constructor which is used for instantiating a new packet.
     */
    public Packet() {
    }

    /**
     * Constructor which should always get implemented in a packet. Add extra fields if needed
     *
     * @param packetId     The type of packet
     * @param connectionId The id which sent the packet
     */
    public Packet(int packetId, int connectionId) {
        this.packetId = packetId;
        this.connectionId = connectionId;
    }

    /**
     * This function should get used to read a packet from the net.
     *
     * @param in the data stream to receive everything trough
     * @throws IOException if something happens while trying to receive data
     */
    public void read(DataInputStream in) throws IOException {
        connectionId = in.readInt();
        readPacketSpecific(in);
    }

    /**
     * This function should get implemented in a packet it should read all the data
     * that is getting send.
     *
     * @param in the data stream to receive everything trough
     * @throws IOException if something happens while trying to receive data
     */
    public abstract void readPacketSpecific(DataInputStream in) throws IOException;

    /**
     * This function should get used to send the packet elsewhere.
     *
     * @param out the data output stream to send the packet trough
     * @throws IOException if anything happens while trying to send the packet
     */
    public void write(DataOutputStream out) throws IOException {
        out.writeInt(getPacketId());
        out.writeInt(connectionId);
        writePacketSpecific(out);
        out.flush();
    }

    /**
     * This function should get implemented in a packet. In this function should be all the
     * specific data writing towards the host
     *
     * @param out the data output stream to send the data trough
     * @throws IOException if anything happens while trying to send the data
     */
    public abstract void writePacketSpecific(DataOutputStream out) throws IOException;

    /**
     * This function will return the packet id. This is important so that the system
     * knows what type of packet it is.
     *
     * @return the packetId of the packet
     */
    public int getPacketId() {
        if (packetId == 0)
            packetId = PacketManager.getPacketId(this);
        return packetId;
    }

    /**
     * This function returns the connection id off the packet. This means the player that has
     * send the packet.
     *
     * @return the connection id
     */
    public int getConnectionId() {
        return connectionId;
    }

    /**
     * This function is used to set the connection id of a packet.
     *
     * @param connectionId the new connection id
     */
    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }
}
