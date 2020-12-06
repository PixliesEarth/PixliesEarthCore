package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class CarbonChunk extends CustomRecipe {
    //@Override
    public CarbonChunk() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Carbon_Chunk";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.COAL_BLOCK.getUUID());
        map.put(1, MinecraftMaterial.GLASS.getUUID());
        map.put(2, MinecraftMaterial.COAL_BLOCK.getUUID());
        map.put(3, MinecraftMaterial.GLASS.getUUID());
        map.put(4, "Pixlies:Charcoal_Chunk");
        map.put(5, MinecraftMaterial.GLASS.getUUID());
        map.put(6, MinecraftMaterial.COAL_BLOCK.getUUID());
        map.put(7, MinecraftMaterial.GLASS.getUUID());
        map.put(8, MinecraftMaterial.COAL_BLOCK.getUUID());
        return map;
    }
    @Override
    public int getResultAmount() {
        return 1;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
