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
        for (int i = 0; i < addresses.size(); i++) {
            System.out.println("Comparing: " + addresses.get(i).getAddress() + " with: " + address.getAddress());
            if (address.getAddress().equals(addresses.get(i).getAddress()) && address.getPort() == addresses.get(i).getPort()) {
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
        for (int i = 0; i < addresses.size(); i++) {
            receivePacket = addresses.get(i);
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }
}