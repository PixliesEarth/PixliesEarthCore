package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class Sugar2 extends CustomRecipe {
    
    public Sugar2() {
        
    }
    
    @Override
    public String craftedInUUID() {
		return "Pixlies:Grill";
	}
    
    @Override
    public int getResultAmount() {
		return 3;
	}
    
    @Override
    public String getResultUUID() {
        return MinecraftMaterial.SUGAR.getUUID();
    }
    
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.HONEYCOMB.getUUID());
        return map;
    }
    
    @Override
    public Long getCraftTime() {
		return 9000L;
	}
}
