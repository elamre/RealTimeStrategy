package com.rts.networking.udp;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/13/13
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class MovePacket extends BaseUDPPacket {
    int entityId;
    int direction;
    int speed;
    int x, y;

    public MovePacket() {
        super((byte) 0x01);
    }


    public MovePacket(int entityId, int x, int y, int direction, int speed) {
        super((byte) 0x01);
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        prepareMessage(packData());
    }

    private byte[] packData() {
        byte[] data = new byte[10];
        byte[] entityIdByte = UDPFunctions.toByte(entityId, 4);
        byte[] xByte = UDPFunctions.toByte(x, 2);
        byte[] yByte = UDPFunctions.toByte(y, 2);
        byte[] speedByte = UDPFunctions.toByte(speed, 1);
        byte[] directionByte = UDPFunctions.toByte(direction, 1);
        System.arraycopy(speedByte, 0, data, 0, 1);
        System.arraycopy(directionByte, 0, data, 1, 1);
        System.arraycopy(yByte, 0, data, 2, 2);
        System.arraycopy(xByte, 0, data, 4, 2);
        System.arraycopy(entityIdByte, 0, data, 6, 4);
        return data;
    }

    @Override
    protected void extractData(byte[] data) {
        entityId = UDPFunctions.toUnsignedInt(data, 4, 6);
        x = UDPFunctions.toUnsignedInt(data, 2, 4);
        y = UDPFunctions.toUnsignedInt(data, 2, 2);
        speed = UDPFunctions.toUnsignedInt(data, 1, 1);
        direction =UDPFunctions.toUnsignedInt(data, 1, 0);
    }

    public int getEntityId() {
        return entityId;
    }

    public int getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
