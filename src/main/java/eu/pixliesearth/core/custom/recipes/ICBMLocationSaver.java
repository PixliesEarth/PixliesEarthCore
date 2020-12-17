package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class ICBMLocationSaver extends CustomRecipe {
    //@Override
    public ICBMLocationSaver() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:ICBM_Location_Holder";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(1, MinecraftMaterial.MAP.getUUID());
        map.put(2, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(3, MinecraftMaterial.NETHERITE_INGOT.getUUID());
        map.put(4, "Pixlies:Circuit_Board");
        map.put(5, MinecraftMaterial.NETHERITE_INGOT.getUUID());
        map.put(6, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(7, MinecraftMaterial.COMPASS.getUUID());
        map.put(8, MinecraftMaterial.IRON_INGOT.getUUID());
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
