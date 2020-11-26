package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class EnergyCubeBasic extends CustomRecipe {
    //@Override
    public EnergyCubeBasic() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Energy_Cube_Basic";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Battery");
        map.put(1, "Pixlies:Capacitor_Basic");
        map.put(2, "Pixlies:Circuit_Board");
        map.put(3, "Pixlies:Capacitor_Basic");
        map.put(4, "Pixlies:Machine_Base_Energy");
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
