package com.rts.networking_old.kryonet;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/11/13
 * Time: 7:21 PM
 * To change this template use File | Settings | File Templates.
 */

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ServerListener extends Listener {
    /**
     * Called when the remote end has been connected. This will be invoked before any objects are received by
     * {@link #received(com.esotericsoftware.kryonet.Connection, Object)}. This will be invoked on the same thread as {@link com.esotericsoftware.kryonet.Client#update(int)} and
     * {@link com.esotericsoftware.kryonet.Server#update(int)}. This method should not block for long periods as other network activity will not be processed
     * until it returns.
     */
    @Override
    public void connected(Connection connection) {
        super.connected(connection);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Called when the remote end is no longer connected. There is no guarantee as to what thread will invoke this method.
     */
    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Called when an object has been received from the remote end of the connection. This will be invoked on the same thread as
     * {@link com.esotericsoftware.kryonet.Client#update(int)} and {@link com.esotericsoftware.kryonet.Server#update(int)}. This method should not block for long periods as other network
     * activity will not be processed until it returns.
     */
    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Called when the connection is below the {@link com.esotericsoftware.kryonet.Connection#setIdleThreshold(float) idle threshold}.
     */
    @Override
    public void idle(Connection connection) {
        super.idle(connection);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
