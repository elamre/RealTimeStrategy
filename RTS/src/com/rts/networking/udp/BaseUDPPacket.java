package com.rts.networking.udp;

import com.rts.test.UDPPackets;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/13/13
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseUDPPacket {
    byte[] dataPacket;
    private byte type = 0;
    private boolean prepared = false;

    public BaseUDPPacket(byte type) {
        this.type = type;
    }

    public void readData(byte[] data) {
        int length = data.length - UDPFunctions.INDEX_SIZE - UDPFunctions.PACKET_TYPE_SIZE;
        byte[] usableData = new byte[length];
        System.arraycopy(data, 1, usableData, 0, length);
        extractData(usableData);
    }

    protected abstract void extractData(byte[] data);

    protected void prepareMessage(byte[] data) {
        dataPacket = new byte[UDPFunctions.PACKET_TYPE_SIZE + data.length + UDPFunctions.INDEX_SIZE];
        dataPacket[0] = type;
        for (int i = 0, l = data.length; i < l; i++) {
            dataPacket[i + 1] = data[i];
        }
        dataPacket[dataPacket.length - (UDPFunctions.INDEX_SIZE)] = ++UDPPackets.packetIndex;
        prepared = true;
    }

    public byte[] getPacket() {
        if (prepared)
            return dataPacket;
        return null;
    }
}
