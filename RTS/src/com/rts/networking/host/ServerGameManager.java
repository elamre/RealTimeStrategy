package com.rts.networking.host;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 2:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerGameManager {
    /* The id. TODO implement a list of any kind */
    private static int id = 0;

    /**
     * This function will retrieve a new id to use.
     *
     * @return the id
     */
    public static int getId() {
        return ++id;
    }
}
