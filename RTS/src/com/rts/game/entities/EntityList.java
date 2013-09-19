package com.rts.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rts.game.hud.BuildingGhost;
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
    /* The packet id to assign to packets. Will increment after each use */
    private static int entityType = 1;
    /* Simple hashMap to obtain an ID from a packet object */
    private static HashMap<Class<? extends Entity>, Integer> entityToType = new HashMap<Class<? extends Entity>, Integer>();
    /* Simple hashMap to obtain an empty object from ID */
    private static HashMap<Integer, Class<? extends Entity>> typeToEntity = new HashMap<Integer, Class<? extends Entity>>();

    public static void register() {
        registerEntity(new TestEntity());
        registerEntity(new TestBuilding());
        //TODO add all the units here. Maybe load them from XML?
    }

    public static void registerEntity(Entity entity) {
        if (!entityToType.containsKey(entity)) {
            Logger.getInstance().system("Registered entity: " + entity.getClass().toString() + " id: " + entityType);
            typeToEntity.put(entityType, entity.getClass());
            entityToType.put(entity.getClass(), entityType);
            entityType++;
        }
    }

    public static void empty() {
    }

    public static void removeEntity(int type) {
        typeToEntity.remove(entityToType.get(type));
        entityToType.remove(type);
    }

    public static int getEntityType(Entity entity) {
        System.out.println("Retrieving id for class: " + entity.getClass().toString());
        if (!entityToType.containsKey(entity.getClass())) {
            System.out.println("Entity isnt registered");
            return 0;
        }

        return entityToType.get(entity.getClass());
    }

    public static Class<? extends Entity> getEntity(int type) {
        return typeToEntity.get(type);
    }
}
