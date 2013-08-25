package com.rts.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.rts.game.pathfinding.JumpPoint;
import com.rts.game.pathfinding.Node;
import com.rts.util.Configuration;
import com.rts.util.Logger;

import java.util.ArrayList;

public class Game implements ApplicationListener {
    ConnectionBridge connectionBridge;
    World world = new World();
    InGame inGame;
    JumpPoint jps;
    boolean[][] testArray = {

            {false, false, false, false, false},
            {false, false, true, true, true},
            {true, false, false, false, true},
            {false, false, false, true, false},
            {true, true, false, false, false}

    };

    @Override
    public void create() {
        jps = new JumpPoint();
        jps.grid.buildNodes(5, 5, testArray);
        ArrayList<Node> test = jps.search(0, 0, 2, 1);
        for (Node n : test) {
            System.out.println("Path: " + n.getX() + ", " + n.getY());
        }


        Camera.create();
        //Cursor.create();

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

        Camera.update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        world.draw(Camera.batch);

        inGame.draw();

        Camera.draw();

        // Cursor.draw();

        Camera.finishBatches();

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
        //Cursor.update(deltaT);
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
