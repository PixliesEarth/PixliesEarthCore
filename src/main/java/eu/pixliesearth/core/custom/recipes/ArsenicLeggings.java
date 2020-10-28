package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class ArsenicLeggings extends CustomRecipe {
    
    public ArsenicLeggings() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Arsenic_Leggings";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Arsenic_Ingot");
        map.put(1, "Pixlies:Arsenic_Ingot");
        map.put(2, "Pixlies:Arsenic_Ingot");
        map.put(3, "Pixlies:Arsenic_Ingot");
        map.put(4, "minecraft:air");
        map.put(5, "Pixlies:Arsenic_Ingot");
        map.put(6, "Pixlies:Arsenic_Ingot");
        map.put(7, "minecraft:air");
        map.put(8, "Pixlies:Arsenic_Ingot");
        return map;
    }
}
