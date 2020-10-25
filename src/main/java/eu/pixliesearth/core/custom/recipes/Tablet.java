package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class Tablet extends CustomRecipe {
    
    public Tablet() {
        
    }
    
    public String getResultUUID() {
        return "Pixlies:Tablet";
    }
    
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "minecraft:clay");
        map.put(1, "minecraft:clay");
        map.put(2, "minecraft:air");
        map.put(3, "minecraft:clay");
        map.put(4, "minecraft:clay");
        map.put(5, "minecraft:air");
        map.put(6, "minecraft:clay");
        map.put(7, "minecraft:clay");
        map.put(8, "minecraft:air");
        return map;
    }
}
