package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rts.util.Logger;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/26/13
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityList {
    public static final int UNIT_TEST_1 = 1;
    public static final int BUILDING_TEST = 2;
    public static final int UNIT_TEST_3 = 3;

    static {

        //TODO add all the units here. Maybe load them from XML?
    }

    /* The packet id to assign to packets. Will increment after each use */
    private static int entityType = 1;
    /* Simple hashMap to obtain an ID from a packet object */
    private static HashMap<Class<? extends Entity>, Integer> typeToEntity = new HashMap<Class<? extends Entity>, Integer>();
    /* Simple hashMap to obtain an empty object from ID */
    private static HashMap<Integer, Class<? extends Entity>> entityToType = new HashMap<Integer, Class<? extends Entity>>();
    /* Hashmap representing the texture regions of every sprite */
    private static HashMap<Integer, TextureRegion> idToTexture = new HashMap<Integer, TextureRegion>();

    public static void registerEntity(Class<? extends Entity> entity, TextureRegion area) {
        typeToEntity.put(entity, entityType);
        entityToType.put(entityType, entity);
        idToTexture.put(entityType, area);
        Logger.getInstance().system("Registered entity: " + entityToType.get(entityType) + " id: " + entityType);
        entityType++;
    }

    public static void empty() {
    }

    public static void removeEntity(int type) {
        idToTexture.remove(type);
        typeToEntity.remove(entityToType.get(type));
        entityToType.remove(type);
    }

    public static TextureRegion getTextureArea(int type) {
        if (idToTexture.containsKey(type))
            return idToTexture.get(type);
        return null;
    }

    public static int getEntityType(Entity entity) {
        return typeToEntity.get(entity.getClass());
    }

    public static int getEntityType(Class<? extends Entity> entity) {
        return typeToEntity.get(entity);
    }

    public static Class<? extends Entity> getEntity(int type) {
        return entityToType.get(type);
    }
}
