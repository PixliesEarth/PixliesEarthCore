package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class TungstenBlock extends CustomRecipe {
    
    public TungstenBlock() {
        
    }
    
    public String getResultUUID() {
        return "Pixlies:Tungsten_Block";
    }
    
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Tungsten_Ingot");
        map.put(1, "Pixlies:Tungsten_Ingot");
        map.put(2, "Pixlies:Tungsten_Ingot");
        map.put(3, "Pixlies:Tungsten_Ingot");
        map.put(4, "Pixlies:Tungsten_Ingot");
        map.put(5, "Pixlies:Tungsten_Ingot");
        map.put(6, "Pixlies:Tungsten_Ingot");
        map.put(7, "Pixlies:Tungsten_Ingot");
        map.put(8, "Pixlies:Tungsten_Ingot");
        return map;
    }
}
