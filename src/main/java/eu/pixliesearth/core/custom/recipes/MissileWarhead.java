package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;



public class MissileWarhead extends CustomRecipe {
    //@Override
    public MissileWarhead() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Missile_Block";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Titanium_Ingot");
        map.put(1, "Pixlies:Missile_Block");
        map.put(2, "Pixlies:Titanium_Ingot");
        map.put(3, "Pixlies:Missile_Block");
        map.put(4, MinecraftMaterial.NETHER_STAR.getUUID());
        map.put(5, "Pixlies:Missile_Block");
        map.put(6, "Pixlies:Titanium_Ingot");
        map.put(7, "Pixlies:Missile_Block");
        map.put(8, "Pixlies:Titanium_Ingot");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
