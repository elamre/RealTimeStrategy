package com.rts.game;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 3:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionBridge {
    private static int id = 0;

    public static int getId() {
        return ++id;
    }
}
