package com.rts.networking.client;

import com.rts.networking.packets.Packet;
import com.rts.networking.udp.BaseUDPPacket;
import sun.security.x509.IPAddressName;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/14/13
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class UDPConnection {
    /* The packets to send. */
    private LinkedBlockingQueue<BaseUDPPacket> packetsToSend;

    DatagramSocket clientSocket;

    InetAddress IPAddress;

    public UDPConnection(InetAddress IPAddress) throws SocketException {
        clientSocket = new DatagramSocket();
        this.IPAddress = IPAddress;
        packetsToSend = new LinkedBlockingQueue<BaseUDPPacket>();
    }

    public void sendPacket(BaseUDPPacket packet) {
        packetsToSend.add(packet);
    }
}
