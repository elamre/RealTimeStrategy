package com.rts.networking_old.packets.system;

import com.rts.networking_old.packets.Packet;
import com.rts.networking_old.packets.PacketManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PingPacket extends Packet {
    long time = 0;

    public PingPacket() {
        super();
    }

    public PingPacket(int connectionId, long time) {
        super(PacketManager.getPacketId(new PingPacket()), connectionId);
        this.time = time;
    }

    public void readPacketSpecific(DataInputStream in) throws IOException {
        time = in.readLong();
    }

    public void writePacketSpecific(DataOutputStream out) throws IOException {
        out.writeLong(time);
    }

    public long getTime() {
        return time;
    }
}
