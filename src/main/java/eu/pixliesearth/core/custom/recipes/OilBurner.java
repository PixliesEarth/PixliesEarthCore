package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class OilBurner extends CustomRecipe {
    //@Override
    public OilBurner() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Oil_Burner";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Heavy_Steel_Ingot");
        map.put(1, "Pixlies:Carbon_Block");
        map.put(2, "Pixlies:Heavy_Steel_Ingot");
        map.put(3, "Pixlies:Circuit_Board");
        map.put(4, "Pixlies:Machine_Base_Energy");
        map.put(5, "Pixlies:Circuit_Board");
        map.put(6, "Pixlies:Heavy_Steel_Ingot");
        map.put(7, "Pixlies:Titanium_Ingot");
        map.put(8, "Pixlies:Heavy_Steel_Ingot");
        return map;
    }

}
