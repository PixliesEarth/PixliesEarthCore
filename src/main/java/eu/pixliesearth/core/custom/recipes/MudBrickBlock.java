package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class MudBrickBlock extends CustomRecipe {
    
    public MudBrickBlock() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Mud_Brick_Block";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Mud_Brick");
        map.put(1, "Pixlies:Mud_Brick");
        map.put(2, "minecraft:air");
        map.put(3, "Pixlies:Mud_Brick");
        map.put(4, "Pixlies:Mud_Brick");
        map.put(5, "minecraft:air");
        map.put(6, "minecraft:air");
        map.put(7, "minecraft:air");
        map.put(8, "minecraft:air");
        return map;
    }
}
