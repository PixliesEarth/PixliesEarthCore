package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;



public class DamascusSteelIngot9 extends CustomRecipe {
    //@Override
    public DamascusSteelIngot9() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Damascus_Steel_Ingot";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.AIR.getUUID());
        map.put(1, MinecraftMaterial.AIR.getUUID());
        map.put(2, MinecraftMaterial.AIR.getUUID());
        map.put(3, MinecraftMaterial.AIR.getUUID());
        map.put(4, "Pixlies:Damascus_Steel_Block");
        map.put(5, MinecraftMaterial.AIR.getUUID());
        map.put(6, MinecraftMaterial.AIR.getUUID());
        map.put(7, MinecraftMaterial.AIR.getUUID());
        map.put(8, MinecraftMaterial.AIR.getUUID());
        return map;
    }
    @Override
    public int getResultAmount() {
        return 9;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
