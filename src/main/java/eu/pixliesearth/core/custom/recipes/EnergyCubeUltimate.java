package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class EnergyCubeUltimate extends CustomRecipe {
    //@Override
    public EnergyCubeUltimate() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Energy_Cube_Ultimate";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.NETHERITE_INGOT.getUUID());
        map.put(1, "Pixlies:Capacitor_Ultimate");
        map.put(2, "Pixlies:Circuit_Board");
        map.put(3, "Pixlies:Capacitor_Ultimate");
        map.put(4, "Machine:Energy_Cube_Advanced");
        map.put(5, "Pixlies:Capacitor_Ultimate");
        map.put(6, "Pixlies:Circuit_Board");
        map.put(7, "Pixlies:Capacitor_Ultimate");
        map.put(8, MinecraftMaterial.NETHERITE_INGOT.getUUID());
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
