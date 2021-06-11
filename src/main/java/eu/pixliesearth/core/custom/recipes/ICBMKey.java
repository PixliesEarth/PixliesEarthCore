package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class ICBMKey extends CustomRecipe {
    //@Override
    public ICBMKey() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:ICBM_Key";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(1, MinecraftMaterial.REDSTONE_BLOCK.getUUID());
        map.put(2, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(3, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(4, "Pixlies:Block_Inspector");
        map.put(5, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(6, MinecraftMaterial.NETHERITE_INGOT.getUUID());
        map.put(7, MinecraftMaterial.NETHERITE_INGOT.getUUID());
        map.put(8, MinecraftMaterial.NETHERITE_INGOT.getUUID());
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
