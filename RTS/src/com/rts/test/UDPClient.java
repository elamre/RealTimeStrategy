package com.rts.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/13/13
 * Time: 9:38 AM
 * To change this template use File | Settings | File Templates.
 */
class UDPClient {
    public static DatagramSocket clientSocket;
    public static InetAddress IPAddress;

    public static void main(String args[]) throws Exception {
        clientSocket = new DatagramSocket();
        IPAddress = InetAddress.getByName("localhost");
        new Thread(new Runnable() {

            @Override
            public void run() {
                String sentence = "";
                BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    try {
                        sentence = inFromUser.readLine();
                        sendLine(sentence);
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        }).start();
        byte[] receiveData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("FROM SERVER:" + modifiedSentence);
        }
        ///clientSocket.close();
    }

    public static void sendLine(String sentence) throws IOException {
        byte[] sendData = new byte[1024];
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        clientSocket.send(sendPacket);
    }
}
