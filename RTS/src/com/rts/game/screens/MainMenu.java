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
import com.rts.networking.host.Server;

/** TODO this is going to be the main menu */
public class MainMenu implements Screen {

	private Skin skin;
	private Stage stage;
	private Table table;

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
		skin.load(Gdx.files.internal("ui/menuSkin.json"));

		table = new Table(skin);
		table.setFillParent(true);
		table.add("RealTimeStrategy", "large").row();

		final TextField enterIp = new TextField("127.0.0.1", skin, "large");

		final TextButton connectButton = new TextButton("connect", skin);
		connectButton.pad(10);

		final TextButton hostButton = new TextButton("host game", skin);
		hostButton.pad(10);

		ClickListener buttonHandler = new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(event.getListenerActor() == connectButton) {
					// ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new Game(enterIp.getText())); // doesn't work with 0.9.8...
					RealTimeStrategy.game.setScreen(new Game(enterIp.getText()));
				} else if(event.getListenerActor() == hostButton) {
					Server.main(new String[] {});
					hostButton.setText("started!");
				}
			}
		};

		connectButton.addListener(buttonHandler);
		hostButton.addListener(buttonHandler);

		Table dialog = new Table(skin);
		dialog.add(enterIp).width(enterIp.getStyle().font.getBounds(enterIp.getText()).width).colspan(2).row();
		dialog.add(connectButton);
		dialog.add(hostButton);

		table.add(dialog).expandY();

		stage.addActor(table);
	}

	@Override
	public void hide() {
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
