package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class Sugar4 extends CustomRecipe {
    
    public Sugar4() {
        
    }
    
    @Override
    public String craftedInUUID() {
		return "Pixlies:Grill";
	}
    
    @Override
    public int getResultAmount() {
		return 4;
	}
    
    @Override
    public String getResultUUID() {
        return MinecraftMaterial.SUGAR.getUUID();
    }
    
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.HONEY_BOTTLE.getUUID());
        return map;
    }
    
    @Override
    public Long getCraftTime() {
		return 2000L;
	}
}