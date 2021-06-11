package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;

public class PlasticSoftBlock extends CustomRecipe {
    
    public PlasticSoftBlock() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Plastic_Soft_Block";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Plastic_Soft");
        map.put(1, "Pixlies:Plastic_Soft");
        map.put(2, "Pixlies:Plastic_Soft");
        map.put(3, "Pixlies:Plastic_Soft");
        map.put(4, "Pixlies:Plastic_Soft");
        map.put(5, "Pixlies:Plastic_Soft");
        map.put(6, "Pixlies:Plastic_Soft");
        map.put(7, "Pixlies:Plastic_Soft");
        map.put(8, "Pixlies:Plastic_Soft");
        return map;
    }
}
