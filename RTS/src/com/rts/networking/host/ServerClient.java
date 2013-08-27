package com.rts.networking.host;

import com.rts.networking.NetworkEntity;
import com.rts.networking.packets.Packet;
import com.rts.networking.packets.PacketManager;
import com.rts.networking.packets.game.MoveEntityPacket;
import com.rts.networking.packets.system.ChatPacket;
import com.rts.networking.packets.game.EntityCreationPacket;
import com.rts.networking.packets.system.PingPacket;
import com.rts.networking.packets.game.RequestEntityPacket;
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

public class ServerClient implements Runnable {
    /* The id of the client. */
    public int id = 0;
    /* If the thread should be running or not. */
    private boolean run = false;
    /* The packets to send. */
    private LinkedBlockingQueue<Packet> packetsToSend;
    /* The input stream. */
    private DataInputStream inputStream;
    /* The output stream. */
    private DataOutputStream outputStream;
    /* The logger instance. */
    private Logger logger = Logger.getInstance();
    /* The last time received a package. */
    private long lastMessage = 0;
    /* The time out. */
    private int timeOut = 20000;

    /**
     * Constructor which should
     *
     * @param id     The client id
     * @param socket
     * @throws IOException
     */
    public ServerClient(int id, Socket socket) throws IOException {
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

    public void writePacket(Packet packet) {
        System.out.println("sending packet");
        packetsToSend.add(packet);
    }

    public void writePackets(ArrayList<Packet> packets) {
        packetsToSend.addAll(packets);
    }

    public void run() {
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
            logger.system("ID: " + id + " Timed out!");
        }
        //TODO remove from the server
    }

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

    private void processPacket(Packet packet) {
        if (packet instanceof PingPacket) {
            writePacket(packet);
        } else if (packet instanceof RequestEntityPacket) {
            NetworkEntity networkEntity = new NetworkEntity(ServerGameManager.getId(), (RequestEntityPacket) packet);
            EntityCreationPacket entityCreationPacket = new EntityCreationPacket(-1, networkEntity);
            Server.getServer().sendAllTCP(entityCreationPacket);
            // TODO check if you can actually build it
            // TODO check for resources
            // Todo send a new packet to all
        } else {
            if (packet instanceof MoveEntityPacket) {
                // if (packet.getConnectionId() == ((MoveEntityPacket) packet).getEntityId()) {
                // }
                // TODO validate
            } else if (packet instanceof ChatPacket) {
                logger.debug("chatpack: " + ((ChatPacket) packet).getText());
            }
            Server.getServer().sendAllTCP(packet);
        }
    }

    public int getId() {
        return id;
    }
}
