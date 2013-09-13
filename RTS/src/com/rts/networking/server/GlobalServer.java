package com.rts.networking.server;

import com.rts.util.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/12/13
 * Time: 9:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class GlobalServer {
    ServerListener serverListener;
    ServerGame serverGame;
    ServerHandler serverHandler;

    public GlobalServer() {
        serverGame = new ServerGame();
        serverHandler = new ServerHandler();
        serverListener = new ServerListener(serverHandler, serverGame);
        serverHandler.setListener(serverListener);
    }

    public static void main(String[] args) {
        GlobalServer globalServer = new GlobalServer();
        globalServer.startListening(Configuration.TCP_PORT);
    }

    public void startListening(int port) {
        serverHandler.host(port);
    }
}
