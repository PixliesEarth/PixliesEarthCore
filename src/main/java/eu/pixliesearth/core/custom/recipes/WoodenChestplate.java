package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class WoodenChestplate extends CustomRecipe {
    
    public WoodenChestplate() {
        
    }
    
    public String getResultUUID() {
        return "Pixlies:Wooden_Chestplate";
    }
    
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "minecraft:oak_planks");
        map.put(1, "minecraft:air");
        map.put(2, "minecraft:oak_planks");
        map.put(3, "minecraft:oak_planks");
        map.put(4, "minecraft:oak_planks");
        map.put(5, "minecraft:oak_planks");
        map.put(6, "minecraft:oak_planks");
        map.put(7, "minecraft:oak_planks");
        map.put(8, "minecraft:oak_planks");
        return map;
    }
}
