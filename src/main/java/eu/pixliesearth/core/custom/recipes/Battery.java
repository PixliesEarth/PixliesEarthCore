package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class Battery extends CustomRecipe {
    //@Override
    public Battery() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Battery";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Lithium_Ingot");
        map.put(1, "Pixlies:Lithium_Dust");
        map.put(2, "Pixlies:Zinc_Ingot");
        map.put(3, "Pixlies:Lithium_Ingot");
        map.put(4, "Pixlies:Lithium_Dust");
        map.put(5, "Pixlies:Zinc_Ingot");
        map.put(6, "Pixlies:Capacitor_Basic");
        map.put(7, "Pixlies:Capacitor_Basic");
        map.put(8, "Pixlies:Capacitor_Basic");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
