package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class ChargerIntermediate extends CustomRecipe {
    //@Override
    public ChargerIntermediate() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Charger_Intermediate";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Lithium_Ingot");
        map.put(1, "Pixlies:Capacitor_Intermediate");
        map.put(2, "Pixlies:Circuit_Board");
        map.put(3, "Pixlies:Capacitor_Intermediate");
        map.put(4, "Machine:Energy_Cube_Intermediate");
        map.put(5, "Pixlies:Capacitor_Intermediate");
        map.put(6, "Pixlies:Circuit_Board");
        map.put(7, "Pixlies:Capacitor_Intermediate");
        map.put(8, "Pixlies:Platinum_Ingot");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
