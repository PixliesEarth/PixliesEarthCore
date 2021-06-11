package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class BakedPotato extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return MinecraftMaterial.BAKED_POTATO.getUUID();
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
        map.put(0, MinecraftMaterial.POTATO.getUUID());
        return map;
    }

}