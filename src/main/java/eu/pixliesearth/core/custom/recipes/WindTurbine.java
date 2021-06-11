package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class WindTurbine extends CustomRecipe {
    //@Override
    public WindTurbine() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Wind_Turbine";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Steel_Block");
        map.put(1, "Pixlies:Copper_Wire");
        map.put(2, "Pixlies:Steel_Block");
        map.put(3, "Machine:Energy_Cube_Advanced");
        map.put(4, "Pixlies:Circuit_Board");
        map.put(5, "Machine:Energy_Cube_Advanced");
        map.put(6, "Pixlies:Steel_Block");
        map.put(7, "Pixlies:Copper_Wire");
        map.put(8, "Pixlies:Steel_Block");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
