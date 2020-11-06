package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class Tablet extends CustomRecipe {
    
    public Tablet() {
        
    }

    @Override
    public String craftedInUUID() {
        return "Machine:Pottery";
    }

    @Override
    public String getResultUUID() {
        return "Pixlies:Tablet";
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "minecraft:clay");
        map.put(1, "minecraft:clay");
        map.put(2, "minecraft:clay");
        map.put(3, "Pixlies:Mud_Brick");
        map.put(4, "minecraft:clay");
        map.put(5, "minecraft:clay");
        map.put(6, "minecraft:clay");
        return map;
    }
}
