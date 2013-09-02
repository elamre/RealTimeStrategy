package com.rts.game.abilities;

import com.rts.game.entities.Unit;

/**
 * Created with IntelliJ IDEA.
 * User: Jake
 * Date: 8/31/13
 * Time: 11:53 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Ability {

    //Passive abilities require no use from the player
    //Press abilities require one press to use
    //Targeted abilities require you to press the button, then click the unit or location to use it on


    protected Unit owner;
    public boolean disabled;
    int key;

    public abstract void logic(float delta);

    public Ability(Unit owner) {
        this.owner = owner;
    }

}
