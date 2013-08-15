package com.rts.networking.host;

import com.rts.util.Logger;

import java.io.IOException;
import java.net.*;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 2:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class UDPServer implements HostAble, Runnable {
    /* The logger object */
    Logger logger = Logger.getInstance();
    /* ServerSocket object */
    DatagramSocket serverSocket;
    /* A reference to the server for access to functions like sendAll(); */
    private Server server;
    /* If the server is running or not */
    private boolean running = false;

    public UDPServer(Server server) {
        this.server = server;
    }

    @Override
    public void startListening(int port) {
        try {
            serverSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void stopListening() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addDataListener() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addClientListener() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void run() {
        byte[] receiveData = new byte[16];
        byte[] sendData = new byte[16];
        DatagramPacket receivePacket;
        while (true) {
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            actOnData(receivePacket.getData());

            String sentence = new String(receivePacket.getData());
            System.out.println("RECEIVED: " + sentence);

            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            String capitalizedSentence = sentence.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            try {
                serverSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void actOnData(byte[] data){

    }

    public boolean isRunning() {
        return false;
    }
}
