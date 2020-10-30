package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class AluminiumBlock extends CustomRecipe {
    
    public AluminiumBlock() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Aluminium_Block";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Aluminium_Ingot");
        map.put(1, "Pixlies:Aluminium_Ingot");
        map.put(2, "Pixlies:Aluminium_Ingot");
        map.put(3, "Pixlies:Aluminium_Ingot");
        map.put(4, "Pixlies:Aluminium_Ingot");
        map.put(5, "Pixlies:Aluminium_Ingot");
        map.put(6, "Pixlies:Aluminium_Ingot");
        map.put(7, "Pixlies:Aluminium_Ingot");
        map.put(8, "Pixlies:Aluminium_Ingot");
        return map;
    }
}
