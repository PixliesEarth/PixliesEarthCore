package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;



public class Carbon extends CustomRecipe {
    //@Override
    public Carbon() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Carbon";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Carbon_Dust");
        map.put(1, "Pixlies:Carbon_Dust");
        map.put(2, "Pixlies:Carbon_Dust");
        map.put(3, "Pixlies:Carbon_Dust");
        map.put(4, "Pixlies:Carbon_Dust");
        map.put(5, "Pixlies:Carbon_Dust");
        map.put(6, "Pixlies:Carbon_Dust");
        map.put(7, "Pixlies:Carbon_Dust");
        map.put(8, "Pixlies:Carbon_Dust");
        return map;
    }
    @Override
    public int getResultAmount() {
        return 1;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
