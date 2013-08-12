package networking.host;

import com.badlogic.gdx.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 2:04 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ClientListener {
    /**
     * Function which will get called upon the connection of a client.
     *
     * @param socket the socket of the client
     */
    public void clientConnected(Socket socket);
}
