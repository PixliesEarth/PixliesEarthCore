package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class MissileRadar extends CustomRecipe {
    //@Override
    public MissileRadar() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Missile_Radar";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Steel_Ingot");
        map.put(1, "Pixlies:Copper_Ingot");
        map.put(2, "Pixlies:Steel_Ingot");
        map.put(3, "Pixlies:Zinc_Block");
        map.put(4, "Pixlies:Circuit_Board");
        map.put(5, "Pixlies:Zinc_Block");
        map.put(6, "Pixlies:Titanium_Ingot");
        map.put(7, "Pixlies:Copper_Wire");
        map.put(8, "Pixlies:Titanium_Ingot");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
