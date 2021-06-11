package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class CookedChicken extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return MinecraftMaterial.COOKED_CHICKEN.getUUID();
    }
    
    @Override
    public String craftedInUUID() {
        return "Pixlies:Grill";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }
    @Override
	public Long getCraftTime() {
		return 5000L;
	}
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.CHICKEN.getUUID());
        return map;
    }

}