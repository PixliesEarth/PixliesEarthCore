package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class TitaniumBlock extends CustomRecipe {
    
    public TitaniumBlock() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Titanium_Block";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Titanium_Ingot");
        map.put(1, "Pixlies:Titanium_Ingot");
        map.put(2, "Pixlies:Titanium_Ingot");
        map.put(3, "Pixlies:Titanium_Ingot");
        map.put(4, "Pixlies:Titanium_Ingot");
        map.put(5, "Pixlies:Titanium_Ingot");
        map.put(6, "Pixlies:Titanium_Ingot");
        map.put(7, "Pixlies:Titanium_Ingot");
        map.put(8, "Pixlies:Titanium_Ingot");
        return map;
    }
}
