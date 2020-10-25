package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class LeadBlock extends CustomRecipe {
    
    public LeadBlock() {
        
    }
    
    public String getResultUUID() {
        return "Pixlies:Lead_Block";
    }
    
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Lead_Ingot");
        map.put(1, "Pixlies:Lead_Ingot");
        map.put(2, "Pixlies:Lead_Ingot");
        map.put(3, "Pixlies:Lead_Ingot");
        map.put(4, "Pixlies:Lead_Ingot");
        map.put(5, "Pixlies:Lead_Ingot");
        map.put(6, "Pixlies:Lead_Ingot");
        map.put(7, "Pixlies:Lead_Ingot");
        map.put(8, "Pixlies:Lead_Ingot");
        return map;
    }
}
