package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;



public class MissileFin extends CustomRecipe {
    //@Override
    public MissileFin() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Missile_Fin";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.AIR.getUUID());
        map.put(1, MinecraftMaterial.AIR.getUUID());
        map.put(2, "Pixlies:Rolled_Steel");
        map.put(3, MinecraftMaterial.AIR.getUUID());
        map.put(4, "Pixlies:Rolled_Steel");
        map.put(5, "Pixlies:Steel_Ingot");
        map.put(6, "Pixlies:Rolled_Steel");
        map.put(7, "Pixlies:Steel_Ingot");
        map.put(8, "Pixlies:Steel_Ingot");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
