package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class AntiMissileHead extends CustomRecipe {
    //@Override
    public AntiMissileHead() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:AntiMissile_Head";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Titanium_Ingot");
        map.put(1, "Pixlies:Missile_Block");
        map.put(2, "Pixlies:Titanium_Ingot");
        map.put(3, "Pixlies:Missile_Block");
        map.put(4, MinecraftMaterial.NETHERITE_INGOT.getUUID());
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
