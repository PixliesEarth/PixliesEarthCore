package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class ArsenicBronzeHelmet extends CustomRecipe {
    
    public ArsenicBronzeHelmet() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Arsenic_Bronze_Helmet";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "minecraft:air");
        map.put(1, "minecraft:air");
        map.put(2, "minecraft:air");
        map.put(3, "Pixlies:Arsenic_Bronze_Ingot");
        map.put(4, "Pixlies:Arsenic_Bronze_Ingot");
        map.put(5, "Pixlies:Arsenic_Bronze_Ingot");
        map.put(6, "Pixlies:Arsenic_Bronze_Ingot");
        map.put(7, "minecraft:air");
        map.put(8, "Pixlies:Arsenic_Bronze_Ingot");
        return map;
    }
}
