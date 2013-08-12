package com.rts.networking.matchmaking;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Server implements Runnable {
    private HashMap<String, Games> games = new HashMap<String, Games>();

    public static void main(String[] args) {
        Thread serverThread = new Thread(new Server());
        serverThread.start();
    }

    @Override
    public void run() {
        while (true) {

        }
    }

    public class clientHandler implements Runnable {
        Socket socket;

        clientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            //check if he wants to host or if he wants to retrieve data
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
