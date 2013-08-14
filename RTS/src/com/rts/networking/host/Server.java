package com.rts.networking.host;

import com.rts.networking.packets.Packet;
import com.rts.networking.packets.PacketManager;
import com.rts.util.Configuration;
import com.rts.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 2:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class Server {
    Logger logger = Logger.getInstance();
    TCPServer tcpServer;
    Thread tcpThread;
    UDPServer udpServer;
    Thread udpThread;
    /* ArrayList containing all the connected clients */
    private ArrayList<ServerClient> serverClients = new ArrayList<ServerClient>();
    /* enum holding the status of the server */
    private ServerStatus serverStatus = ServerStatus.STOPPED;
    /* Simple boolean holding if the server is running or not */
    private boolean running = false;
    private int connectionId = 1;

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
        //server.stopServer();
    }

    /**
     * This function will start the server.
     */
    public void startServer() {
        tcpServer = new TCPServer(this);
        tcpServer.startListening(Configuration.TCP_PORT);
        tcpThread = new Thread(tcpServer);
        tcpThread.start();
        udpServer = new UDPServer(this);
        udpServer.startListening(Configuration.UDP_PORT);
        udpThread = new Thread(udpServer);
        udpThread.start();

        // Wait for them both to be up and running. And add a timeout TODO
    }

    public void addClient(Socket socket) {
        try {
            serverClients.add(new ServerClient(connectionId++, socket));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function will stop the server
     */
    public void stopServer() {
        Logger.getInstance().system("Stopping server...");
        tcpServer.stopListening();
        udpServer.stopListening();
        while (tcpServer.isRunning() || udpServer.isRunning()) ;
        running = false;
        Logger.getInstance().system("Server successfully stopped!");
    }

    /**
     * This function will restart the server
     */
    public void restartServer() {
        if (running) {
            Logger.getInstance().system("Restarting server...");
            stopServer();
            while (running) ;
            startServer();
        } else {
            Logger.getInstance().warn("Trying to restart server while the server is not running!");
        }
    }

    /**
     * Function to send all clients a packet
     *
     * @param packet the packet to send
     */
    public void sendAllTCP(Packet packet) {
        for (int i = 0; i < serverClients.size(); i++) {
            if (packet.getConnectionId() != serverClients.get(i).getId())
                serverClients.get(i).writePacket(packet);
        }
    }

    /**
     * TODO figure udp part out
     */
    public void sendAllUDP() {

    }

    /**
     * Enum containing all the possible statuses of the server. TODO add more.
     */
    enum ServerStatus {
        STOPPED, IDLE, CRASHED;
    }
}
