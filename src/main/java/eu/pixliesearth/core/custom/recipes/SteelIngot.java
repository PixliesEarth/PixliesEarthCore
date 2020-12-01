package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class SteelIngot extends CustomRecipe {
    
    public SteelIngot() {
        
    }
    
    @Override
    public String getResultUUID() {
        return "Pixlies:Steel_Ingot";
    }
    
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.COAL.getUUID());
        map.put(1, MinecraftMaterial.COAL.getUUID());
        map.put(2, MinecraftMaterial.IRON_INGOT.getUUID());
        return map;
    }
    
    @Override
    public Long getCraftTime() {
    	return 4500l;
    }
}
