package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class AluminumBlock extends CustomRecipe {
    
    public AluminumBlock() {
        
    }
    
    public String getResultUUID() {
        return "Pixlies:Aluminum_Block";
    }
    
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Aluminum_Ingot");
        map.put(1, "Pixlies:Aluminum_Ingot");
        map.put(2, "Pixlies:Aluminum_Ingot");
        map.put(3, "Pixlies:Aluminum_Ingot");
        map.put(4, "Pixlies:Aluminum_Ingot");
        map.put(5, "Pixlies:Aluminum_Ingot");
        map.put(6, "Pixlies:Aluminum_Ingot");
        map.put(7, "Pixlies:Aluminum_Ingot");
        map.put(8, "Pixlies:Aluminum_Ingot");
        return map;
    }
}
