package com.rts.networking.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.rts.networking.mutual.packets.*;
import com.rts.util.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/12/13
 * Time: 9:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerListener extends Listener {
    ServerHandler serverHandler;
    ServerGame serverGame;

    public ServerListener(ServerHandler serverHandler, ServerGame serverGame) {
        this.serverHandler = serverHandler;
        this.serverGame = serverGame;
    }

    /**
     * Called when the remote end has been connected. This will be invoked before any objects are received by
     * {@link #received(com.esotericsoftware.kryonet.Connection, Object)}. This will be invoked on the same thread as {@link com.esotericsoftware.kryonet.Client#update(int)} and
     * {@link com.esotericsoftware.kryonet.Server#update(int)}. This method should not block for long periods as other network activity will not be processed
     * until it returns.
     */
    @Override
    public void connected(Connection connection) {
        super.connected(connection);    //To change body of overridden methods use File | Settings | File Templates.
        //TODO Inform other players
    }

    /**
     * Called when the remote end is no longer connected. There is no guarantee as to what thread will invoke this method.
     */
    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);    //To change body of overridden methods use File | Settings | File Templates.
        //TODO show message and inform other players
    }

    /**
     * Called when an object has been received from the remote end of the connection. This will be invoked on the same thread as
     * {@link com.esotericsoftware.kryonet.Client#update(int)} and {@link com.esotericsoftware.kryonet.Server#update(int)}. This method should not block for long periods as other network
     * activity will not be processed until it returns.
     */
    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);    //To change body of overridden methods use File | Settings | File Templates.
        if (object instanceof ChatMessage) {
            serverHandler.sendAll(object, -1, false);
        } else if (object instanceof EntityRequest) {
            if (serverGame.canCreate(((EntityRequest) object).x, ((EntityRequest) object).y, ((EntityRequest) object).entityType)) {

                EntityCreation entityCreation = new EntityCreation();
                entityCreation.x = ((EntityRequest) object).x;
                entityCreation.y = ((EntityRequest) object).y;
                entityCreation.entityType = ((EntityRequest) object).entityType;
                entityCreation.building = ((EntityRequest) object).building;
                entityCreation.id = IdManager.getEntityId();
                Logger.getInstance().debug("New entity of type: " + entityCreation.entityType + " was made at pos: " + entityCreation.x + ", " + entityCreation.y + " with id: " + entityCreation.id);
                serverHandler.sendAll(entityCreation, -1, false);
            }
        } else if (object instanceof PlayerConnected) {
            serverHandler.sendAll(object, connection.getID(), false);
        }
        //TODO complete
    }

    /**
     * Called when the connection is below the {@link com.esotericsoftware.kryonet.Connection#setIdleThreshold(float) idle threshold}.
     */
    @Override
    public void idle(Connection connection) {
        super.idle(connection);    //To change body of overridden methods use File | Settings | File Templates.
        //TODO find out if this means only keep alive packets. If so, show a dialog on the players's computer. AFK for > 10 min is kick
    }
}
