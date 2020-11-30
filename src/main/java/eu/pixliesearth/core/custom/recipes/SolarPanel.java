package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;



public class SolarPanel extends CustomRecipe {
    //@Override
    public SolarPanel() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Solar_Panel";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.GLASS.getUUID());
        map.put(1, MinecraftMaterial.GLASS.getUUID());
        map.put(2, MinecraftMaterial.GLASS.getUUID());
        map.put(3, MinecraftMaterial.GLASS.getUUID());
        map.put(4, "Pixlies:Plastic_Hard_Block");
        map.put(5, MinecraftMaterial.GLASS.getUUID());
        map.put(6, "Pixlies:Plastic_Hard_Block");
        map.put(7, "Pixlies:Plastic_Hard_Block");
        map.put(8, "Pixlies:Plastic_Hard_Block");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
