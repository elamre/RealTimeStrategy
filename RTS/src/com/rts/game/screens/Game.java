package com.rts.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.rts.game.gameplay.Camera;
import com.rts.game.gameplay.Cursor;
import com.rts.game.gameplay.World;
import com.rts.game.multiplayer.ClientEventListener;
import com.rts.game.multiplayer.ConnectionBridge;
import com.rts.util.Configuration;
import com.rts.util.Logger;

public class Game implements Screen {

	ConnectionBridge connectionBridge;
	World world = new World();
	InGame inGame;
	String ip;

	public Game(String ip) {
		this.ip = ip;
	}

	@Override
	public void show() {
		Camera.create();
		Cursor.create();

		connectionBridge = new ConnectionBridge();
		connectionBridge.connect(ip, Configuration.TCP_PORT, new ClientEventListener() {

			@Override
			public void hostNotFound(String ip, int port) {
				Logger.getInstance().system("Could not find host: " + ip + ":" + port);
			}

			@Override
			public void connected() {
				Logger.getInstance().system("Connected");
			}

		}
				); // TODO move this to a fancy menu
		inGame = new InGame(connectionBridge);
		world.initTestMap();
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glCullFace(GL10.GL_BACK);

		Camera.update(delta);

		world.draw();

		inGame.draw();

		Camera.draw();

		Cursor.draw();

		Camera.finishBatches();

		update(delta);
	}

	@Override
	public void dispose() {

	}

	/**
	 * Master update function. This function should update all the objects there are
	 *
	 * @param deltaT the time that has passed since the previous update
	 */
	public void update(float deltaT) {
		Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond());
		Cursor.update(deltaT);
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

	@Override
	public void hide() {
	}
}
