package networking.host;

import networking.packets.Packet;
import util.Logger;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 2:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class Server implements Runnable {
    /* ArrayList containing all the connected clients */
    private ArrayList<ServerClient> serverClients = new ArrayList<ServerClient>();
    /* enum holding the status of the server */
    private ServerStatus serverStatus = ServerStatus.STOPPED;
    /* Simple boolean holding if the server is running or not */
    private boolean running = false;

    public static void main(String[] args) {
        Thread serverThread;
        Server server = new Server();
        serverThread = new Thread(server);
        serverThread.start();
        server.startServer();
    }

    /**
     * This function will start the server.
     */
    public void startServer() {
        Logger.getInstance().system("Starting server...");
        running = true;
        Logger.getInstance().system("Server successfully started!");
    }

    /**
     * This function will stop the server
     */
    public void stopServer() {
        Logger.getInstance().system("Stopping server...");
        running = false; // TODO wait for processes
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

    @Override
    public void run() {
        while (true) {

        }
    }

    /**
     * Function to send all clients a packet
     *
     * @param packet the packet to send
     */
    public void sendAllTCP(Packet packet) {

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
