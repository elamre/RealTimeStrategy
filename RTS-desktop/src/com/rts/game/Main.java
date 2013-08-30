package com.rts.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rts.game.screens.Game;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RTS";
        cfg.useGL20 = false;
        cfg.width = 480;
        cfg.height = 320;
        new LwjglApplication(new Game(), cfg);
    }
}
