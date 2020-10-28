package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class StainlessSteelBlock extends CustomRecipe {
    
    public StainlessSteelBlock() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Stainless_Steel_Block";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Stainless_Steel_Ingot");
        map.put(1, "Pixlies:Stainless_Steel_Ingot");
        map.put(2, "Pixlies:Stainless_Steel_Ingot");
        map.put(3, "Pixlies:Stainless_Steel_Ingot");
        map.put(4, "Pixlies:Stainless_Steel_Ingot");
        map.put(5, "Pixlies:Stainless_Steel_Ingot");
        map.put(6, "Pixlies:Stainless_Steel_Ingot");
        map.put(7, "Pixlies:Stainless_Steel_Ingot");
        map.put(8, "Pixlies:Stainless_Steel_Ingot");
        return map;
    }
}
