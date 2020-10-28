package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class DamascusSteelBoots extends CustomRecipe {
    
    public DamascusSteelBoots() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Damascus_Steel_Boots";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "minecraft:air");
        map.put(1, "minecraft:air");
        map.put(2, "minecraft:air");
        map.put(3, "Pixlies:Damascus_Steel_Ingot");
        map.put(4, "minecraft:air");
        map.put(5, "Pixlies:Damascus_Steel_Ingot");
        map.put(6, "Pixlies:Damascus_Steel_Ingot");
        map.put(7, "minecraft:air");
        map.put(8, "Pixlies:Damascus_Steel_Ingot");
        return map;
    }
}
