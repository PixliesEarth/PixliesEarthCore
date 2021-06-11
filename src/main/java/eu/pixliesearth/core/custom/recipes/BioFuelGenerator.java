package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class BioFuelGenerator extends CustomRecipe {
    //@Override
    public BioFuelGenerator() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Biofuel_Generator";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Titanium_Ingot");
        map.put(1, "Pixlies:Carbon_Steel_Ingot");
        map.put(2, "Pixlies:Titanium_Ingot");
        map.put(3, "Pixlies:Circuit_Board");
        map.put(4, "Pixlies:Machine_Base_Energy");
        map.put(5, "Pixlies:Circuit_Board");
        map.put(6, "Pixlies:Titanium_Ingot");
        map.put(7, "Pixlies:Carbon_Steel_Ingot");
        map.put(8, "Pixlies:Titanium_Ingot");
        return map;
    }

}
