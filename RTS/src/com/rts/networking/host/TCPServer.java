package com.rts.networking.host;

import com.rts.networking.packets.PacketManager;
import com.rts.util.Configuration;
import com.rts.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 2:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class TCPServer implements Hostable, Runnable {
    Logger logger = Logger.getInstance();
    ServerSocket serverSocket;
    private Server server;
    private boolean running = false;

    public TCPServer(Server server) {
        this.server = server;
    }

    @Override
    public void startListening(int port) {
        boolean error = false;
        long startTime = System.nanoTime() / (1000 * 1000);
        PacketManager.empty(); //To setup all the packets
        logger.system("Starting TCP server...");
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e1) {
            logger.error(e1);
            logger.error("Unable to start TCP server.");
            error = true;
        }
        if (!error) {
            logger.system("TCP server successfully started!");
            running = true;
            logger.system("TCP server started successfully in: " + ((System.nanoTime() / (1000 * 1000)) - startTime) + " ms");
        }
    }

    @Override
    public void stopListening() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void addDataListener() {

    }

    @Override
    public void addClientListener() {

    }

    @Override
    public void run() {
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                server.addClient(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                break;
            }
        }
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
