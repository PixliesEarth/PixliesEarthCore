package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class PlatinumBlock extends CustomRecipe {
    
    public PlatinumBlock() {
        
    }
    
    public String getResultUUID() {
        return "Pixlies:Platinum_Block";
    }
    
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Platinum_Ingot");
        map.put(1, "Pixlies:Platinum_Ingot");
        map.put(2, "Pixlies:Platinum_Ingot");
        map.put(3, "Pixlies:Platinum_Ingot");
        map.put(4, "Pixlies:Platinum_Ingot");
        map.put(5, "Pixlies:Platinum_Ingot");
        map.put(6, "Pixlies:Platinum_Ingot");
        map.put(7, "Pixlies:Platinum_Ingot");
        map.put(8, "Pixlies:Platinum_Ingot");
        return map;
    }
}
