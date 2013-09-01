package com.rts.game.abilities;

import com.rts.game.entities.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/31/13
 * Time: 11:53 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Ability {

    public static enum AbilityUse {PASSIVE, PRESS, TARGETED}

    ;
    //Passive abilities require no use from the player
    //Press abilities require one press to use
    //Targeted abilities require you to press the button, then click the unit or location to use it on


    protected Entity owner;
    public boolean disabled;

    public abstract void logic();

    public Ability(Entity owner) {
        this.owner = owner;
    }

}
