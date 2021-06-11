package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class EnergyInspector extends CustomRecipe {
    //@Override
    public EnergyInspector() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Energy_Inspector";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Steel_Block");
        map.put(1, "Pixlies:Platinum_Ingot");
        map.put(2, "Pixlies:Steel_Block");
        map.put(3, "Pixlies:Circuit_Board");
        map.put(4, "Pixlies:Block_Inspector");
        map.put(5, "Pixlies:Circuit_Board");
        map.put(6, "Pixlies:Steel_Block");
        map.put(7, "Pixlies:Platinum_Ingot");
        map.put(8, "Pixlies:Steel_Block");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
