package com.rts.networking.packets;

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
     * @param packetId The type of packet
     * @param connectionId The id which sent the packet
     */
    public Packet(int packetId, int connectionId) {
        this.packetId = packetId;
        this.connectionId = connectionId;
    }

    /**
     *
     * @param in
     * @throws IOException
     */
    public void read(DataInputStream in) throws IOException {
        connectionId = in.readInt();
        readPacketSpecific(in);
    }

    public abstract void readPacketSpecific(DataInputStream in) throws IOException;

    public void write(DataOutputStream out) throws IOException {
        out.writeInt(getPacketId());
        out.writeInt(connectionId);
        writePacketSpecific(out);
        out.flush();
    }

    public abstract void writePacketSpecific(DataOutputStream out) throws IOException;

    public int getPacketId() {
        if (packetId == 0)
            packetId = PacketManager.getPacketId(this);
        return packetId;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }
}
