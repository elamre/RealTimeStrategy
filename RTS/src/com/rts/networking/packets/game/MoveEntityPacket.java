package com.rts.networking.packets.game;

import com.rts.networking.packets.Packet;
import com.rts.networking.packets.PacketManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/21/13
 * Time: 7:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class MoveEntityPacket extends Packet {
    int entityId;
    int x, y;
    int targetX, targetY;

    public MoveEntityPacket() {
    }

    public MoveEntityPacket(int entityId, int x, int y, int targetX, int targetY, int connectionId) {
        super(PacketManager.getPacketId(new MoveEntityPacket()), connectionId);
        this.entityId = entityId;
        this.targetX = targetX;
        this.targetY = targetY;
        this.x = x;
        this.y = y;
    }

    @Override
    public void readPacketSpecific(DataInputStream in) throws IOException {
        entityId = in.readInt();
        x = in.readInt();
        y = in.readInt();
        targetX = in.readInt();
        targetY = in.readInt();
    }

    @Override
    public void writePacketSpecific(DataOutputStream out) throws IOException {
        out.writeInt(entityId);
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(targetX);
        out.writeInt(targetY);
    }

    public int getEntityId() {
        return entityId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }
}
