package com.rts.game;

import com.badlogic.gdx.Game;
import com.rts.game.menu.MainMenu;


public class RealTimeStrategy extends Game {

	public static ScreenState screenState;
	
	@Override
	public void create() {
		setScreen(new MainMenu());
	}

}
