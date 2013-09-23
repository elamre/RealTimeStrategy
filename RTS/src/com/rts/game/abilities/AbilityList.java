package com.rts.game.abilities;

import com.rts.util.Logger;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/18/13
 * Time: 9:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class AbilityList {
    /* The ability id to assign to packets. Will increment after each use */
    private static int abilityType = 1;
    /* Simple hashMap to obtain an ID from a packet object */
    private static HashMap<Class<? extends Ability>, Integer> abilityToType = new HashMap<Class<? extends Ability>, Integer>();
    /* Simple hashMap to obtain an empty object from ID */
    private static HashMap<Integer, Class<? extends Ability>> typeToAbility = new HashMap<Integer, Class<? extends Ability>>();

    public static void register() {
        //TODO add all the units here. Maybe load them from XML?
    }

    public static void registerAbility(Ability ability) {
        if (!abilityToType.containsKey(ability)) {
            Logger.getInstance().system("Registered ability: " + ability.getClass().toString() + " id: " + abilityType);
            typeToAbility.put(abilityType, ability.getClass());
            abilityToType.put(ability.getClass(), abilityType);
            abilityType++;
        }
    }

    public static void empty() {
    }

    public static void removeAbility(int type) {
        typeToAbility.remove(abilityToType.get(type));
        abilityToType.remove(type);
    }

    public static int getAbilityType(Ability ability) {
        System.out.println("Retrieving id for class: " + ability.getClass().toString());
        if (!abilityToType.containsKey(ability.getClass())) {
            System.out.println("Entity isnt registered");
            return 0;
        }

        return abilityToType.get(ability.getClass());
    }

    public static Class<? extends Ability> getAbility(int type) {
        return typeToAbility.get(type);
    }
}
