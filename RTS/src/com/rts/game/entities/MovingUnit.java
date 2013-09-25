package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rts.game.abilities.Walk;
import com.rts.game.gameplay.World;
import com.rts.networking.mutual.packets.EntityCreation;
import com.rts.networking.mutual.packets.EntityPosChange;

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
    private EntityPosChange moveEntityPacket;

    /** USE THIS ONLY FOR REGISTERING THE ENTITY! SHOULD NOT BE USED OTHERWISE! */
    protected MovingUnit() {
        super();
    }

    protected MovingUnit(int x, int y) {
        super(x, y);
        // walker = new Walk(this);
//        abilities.add(walker);
    }


    public MovingUnit(EntityCreation entityCreation, TextureRegion textureRegion) {
        super(entityCreation, textureRegion);
        walker = new Walk(this);
        abilities.add(walker);
    }

    public MovingUnit(EntityCreation entityCreation, TextureRegion textureRegion, int frames, float frameSpeed) {
        super(entityCreation, null);
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
        setAngle((float) Math.toDegrees(walker.getAngle()));
        implementUpdate_4(deltaT);
    }

    public abstract void implementUpdate_4(float deltaT);

    public void moveEntity(EntityPosChange entityPosChange) {
        this.x = entityPosChange.x;
        this.y = entityPosChange.y;
        walker.nextNode = World.nodeAt(entityPosChange.tarX, entityPosChange.tarY);
    }

    @Override
    public EntityPosChange getMovePacket() {
        EntityPosChange tempChange = moveEntityPacket;
        moveEntityPacket = null;
        return tempChange;
    }

}
