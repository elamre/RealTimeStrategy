package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rts.game.abilities.Walk;
import com.rts.game.gameplay.World;
import com.rts.networking.packets.game.EntityCreationPacket;
import com.rts.networking.packets.game.MoveEntityPacket;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/28/13
 * Time: 8:35 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class MovingUnit extends SelectableUnit {
    public float speed = 1;
    public Walk walker;
    private Animation animation;
    private float stateTime = 0;
    private MoveEntityPacket moveEntityPacket;

    protected MovingUnit(int x, int y, int entityType) {
        super(x, y, entityType);
        walker = new Walk(this);
        abilities.add(walker);
    }

    public MovingUnit(int id, int x, int y, int entityType, TextureRegion textureRegion) {
        super(id, x, y, entityType, textureRegion);
        walker = new Walk(this);
        abilities.add(walker);
    }

    public MovingUnit(EntityCreationPacket packet, int entityType, TextureRegion textureRegion) {
        super(packet, entityType, textureRegion);
        walker = new Walk(this);
        abilities.add(walker);
    }

    public MovingUnit(EntityCreationPacket packet, int entityType, TextureRegion textureRegion, int frames, float frameSpeed) {
        super(packet, entityType, null);
        TextureRegion animationRegions[] = new TextureRegion[frames];
        for (int i = 0; i < frames; i++) {
            animationRegions[i] = textureRegion.split(textureRegion.getRegionWidth() / frames, textureRegion.getRegionHeight())[0][i];
        }
        animation = new Animation(frameSpeed, animationRegions);
        animation.setPlayMode(Animation.LOOP_PINGPONG);
        System.out.println("Frame amount: " + animationRegions.length);
        setTextureRegion(animationRegions[0]);

        walker = new Walk(this);
        abilities.add(walker);
    }

    @Override
    public void implementUpdate_3(float deltaT) {
        if (animation != null) {
            if (walker.dx != 0 || walker.dy != 0) {
                stateTime += deltaT;
                setTextureRegion(animation.getKeyFrame(stateTime));
                if (stateTime >= animation.animationDuration * 2) {
                    stateTime -= animation.animationDuration * 2;
                }
            }
        }

        if (World.nodeAt(getX(), getY()) != walker.currentSquare) {
            walker.setCurrentSquare();
        }

        //System.out.println("dx,dy: " + walker.dx + ", " + walker.dy);
        setAngle((float) Math.toDegrees(walker.getNextAngle()));
        implementUpdate_4(deltaT);
    }

    public abstract void implementUpdate_4(float deltaT);

    @Override
    public void implementDraw_2(SpriteBatch spriteBatch) {
        implementDraw_3(spriteBatch);

    }

    public void moveEntity(MoveEntityPacket moveEntityPacket) {
        this.x = moveEntityPacket.getX();
        this.y = moveEntityPacket.getY();
        walker.nextNode = World.nodeAt(moveEntityPacket.getTargetX(), moveEntityPacket.getTargetY());
    }

    @Override
    public MoveEntityPacket getMovePacket() {
        MoveEntityPacket tempPacket = moveEntityPacket;
        moveEntityPacket = null;
        return tempPacket;
    }

    public abstract void implementDraw_3(SpriteBatch spriteBatch);

    public void setDestination(int realWorldX, int realWorldY) {
        walker.walkTo(realWorldX, realWorldY);
    }
}
