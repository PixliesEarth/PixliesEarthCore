package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class HydroGenerator extends CustomRecipe {
    //@Override
    public HydroGenerator() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Hydro_Generator";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Steel_Ingot");
        map.put(1, "Pixlies:Circuit_Board");
        map.put(2, "Pixlies:Steel_Ingot");
        map.put(3, MinecraftMaterial.WATER_BUCKET.getUUID());
        map.put(4, "Machine:Machine_Base_Energy");
        map.put(5, MinecraftMaterial.WATER_BUCKET.getUUID());
        map.put(6, "Pixlies:Steel_Ingot");
        map.put(7, "Pixlies:Circuit_Board");
        map.put(8, "Pixlies:Steel_Ingot");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
