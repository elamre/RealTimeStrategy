package com.rts.networking.packets;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 2:04 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PacketListener {
    /**
     * Function which will get called upon arriving of a packet containing game information.
     *
     * @param packet the packet received
     */
    public void gamePacketReceived(Packet packet);
}
