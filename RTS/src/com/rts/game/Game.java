package com.rts.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rts.networking.client.Client;
import com.rts.networking.packets.Packet;
import com.rts.networking.packets.PacketListener;
import com.rts.networking.packets.system.EntityCreationPacket;
import com.rts.util.Configuration;
import com.rts.util.Logger;

import java.io.IOException;

public class Game implements ApplicationListener {
    ConnectionBridge connectionBridge;
    World world = new World();
    InGame inGame;

    @Override
    public void create() {
        connectionBridge = new ConnectionBridge();
        connectionBridge.connect("127.0.0.1", Configuration.TCP_PORT, new ClientEventListener() {
            @Override
            public void hostNotFound(String ip, int port) {
                Logger.getInstance().system("Could not find host: " + ip + ":" + port);
            }

            @Override
            public void connected() {
                Logger.getInstance().system("Connected");
            }

        }
        );        // TODO move this to a fancy menu
        inGame = new InGame(connectionBridge);
        world.initTestMap();
    }

    @Override
    public void render() {

        inGame.player.cameraUpdates();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        world.draw(inGame.player.cam.batch);

        inGame.draw();

        inGame.player.cam.finishBatches();

        update(Gdx.graphics.getDeltaTime());
    }

    public void dispose() {

    }

    /**
     * Master update function. This function should update all the objects there are
     *
     * @param deltaT the time that has passed since the previous update
     */
    public void update(float deltaT) {
        inGame.update(deltaT);
        world.update();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
