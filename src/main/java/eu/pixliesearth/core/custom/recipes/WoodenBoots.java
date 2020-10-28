package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class WoodenBoots extends CustomRecipe {
    
    public WoodenBoots() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Wooden_Boots";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "minecraft:air");
        map.put(1, "minecraft:air");
        map.put(2, "minecraft:air");
        map.put(3, "minecraft:oak_planks");
        map.put(4, "minecraft:air");
        map.put(5, "minecraft:oak_planks");
        map.put(6, "minecraft:oak_planks");
        map.put(7, "minecraft:air");
        map.put(8, "minecraft:oak_planks");
        return map;
    }
}
