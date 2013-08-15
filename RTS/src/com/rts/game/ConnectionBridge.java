package com.rts.game;

import com.rts.networking.client.Client;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 3:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionBridge {
    public static void addEntity(Entity entity) {
        Client.getClient().sendEntityRequest(entity);
    }
}
