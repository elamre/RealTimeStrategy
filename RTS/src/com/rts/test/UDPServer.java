package com.rts.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/13/13
 * Time: 9:38 AM
 * To change this template use File | Settings | File Templates.
 */
class UDPServer {
    public static ArrayList<DatagramPacket> addresses = new ArrayList<DatagramPacket>();
    public static DatagramSocket serverSocket;

    public static void main(String args[]) throws Exception {
        serverSocket = new DatagramSocket(9876);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            addAddress(receivePacket);
            String sentence = new String(receivePacket.getData());
            System.out.println("RECEIVED: " + sentence);
            String capitalizedSentence = sentence.toUpperCase();
            sendData(capitalizedSentence.getBytes());
            receiveData = new byte[1024];
        }
    }

    public static void addAddress(DatagramPacket address) {
        boolean found = false;
        for (DatagramPacket address1 : addresses) {
            System.out.println("Comparing: " + address1.getAddress() + " with: " + address.getAddress());
            if (address.getAddress().equals(address1.getAddress()) && address.getPort() == address1.getPort()) {
                found = true;
            }
        }
        if (!found)
            addresses.add(address);
        else {
            System.out.println("New address! ");
        }
    }

    public static void sendData(byte[] sendData) throws IOException {
        DatagramPacket receivePacket;
        for (DatagramPacket address : addresses) {
            receivePacket = address;
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }
}