package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;



public class SteelIngot2 extends CustomRecipe {
    
    public SteelIngot2() {
        
    }
    
    @Override
    public String getResultUUID() {
        return "Pixlies:Steel_Ingot";
    }
    
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Steel_Dust");
        return map;
    }
    
    @Override
    public Long getCraftTime() {
    	return 4500l;
    }
}
