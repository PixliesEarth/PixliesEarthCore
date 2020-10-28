package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class CastIronBlock extends CustomRecipe {
    
    public CastIronBlock() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Cast_Iron_Block";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Cast_Iron_Ingot");
        map.put(1, "Pixlies:Cast_Iron_Ingot");
        map.put(2, "Pixlies:Cast_Iron_Ingot");
        map.put(3, "Pixlies:Cast_Iron_Ingot");
        map.put(4, "Pixlies:Cast_Iron_Ingot");
        map.put(5, "Pixlies:Cast_Iron_Ingot");
        map.put(6, "Pixlies:Cast_Iron_Ingot");
        map.put(7, "Pixlies:Cast_Iron_Ingot");
        map.put(8, "Pixlies:Cast_Iron_Ingot");
        return map;
    }
}
