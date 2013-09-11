package com.rts.networking_old.matchmaking;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 1:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Games {
    String ip = "";
    int players = 0;
    int timeUp = 0;
    GameState gameState = GameState.LOBBY;

    Games(String ip, int players) {
        this.ip = ip;
        this.players = players;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getTimeUp() {
        return timeUp;
    }

    public void setTimeUp(int timeUp) {
        this.timeUp = timeUp;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    enum GameState {
        LOBBY, IN_GAME, FINISHED
    }
}
