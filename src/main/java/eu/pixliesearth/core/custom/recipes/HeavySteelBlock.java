package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class HeavySteelBlock extends CustomRecipe {
    
    public HeavySteelBlock() {
        
    }
    
    public String getResultUUID() {
        return "Pixlies:Heavy_Steel_Block";
    }
    
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Heavy_Steel_Ingot");
        map.put(1, "Pixlies:Heavy_Steel_Ingot");
        map.put(2, "Pixlies:Heavy_Steel_Ingot");
        map.put(3, "Pixlies:Heavy_Steel_Ingot");
        map.put(4, "Pixlies:Heavy_Steel_Ingot");
        map.put(5, "Pixlies:Heavy_Steel_Ingot");
        map.put(6, "Pixlies:Heavy_Steel_Ingot");
        map.put(7, "Pixlies:Heavy_Steel_Ingot");
        map.put(8, "Pixlies:Heavy_Steel_Ingot");
        return map;
    }
}
