package com.rts.networking_old;

import com.rts.networking_old.packets.Packet;
import com.rts.networking_old.packets.PacketManager;
import com.rts.networking_old.packets.system.DisconnectPacket;
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
 * Date: 8/30/13
 * Time: 8:45 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractServerClient implements Runnable {
    /* The id of the client. */
    protected int id = 0;
    /* The socket the client is created on */
    protected Socket socket;
    /* The logger everything can get logged to */
    protected Logger logger = Logger.getInstance();
    /* If the thread should be running or not. */
    private boolean run = false;
    /* The packets to send. */
    private LinkedBlockingQueue<Packet> packetsToSend;
    /* The input stream. */
    private DataInputStream inputStream;
    /* The output stream. */
    private DataOutputStream outputStream;
    /* The last time received a package. */
    private long lastMessage = 0;
    /* The time out. */
    private int timeOut = 20000;

    /**
     * The main constructor for the server client. This will set everything up, disable Naggle's tcp algorithm
     * and start the thread.
     *
     * @param socket  the socket this serverclient connected trough
     * @param timeOut the max duration between packets before the client will get kicked
     * @param id      id identifying the serverclient
     * @throws IOException when the socket is null
     */
    public AbstractServerClient(Socket socket, int timeOut, int id) throws IOException {
        if (timeOut < 100) {
            logger.error("The time-out is too small. AbstractServerClient.java");
            System.exit(0);
        }
        this.id = id;
        socket.setTcpNoDelay(true);
        packetsToSend = new LinkedBlockingQueue<Packet>();
        inputStream = SocketUtil.wrapInputStream(socket);
        outputStream = SocketUtil.wrapOutputStream(socket);
        outputStream.writeInt(id);
        outputStream.flush();
        run = true;
        logger.system("New client joined with id: " + id + " ip: " + socket.getInetAddress());
        new Thread(this).start();
    }

    public void run() {
        int reason = DisconnectPacket.DISCONNECT_BY_USER;
        boolean timeOutCheck = false;
        lastMessage = System.nanoTime() / (1000 * 1000);
        // TODO handshake packet, server rules, current players connected

        while (run) {
            long currentTime = System.nanoTime() / (1000 * 1000);
            if (currentTime - lastMessage > timeOut) {
                timeOutCheck = true;
                break;
            }
            try {
                writePackets();
                readPackets();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IOException e) {
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (timeOutCheck) {
            run = false;
            reason = DisconnectPacket.TIMED_OUT;
            logger.system("ID: " + id + " Timed out!");
        }
        disconnect(reason);
    }

    public void writePacket(Packet packet) {
        System.out.println("sending packet");
        packetsToSend.add(packet);
    }

    public void writePackets(ArrayList<Packet> packets) {
        packetsToSend.addAll(packets);
    }

    /**
     * This function will get executed if a player disconnects or times out.
     *
     * @param reason the reason the player disconnected {DisconnectPacket.java}
     */
    protected abstract void disconnect(int reason);

    private void writePackets() throws IOException {
        Packet packet;
        if (!packetsToSend.isEmpty()) {
            packet = packetsToSend.poll();
            packet.write(outputStream);
            logger.debug("Packet sent! Client ID:" + packet.getConnectionId() + "; Packet ID: " + packet.getPacketId() + " " + packet.getClass().getSimpleName());
        }
    }

    private void readPackets() throws InstantiationException, IllegalAccessException, IOException {
        int packetId = -1;
        Packet packet;
        if (inputStream.available() != 0) {
            packetId = inputStream.readInt();
            packet = PacketManager.getPacket(packetId).newInstance();
            if (packet != null) {
                packet.read(inputStream);
                processPacket(packet);
                logger.debug("Packet received! Client ID:" + packet.getConnectionId() + "; Packet ID: " + packet.getPacketId() + " " + packet.getClass().getSimpleName());
            }
            lastMessage = System.nanoTime() / (1000 * 1000);
        }
    }

    /**
     * This function will get executed if a packet is received and need to know what to do with it.
     *
     * @param packet the packet received
     */
    protected abstract void processPacket(Packet packet);

    /**
     * This function will return the id assigned to this serverclient.
     *
     * @return assigned id
     */
    public int getId() {
        return id;
    }
}
