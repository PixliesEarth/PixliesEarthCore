package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class CarbonSteelBlock extends CustomRecipe {
    
    public CarbonSteelBlock() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Carbon_Steel_Block";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Carbon_Steel_Ingot");
        map.put(1, "Pixlies:Carbon_Steel_Ingot");
        map.put(2, "Pixlies:Carbon_Steel_Ingot");
        map.put(3, "Pixlies:Carbon_Steel_Ingot");
        map.put(4, "Pixlies:Carbon_Steel_Ingot");
        map.put(5, "Pixlies:Carbon_Steel_Ingot");
        map.put(6, "Pixlies:Carbon_Steel_Ingot");
        map.put(7, "Pixlies:Carbon_Steel_Ingot");
        map.put(8, "Pixlies:Carbon_Steel_Ingot");
        return map;
    }
}
