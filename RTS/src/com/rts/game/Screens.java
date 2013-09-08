package com.rts.game;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/26/13
 * Time: 10:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class Screens {
    private static ScreenState screenState = Screens.ScreenState.MENU;

    public static void changeState(ScreenState state) {
        screenState = state;
    }

    public static ScreenState getScreenState() {
        return screenState;
    }

    public enum ScreenState {
        MENU, LOBBY, GAME;
    }
}


