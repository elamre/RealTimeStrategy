package com.rts.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rts.game.Assets;
import com.rts.game.RealTimeStrategy;

public class MainMenu implements Screen {

	private static final float BUTTON_PADDING = 8;
	private Stage stage;
	private Skin skin;
	private Table table;

	@Override
	public void show() {
		Gdx.gl.glClearColor(0, .25f, 0, 1);

		Gdx.input.setInputProcessor(stage = new Stage());
		skin = new Skin();
		skin.addRegions(Assets.getAssets().getTextureAtlas());
		skin.load(Assets.FileHandles.MenuSkin.fileHandle);
		table = new Table(skin);
		table.setFillParent(true);

		final TextButton singleplayerButton = new TextButton("Single Player", skin);
		final TextButton multiplayerButton = new TextButton("Multi Player", skin);
		final TextButton optionsButton = new TextButton("Options", skin);
		final TextButton quitButton = new TextButton("Quit", skin);

		singleplayerButton.pad(BUTTON_PADDING);
		multiplayerButton.pad(BUTTON_PADDING);
		optionsButton.pad(BUTTON_PADDING);
		quitButton.pad(BUTTON_PADDING);

		ClickListener buttonHandler = new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(event.getListenerActor() == singleplayerButton)
					singleplayerButton.setText("not implemented yet");
				else if(event.getListenerActor() == multiplayerButton) {
					stage.addAction(Actions.moveBy(0, -stage.getHeight() / 10, .5f));
					stage.addAction(Actions.sequence(Actions.fadeOut(.5f), Actions.run(new Runnable() {

						public void run() {
							RealTimeStrategy.game.setScreen(new MultiplayerMenu());
						}

					})));
				} else if(event.getListenerActor() == optionsButton)
					optionsButton.setText("not implemented yet");
				else if(event.getListenerActor() == quitButton) {
					stage.addAction(Actions.moveBy(0, -stage.getHeight() / 10, .5f));
					stage.addAction(Actions.sequence(Actions.fadeOut(.5f), Actions.run(new Runnable() {

						@Override
						public void run() {
							Gdx.app.exit();
						}

					})));
				}
			}

		};

		singleplayerButton.addListener(buttonHandler);
		multiplayerButton.addListener(buttonHandler);
		optionsButton.addListener(buttonHandler);
		quitButton.addListener(buttonHandler);

		table.add(RealTimeStrategy.class.getSimpleName(), "large").padTop(25).spaceBottom(125).row();
		table.add(singleplayerButton).pad(BUTTON_PADDING / 2).row();
		table.add(multiplayerButton).pad(BUTTON_PADDING / 2).row();
		table.add(optionsButton).pad(BUTTON_PADDING / 2).row();
		table.add(quitButton).pad(BUTTON_PADDING / 2).row();

		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// act before draw throws java access violation exception since a java version between 1.7.0_07 (works) and 1.7.0_25 (doesn't work) because dispose is called when the screen changes (it's in hide) in act  
		stage.draw();
		stage.act(delta);
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
		table.invalidateHierarchy();
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
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

}
