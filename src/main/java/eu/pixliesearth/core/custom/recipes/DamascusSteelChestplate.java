package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class DamascusSteelChestplate extends CustomRecipe {
    
    public DamascusSteelChestplate() {
        
    }
    
    public String getResultUUID() {
        return "Pixlies:Damascus_Steel_Chestplate";
    }
    
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Damascus_Steel_Ingot");
        map.put(1, "minecraft:air");
        map.put(2, "Pixlies:Damascus_Steel_Ingot");
        map.put(3, "Pixlies:Damascus_Steel_Ingot");
        map.put(4, "Pixlies:Damascus_Steel_Ingot");
        map.put(5, "Pixlies:Damascus_Steel_Ingot");
        map.put(6, "Pixlies:Damascus_Steel_Ingot");
        map.put(7, "Pixlies:Damascus_Steel_Ingot");
        map.put(8, "Pixlies:Damascus_Steel_Ingot");
        return map;
    }
}