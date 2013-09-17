package com.rts.networking.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.rts.game.entities.*;
import com.rts.networking.mutual.packets.EntityCreation;
import com.rts.networking.mutual.packets.EntityPosChange;
import com.rts.networking.mutual.packets.PlayerConnected;
import com.rts.networking.server.PlayerConnection;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/12/13
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientListener extends Listener {
    private EntityManager entityManager;

    public ClientListener(EntityManager entityManager) {
        this.entityManager = entityManager;
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
        PlayerConnected playerConnected = new PlayerConnected();
        playerConnected.playerName = "Elmar";
        playerConnected.playerId = connection.getID();
        connection.sendTCP(playerConnected);
        connection.updateReturnTripTime();


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
        if (object instanceof EntityCreation) {
            Entity entity;
/*            try {
                entity = EntityList.getEntity(((EntityCreation) object).entityType).getConstructor(EntityCreation.class).newInstance((EntityCreation) object);
                entityManager.createEntity(entity);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvocationTargetException e) {
                System.out.println(e.getCause());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }*/
            if (((EntityCreation) object).entityType == EntityList.getEntityType(new TestEntity())) {
                entity = new TestEntity((EntityCreation) object);
            } else if (((EntityCreation) object).entityType == EntityList.getEntityType(new TestEntity())) {
                entity = new TestBuilding((EntityCreation) object);
            } else {
                entity = new TestEntity((EntityCreation) object);
            }
            if (entity != null) {
                entityManager.createEntity(entity);
            }

        } else if (object instanceof EntityPosChange) {
            ((MovingUnit) entityManager.getEntity(((EntityPosChange) object).id)).moveEntity((EntityPosChange) object);
        } else if (object instanceof PlayerConnected) {
            System.out.println("Player: " + ((PlayerConnected) object).playerName + " with id: " + ((PlayerConnected) object).playerId + " connected!");
        }
    }

    /**
     * Called when the connection is below the {@link com.esotericsoftware.kryonet.Connection#setIdleThreshold(float) idle threshold}.
     */
    @Override
    public void idle(Connection connection) {
        super.idle(connection);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
