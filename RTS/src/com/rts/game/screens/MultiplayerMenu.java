package com.rts.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rts.game.Assets;
import com.rts.game.RealTimeStrategy;
import com.rts.game.multiplayer.ClientEventListener;
import com.rts.game.multiplayer.ConnectionBridge;
import com.rts.networking.server.GlobalServer;
import com.rts.util.Configuration;
import com.rts.util.Logger;

/**
 * TODO this is going to be the main menu
 */
public class MultiplayerMenu implements Screen {

	InGame inGame;
	ConnectionBridge connectionBridge = null;
	private Skin skin;
	private Stage stage;
	private Table table;
	private final float BUTTON_PADDING = 10;
	private boolean serverStarted = false;

	public MultiplayerMenu() {
		connectionBridge = new ConnectionBridge();
		inGame = new InGame(connectionBridge);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
		table.invalidateHierarchy();
	}

	@Override
	public void show() {
		Gdx.gl.glClearColor(0, .25f, 0, 1);
		Gdx.input.setInputProcessor(stage = new Stage());
		skin = new Skin();
		skin.addRegions(Assets.getAssets().getTextureAtlas());
		skin.load(Assets.FileHandles.MenuSkin.fileHandle);

		table = new Table(skin);
		table.setFillParent(true);
		table.add("Multiplayer", "large").padTop(100).row();

		final TextField enterIp = new TextField("127.0.0.1", skin, "large");

		final TextButton connectButton = new TextButton("connect", skin);
		connectButton.pad(BUTTON_PADDING);

		final TextButton hostButton = new TextButton("host game", skin);
		hostButton.pad(BUTTON_PADDING);

		final TextButton backButton = new TextButton("back", skin);
		backButton.pad(BUTTON_PADDING);

		ClickListener buttonHandler = new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(event.getListenerActor() == connectButton) {
					connectionBridge.connect(enterIp.getText(), Configuration.TCP_PORT, new ClientEventListener() {

						@Override
						public void hostNotFound(String ip, int port) {
							Logger.getInstance().system("Could not find host: " + ip + ":" + port);
						}

						@Override
						public void connected() {
							Logger.getInstance().system("Connected");
							RealTimeStrategy.game.setScreen(new Game(inGame));
						}

					});
					// ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new Game(enterIp.getText())); // doesn't work with 0.9.8...
				} else if(event.getListenerActor() == hostButton && !serverStarted) {
					GlobalServer.main(null);
					hostButton.setText("started!");
					serverStarted = true;
				} else if(event.getListenerActor() == backButton)
					RealTimeStrategy.game.setScreen(new MainMenu());
			}
		};

		connectButton.addListener(buttonHandler);
		hostButton.addListener(buttonHandler);
		backButton.addListener(buttonHandler);

		Table dialog = new Table(skin);
		dialog.add(enterIp).width(enterIp.getStyle().font.getBounds(enterIp.getText()).width).colspan(2).row();
		dialog.add(connectButton).pad(BUTTON_PADDING / 2);
		dialog.add(hostButton).pad(BUTTON_PADDING / 2);

		table.add(dialog).expandY().row();
		table.add(backButton).expandX().left().pad(BUTTON_PADDING / 2);

		stage.addActor(table);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

}
