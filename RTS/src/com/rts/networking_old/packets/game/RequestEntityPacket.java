package com.rts.networking_old.packets.game;

import com.rts.networking_old.packets.Packet;
import com.rts.networking_old.packets.PacketManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/13/13
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class RequestEntityPacket extends Packet {
    int entityType = 0;
    int x = 0, y = 0;

    public RequestEntityPacket() {
        super();
    }

    public RequestEntityPacket(int connectionId, int entityType, int x, int y) {
        super(PacketManager.getPacketId(new RequestEntityPacket()), connectionId);
        this.entityType = entityType;
        this.x = x;
        this.y = y;
    }

    @Override
    public void readPacketSpecific(DataInputStream in) throws IOException {
        entityType = in.readInt();
        x = in.readInt();
        y = in.readInt();
    }

    @Override
    public void writePacketSpecific(DataOutputStream out) throws IOException {
        out.writeInt(entityType);
        out.writeInt(x);
        out.writeInt(y);
    }

    public int getEntityType() {
        return entityType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
