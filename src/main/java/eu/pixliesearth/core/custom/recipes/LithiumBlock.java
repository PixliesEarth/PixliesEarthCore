package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class LithiumBlock extends CustomRecipe {
    
    public LithiumBlock() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Lithium_Block";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Lithium_Ingot");
        map.put(1, "Pixlies:Lithium_Ingot");
        map.put(2, "Pixlies:Lithium_Ingot");
        map.put(3, "Pixlies:Lithium_Ingot");
        map.put(4, "Pixlies:Lithium_Ingot");
        map.put(5, "Pixlies:Lithium_Ingot");
        map.put(6, "Pixlies:Lithium_Ingot");
        map.put(7, "Pixlies:Lithium_Ingot");
        map.put(8, "Pixlies:Lithium_Ingot");
        return map;
    }
}
