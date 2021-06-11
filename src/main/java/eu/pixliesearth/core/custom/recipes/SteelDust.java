package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class SteelDust extends CustomRecipe {
    
    public SteelDust() {
        
    }
    
    @Override
    public String getResultUUID() {
        return "Pixlies:Steel_Dust";
    }
    
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.AIR.getUUID());
        map.put(1, MinecraftMaterial.AIR.getUUID());
        map.put(2, MinecraftMaterial.AIR.getUUID());
        map.put(3, MinecraftMaterial.COAL.getUUID());
        map.put(4, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(5, MinecraftMaterial.COAL.getUUID());
        map.put(6, MinecraftMaterial.AIR.getUUID());
        map.put(7, MinecraftMaterial.AIR.getUUID());
        map.put(8, MinecraftMaterial.AIR.getUUID());
        return map;
    }
}
