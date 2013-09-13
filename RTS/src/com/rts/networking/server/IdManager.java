package com.rts.networking.server;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/12/13
 * Time: 9:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class IdManager {
    private static int entityId = 0;

    public static synchronized int getEntityId() {
        return ++entityId;
    }
}
