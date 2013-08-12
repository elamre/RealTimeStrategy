package networking.packets;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 2:04 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PacketListener {
    /**
     * Function which will get called upon arriving of a packet.
     *
     * @param packet the packet received
     */
    public void packetReceived(Packet packet);
}
