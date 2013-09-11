package com.rts.networking_old.udp;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/13/13
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class UDPFunctions {
    public static final int INDEX_SIZE = 1;
    public static final int PACKET_TYPE_SIZE = 1;

    public static byte[] toByte(int number, int size) {
        byte[] array = new byte[size];
        for (int i = 0; i < size; i++) {
            array[i] = (byte) (number >> i * 8);
        }
        return array;
    }

    public static int toUnsignedInt(byte[] array, int size, int offset) {
        byte[] buffer = new byte[4];
        System.arraycopy(array, offset, buffer, 0, size);
        return (buffer[3] << 24) & 0xff000000 | (buffer[2] << 16) & 0xff0000 | (buffer[1] << 8) & 0xff00 | (buffer[0]) & 0xff;
    }
}
