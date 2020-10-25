package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class CopperLeggings extends CustomRecipe {
    
    public CopperLeggings() {
        
    }
    
    public String getResultUUID() {
        return "Pixlies:Copper_Leggings";
    }
    
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Copper_Ingot");
        map.put(1, "Pixlies:Copper_Ingot");
        map.put(2, "Pixlies:Copper_Ingot");
        map.put(3, "Pixlies:Copper_Ingot");
        map.put(4, "minecraft:air");
        map.put(5, "Pixlies:Copper_Ingot");
        map.put(6, "Pixlies:Copper_Ingot");
        map.put(7, "minecraft:air");
        map.put(8, "Pixlies:Copper_Ingot");
        return map;
    }
}
