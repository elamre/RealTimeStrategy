package com.rts.networking.client;

import com.rts.util.Configuration;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 2:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connect("127.0.0.1", Configuration.TCP_PORT);
        while(true);
    }

    public void connect(String ip, int port) throws UnknownHostException, IOException {
        Socket socket = new Socket(ip, port);
        Connection connection = new Connection(socket);
    }
}
