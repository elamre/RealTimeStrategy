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
    int direction;
    int speed;

    public MoveEntityPacket() {
    }

    public MoveEntityPacket(int entityId, int x, int y, int direction, int speed, int connectionId) {
        super(PacketManager.getPacketId(new MoveEntityPacket()), connectionId);
        this.direction = direction;
        this.entityId = entityId;
        this.speed = speed;
        this.x = x;
        this.y = y;
    }

    @Override
    public void readPacketSpecific(DataInputStream in) throws IOException {
        entityId = in.readInt();
        x = in.readInt();
        y = in.readInt();
        direction = in.readInt();
        speed = in.readInt();
    }

    @Override
    public void writePacketSpecific(DataOutputStream out) throws IOException {
        out.writeInt(entityId);
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(direction);
        out.writeInt(speed);
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

    public int getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }
}
