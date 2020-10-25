package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class CobaltBlock extends CustomRecipe {
    
    public CobaltBlock() {
        
    }
    
    public String getResultUUID() {
        return "Pixlies:Cobalt_Block";
    }
    
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Cobalt_Ingot");
        map.put(1, "Pixlies:Cobalt_Ingot");
        map.put(2, "Pixlies:Cobalt_Ingot");
        map.put(3, "Pixlies:Cobalt_Ingot");
        map.put(4, "Pixlies:Cobalt_Ingot");
        map.put(5, "Pixlies:Cobalt_Ingot");
        map.put(6, "Pixlies:Cobalt_Ingot");
        map.put(7, "Pixlies:Cobalt_Ingot");
        map.put(8, "Pixlies:Cobalt_Ingot");
        return map;
    }
}
