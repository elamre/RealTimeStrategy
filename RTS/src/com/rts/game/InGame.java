package com.rts.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rts.networking.packets.Packet;
import com.rts.networking.packets.PacketListener;
import com.rts.networking.packets.system.EntityCreationPacket;
import com.rts.util.Configuration;
import com.rts.util.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/19/13
 * Time: 7:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class InGame {
    Player player;
    ConnectionBridge connectionBridge;
    EntityManager entityManager;

    InGame(ConnectionBridge connectionBridge) {
        this.connectionBridge = connectionBridge;
        entityManager = new EntityManager(connectionBridge);
        connectionBridge.setEntityManager(entityManager);
        player = new Player();
        player.create();

    }

    public void update(float deltaT) {
        player.update(deltaT, entityManager);
        entityManager.update(deltaT);
        connectionBridge.update();
    }

    public void draw(SpriteBatch spriteBatch) {
        entityManager.draw(spriteBatch, player.cam);
    }
}
