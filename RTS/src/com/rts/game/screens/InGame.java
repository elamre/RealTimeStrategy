package com.rts.game.screens;

import com.rts.game.entities.EntityManager;
import com.rts.game.gameplay.Player;
import com.rts.game.multiplayer.ConnectionBridge;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/19/13
 * Time: 7:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class InGame {
    public static Player player;
    ConnectionBridge connectionBridge;
    EntityManager entityManager;

    public InGame(ConnectionBridge connectionBridge) {
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

    public void draw() {
        entityManager.draw();
        player.draw();
    }
}
