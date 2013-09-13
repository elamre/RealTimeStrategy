package com.rts.game.multiplayer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/13/13
 * Time: 1:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Chat {
    private static Chat chat;
    private ArrayList<Line> lines = new ArrayList<Line>();
    private boolean showAll = false;

    public static Chat getChat() {
        if (chat == null)
            chat = new Chat();
        return chat;
    }

    public void update(float deltaT) {

    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    public void draw(SpriteBatch spriteBatch) {
        if (!showAll) {
            for (int i = lines.size() - 5, l = lines.size(); i < l; i++) {
                lines.get(i).draw(spriteBatch);
            }
        } else {
            for (int i = lines.size() - 5, l = lines.size(); i < l; i++) {
                lines.get(i).draw(spriteBatch);
            }
        }
    }

    class Line {
        private float time = 0;
        private String line;
        private int playerId;

        public void update(float deltaT) {

        }

        public void draw(SpriteBatch spriteBatch) {

        }
    }
}
