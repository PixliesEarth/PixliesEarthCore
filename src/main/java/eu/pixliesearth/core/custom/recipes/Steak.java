package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class Steak extends CustomRecipe {
    
    public Steak() {
        
    }
    
    @Override
    public String craftedInUUID() {
		return "Pixlies:Grill";
	}
    
    @Override
    public String getResultUUID() {
        return MinecraftMaterial.COOKED_BEEF.getUUID();
    }
    
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.BEEF.getUUID());
        return map;
    }
    
    @Override
    public Long getCraftTime() {
		return 2000L;
	}
}
