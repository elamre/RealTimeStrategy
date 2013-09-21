package com.rts.game.entities;

import com.rts.networking.mutual.packets.EntityCreation;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 9/21/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Resource extends Entity {

    public Resource(int x, int y) {
        super(x, y);
    }

    public Resource() {
        super();
    }

    public Resource(EntityCreation entityCreation) {
        super(entityCreation);
    }
}
