package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;



public class ChargerBasic extends CustomRecipe {
    //@Override
    public ChargerBasic() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Charger_Basic";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Battery");
        map.put(1, "Pixlies:Capacitor_Basic");
        map.put(2, "Pixlies:Circuit_Board");
        map.put(3, "Pixlies:Capacitor_Basic");
        map.put(4, "Machine:Energy_Cube_Basic");
        map.put(5, "Pixlies:Capacitor_Basic");
        map.put(6, "Pixlies:Circuit_Board");
        map.put(7, "Pixlies:Capacitor_Basic");
        map.put(8, "Pixlies:Battery");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}