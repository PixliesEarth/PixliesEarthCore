package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;



public class Grill extends CustomRecipe {
    //@Override
    public Grill() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Grill";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.IRON_BARS.getUUID());
        map.put(1, MinecraftMaterial.IRON_BARS.getUUID());
        map.put(2, MinecraftMaterial.IRON_BARS.getUUID());
        map.put(3, "Pixlies:Steel_Ingot");
        map.put(4, MinecraftMaterial.COAL_BLOCK.getUUID());
        map.put(5, "Pixlies:Steel_Ingot");
        map.put(6, "Pixlies:Steel_Ingot");
        map.put(7, MinecraftMaterial.COAL_BLOCK.getUUID());
        map.put(8, "Pixlies:Steel_Ingot");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
