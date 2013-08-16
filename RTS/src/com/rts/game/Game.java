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
import com.rts.util.Configuration;

import java.io.IOException;

public class Game implements ApplicationListener {
    private SpriteBatch batch;

    Player player = new Player();
    EntityManager ents = new EntityManager();

    World world = new World();

    @Override
    public void create() {

        try {
            Client.getClient().connect("127.0.0.1", Configuration.TCP_PORT);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        player.create();

        //ents.addEntity(new EntityTest());

        batch = new SpriteBatch();

        world.initTestMap();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {

        player.cameraUpdates();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(player.cam.getCamera().combined);
        batch.begin();

        world.draw(batch);

        ents.draw(batch, player.cam);

        player.draw();

        batch.end();
        update(Gdx.graphics.getDeltaTime());
    }

    /**
     * Master update function. This function should update all the objects there are
     *
     * @param deltaT the time that has passed since the previous update
     */
    public void update(float deltaT) {
        ents.update(deltaT);
        player.update(deltaT, ents);
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
