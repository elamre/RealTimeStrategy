package com.rts.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RTS";
        cfg.useGL20 = true;
        cfg.width = 640;
        cfg.height = 480;
        cfg.vSyncEnabled = true;
        new LwjglApplication(new RealTimeStrategy(), cfg);
    }
}
