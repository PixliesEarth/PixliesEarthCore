package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class BronzeHelmet extends CustomRecipe {
    
    public BronzeHelmet() {
        
    }
    
    public String getResultUUID() {
        return "Pixlies:Bronze_Helmet";
    }
    
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "minecraft:air");
        map.put(1, "minecraft:air");
        map.put(2, "minecraft:air");
        map.put(3, "Pixlies:Bronze_Ingot");
        map.put(4, "Pixlies:Bronze_Ingot");
        map.put(5, "Pixlies:Bronze_Ingot");
        map.put(6, "Pixlies:Bronze_Ingot");
        map.put(7, "minecraft:air");
        map.put(8, "Pixlies:Bronze_Ingot");
        return map;
    }
}
