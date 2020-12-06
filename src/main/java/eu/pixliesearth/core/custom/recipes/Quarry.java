package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class Quarry extends CustomRecipe {
    //@Override
    public Quarry() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Quarry";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Titanium_Ingot");
        map.put(1, MinecraftMaterial.DIAMOND_PICKAXE.getUUID());
        map.put(2, "Pixlies:Titanium_Ingot");
        map.put(3, "Pixlies:Gold_Dust");
        map.put(4, "Pixlies:Copper_Wire");
        map.put(5, "Pixlies:Gold_Dust");
        map.put(6, "Pixlies:Steel_Block");
        map.put(7, "Pixlies:Circuit_Board");
        map.put(8, "Pixlies:Steel_Block");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
