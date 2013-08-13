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

public class Game implements ApplicationListener {
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;

    Player player = new Player();
    EntityManager ents = new EntityManager();

    @Override
    public void create() {

        player.create();

        ents.addEntity(new EntityTest());

        batch = new SpriteBatch();

        texture = new Texture(Gdx.files.internal("Images/Environment/Grass.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);

        sprite = new Sprite(region);
        sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    @Override
    public void render() {

        player.cameraUpdates();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(player.cam.getCamera().combined);
        batch.begin();
        sprite.draw(batch);
        ents.draw(batch);
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
