package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class RockLeggings extends CustomRecipe {
    
    public RockLeggings() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Rock_Leggings";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "minecraft:cobblestone");
        map.put(1, "minecraft:cobblestone");
        map.put(2, "minecraft:cobblestone");
        map.put(3, "minecraft:cobblestone");
        map.put(4, "minecraft:air");
        map.put(5, "minecraft:cobblestone");
        map.put(6, "minecraft:cobblestone");
        map.put(7, "minecraft:air");
        map.put(8, "minecraft:cobblestone");
        return map;
    }
}
