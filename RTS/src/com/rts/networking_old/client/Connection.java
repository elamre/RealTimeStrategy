package com.rts.networking_old.client;

import com.rts.networking_old.packets.Packet;
import com.rts.networking_old.packets.PacketManager;
import com.rts.networking_old.packets.system.DisconnectPacket;
import com.rts.networking_old.packets.system.PingPacket;
import com.rts.util.Logger;
import com.rts.util.SocketUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 2:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class Connection implements Runnable {
    /* If the thread should be running or not */
    private boolean run = false;
    /* The packets to send. */
    private LinkedBlockingQueue<Packet> packetsToSend;
    /* The incoming packet buffer */
    private ArrayList<Packet> receivedPackets = new ArrayList<Packet>();
    /* The input stream. */
    private DataInputStream inputStream;
    /* The output stream. */
    private DataOutputStream outputStream;
    /* The logger instance */
    private Logger logger = Logger.getInstance();
    /* The ping packet timer */
    private long lastPing = 0;
    /* The ping interval */
    private int pingInterval = 5000;
    /* The last packet timer */
    private long lastMessage = 0;
    /* The max timeOut in ms */
    private int timeOut = 20000;
    /* The ping */
    private long ping = 0;
    /* The connection id obtained by the server. Also known as playerID */
    private int connectionId = -1;
    /* Packet listener, interface which will react on received packages */
    //private PacketListener packetListener;

    /**
     * Constructor sets up everything and creates the objects.
     *
     * @param socket The socket that the client is connected to.
     * @throws IOException When an error occurs creating input stream or outputstream.
     */
    public Connection(Socket socket) throws IOException {
        packetsToSend = new LinkedBlockingQueue<Packet>();
        inputStream = SocketUtil.wrapInputStream(socket);
        outputStream = SocketUtil.wrapOutputStream(socket);
        run = true;
        new Thread(this).start();
    }

    /**
     * Running thread, checks for packets and writes the ones in the qeueueueue.
     * Also reads the packets. Will also send a ping packet every once in the
     * pingInterval.
     */
    public void run() {
        logger.system("Client write read initialized");
        lastMessage = System.nanoTime() / (1000 * 1000);
        try {
            connectionId = inputStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("id : " + connectionId);
        while (run) {
            try {
                readPackets();
                if (connectionId != -1) {
                    long currentTime = System.nanoTime() / (1000 * 1000);
                    writePackets();
                    if (currentTime - lastPing > pingInterval) {
                        lastPing = System.nanoTime() / (1000 * 1000);
                        writePacket(new PingPacket(connectionId, currentTime));
                    }
                    if (currentTime - lastMessage > timeOut) {
                        Logger.getInstance().system("Connection timed out.");
                        break;
                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IOException e) {
                logger.error("Error while trying to write to server.");
                //TODO dont stop the game
                System.exit(1);
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

/*    *//**
     * Use this function prior to everything. This handles the incoming packets
     * TODO move to constructor?
     *
     * @param packetListener the packetlistener interface
     *//*
    public void setPacketListener(PacketListener packetListener) {
        this.packetListener = packetListener;
    }*/

    /**
     * Adds a new packet to the qeueu to send. use this function ALWAYS to send
     * packets.
     *
     * @param packet the packet to send.
     */
    public void writePacket(Packet packet) {
        packet.setConnectionId(connectionId);
        packetsToSend.add(packet);
    }

    /**
     * Checks if there is a packet available to write, if thats the case it will
     * also write the packet.
     *
     * @throws IOException if an error occurs while writing the packet.
     */
    private void writePackets() throws IOException {
        Packet packet;
        if (!packetsToSend.isEmpty()) {
            packet = packetsToSend.poll();
            packet.write(outputStream);
        }
    }

    /**
     * Checks first if there is data to be read. Then determines what kind of
     * packet it is, and creates a new instance of that packet. Then it will
     * send it to the process packet function.
     *
     * @throws InstantiationException If something goes wrong while creating the packet object.
     * @throws IllegalAccessException
     * @throws IOException            If something goes wrong while reading.
     */
    private void readPackets() throws InstantiationException, IllegalAccessException, IOException {
        int packetId = -1;
        Packet packet;
        if (inputStream.available() != 0) {
            packetId = inputStream.readInt();
            try {
                packet = PacketManager.getPacket(packetId).newInstance();
                if (packet != null) {
                    packet.read(inputStream);
                    processPacket(packet);
                    // Logger.getInstance().debug("New packet read id: " + packetId);
                }
            } catch (NullPointerException NPE) {
                NPE.printStackTrace();
                logger.warn("Unknown packet! id: " + packetId + " unable to create packet.");
            }
            lastMessage = System.nanoTime() / (1000 * 1000);
        }
    }

    /**
     * handles the packet depending on what kind of packet it is. TODO add new packets here.
     *
     * @param packet the packet containing the data.
     */
    private void processPacket(Packet packet) {
        if (packet instanceof PingPacket) {
            setPing((PingPacket) packet);
            logger.system("Ping: " + ping);
            //TODO visualize the ping in some way
            //TODO Add more system packets
        } else if (packet instanceof DisconnectPacket) {
            logger.system("Player with id: " + packet.getConnectionId() + " disconnected. Reason: " + ((DisconnectPacket) packet).getReason());
            receivedPackets.add(packet);
        } else {
            receivedPackets.add(packet);
            // logger.error("Unknown packet " + packet.getClass().getSimpleName() + " ! Your game version might be outdated. Game will now exit.");
            //TODO System.exit(0);
        }
    }

    /**
     * This function will retrieve the latest ping. That is the time it takes to travel to the host, and back again.
     *
     * @return latest ping in ms
     */
    public long getPing() {
        return ping;
    }

    /**
     * This function will set the ping and do some simple calculations on it.
     *
     * @param packet the ping packet received
     */
    private void setPing(PingPacket packet) {
        long currentTime = System.nanoTime() / (1000 * 1000);
        ping = ((currentTime - packet.getTime()));
        //logger.debug(this.getClass(), " ping: " + ping);
    }

    /**
     * this function will get the latest packet and remove it from the stack. If there are no packets
     * it will return null.
     *
     * @return null or the latest packet
     */
    public Packet getReceivedPacket() {
        if (receivedPackets.size() > 0) {
            Packet packet = receivedPackets.get(0);
            receivedPackets.remove(0);
            return packet;
        }
        return null;
    }

    /**
     * This function will return the amount of packets that are available to process.
     *
     * @return amount of packets
     */
    public int getReceivingPacketSize() {
        return receivedPackets.size();
    }

    /**
     * Disconnect method.
     */
    public void disconnect() {
        //TODO send some kind of notification to the host
    }
}
