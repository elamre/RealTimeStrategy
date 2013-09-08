package com.rts.game;

import com.badlogic.gdx.Game;
import com.rts.game.screens.MainMenu;

public class RealTimeStrategy extends Game {

	public static Game game;
	
	public RealTimeStrategy() {
		game = this;
	}

	@Override
	public void create() {
		setScreen(new MainMenu());
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}
