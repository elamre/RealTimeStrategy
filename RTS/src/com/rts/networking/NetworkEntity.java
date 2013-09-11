package com.rts.networking;

import com.rts.networking.packets.game.RequestEntityPacket;
import com.rts.util.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/13/13
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class NetworkEntity {
    int playerId;
    int entityType;
    int entityId;
    int x, y;

    public NetworkEntity(int entityId, RequestEntityPacket requestEntityPacket) {
        this.entityId = entityId;
        x = requestEntityPacket.getX();
        y = requestEntityPacket.getY();
        entityType = requestEntityPacket.getEntityType();
        playerId = requestEntityPacket.getConnectionId();
        Logger.getInstance().debug("New network entity has been made. EntityId: " + entityId + " entityType: " + entityType);
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getEntityType() {
        return entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
