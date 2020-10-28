package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class TinLeggings extends CustomRecipe {
    
    public TinLeggings() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Tin_Leggings";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Tin_Ingot");
        map.put(1, "Pixlies:Tin_Ingot");
        map.put(2, "Pixlies:Tin_Ingot");
        map.put(3, "Pixlies:Tin_Ingot");
        map.put(4, "minecraft:air");
        map.put(5, "Pixlies:Tin_Ingot");
        map.put(6, "Pixlies:Tin_Ingot");
        map.put(7, "minecraft:air");
        map.put(8, "Pixlies:Tin_Ingot");
        return map;
    }
}
