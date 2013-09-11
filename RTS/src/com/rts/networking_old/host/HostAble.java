package com.rts.networking_old.host;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
interface HostAble {
    public void startListening(int port);

    public void stopListening();

    public void addDataListener();

    public void addClientListener();
}
