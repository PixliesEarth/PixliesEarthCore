package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class HeatGenerator extends CustomRecipe {
    //@Override
    public HeatGenerator() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Heat_Generator";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Energy_Cube_Advanced");
        map.put(1, "Pixlies:Platinum_Block");
        map.put(2, "Pixlies:Copper_Wire");
        map.put(3, "Pixlies:Platinum_Block");
        map.put(4, "Pixlies:Circuit_Board");
        map.put(5, "Pixlies:Platinum_Block");
        map.put(6, "Pixlies:Copper_Wire");
        map.put(7, "Pixlies:Platinum_Block");
        map.put(8, "Pixlies:Energy_Cube_Advanced");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
