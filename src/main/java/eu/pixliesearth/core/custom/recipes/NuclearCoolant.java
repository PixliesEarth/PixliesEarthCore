package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;



public class NuclearCoolant extends CustomRecipe {
    //@Override
    public NuclearCoolant() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Nuclear_Coolant";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Steel_Ingot");
        map.put(1, MinecraftMaterial.BLUE_ICE.getUUID());
        map.put(2, "Pixlies:Steel_Ingot");
        map.put(3, MinecraftMaterial.BLUE_ICE.getUUID());
        map.put(4, MinecraftMaterial.BLUE_ICE.getUUID());
        map.put(5, MinecraftMaterial.BLUE_ICE.getUUID());
        map.put(6, "Pixlies:Steel_Ingot");
        map.put(7, MinecraftMaterial.BLUE_ICE.getUUID());
        map.put(8, "Pixlies:Steel_Ingot");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
