package com.rts.networking.packets.system;

import com.rts.networking.packets.Packet;
import com.rts.networking.packets.PacketManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/27/13
 * Time: 8:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class DisconnectPacket extends Packet {
    public static final int DISCONNECT_BY_USER = 0x01;
    public static final int TIMED_OUT = 0x02;
    public static final int KICKED_FROM_SERVER = 0x03;
    private int reason = 0;

    public DisconnectPacket() {
        super();
    }

    public DisconnectPacket(int reason, int connectionId) {
        super(PacketManager.getPacketId(new DisconnectPacket()), connectionId);
        this.reason = reason;
    }

    @Override
    public void readPacketSpecific(DataInputStream in) throws IOException {
        reason = in.readInt();
    }

    @Override
    public void writePacketSpecific(DataOutputStream out) throws IOException {
        out.writeInt(reason);
    }

    public int getReason() {
        return reason;
    }
}
