package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;

public class MudBrick extends CustomRecipe {
    
    public MudBrick() {
        
    }
    
    public String getResultUUID() {
        return "Pixlies:Mud_Brick";
    }
    
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "minecraft:dirt");
        map.put(1, "minecraft:dirt");
        map.put(2, "minecraft:dirt");
        map.put(3, "minecraft:dirt");
        map.put(4, "minecraft:water_bucket");
        map.put(5, "minecraft:dirt");
        map.put(6, "minecraft:dirt");
        map.put(7, "minecraft:dirt");
        map.put(8, "minecraft:dirt");
        return map;
    }
}
