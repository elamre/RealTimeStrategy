package com.rts.game;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/16/13
 * Time: 8:07 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ClientEventListener {
    public void hostNotFound(String ip, int port);

    public void connected();
}
