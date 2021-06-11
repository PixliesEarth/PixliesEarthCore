package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class GunWorkbench extends CustomRecipe {
    //@Override
    public GunWorkbench() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:GunWorkbench";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Aluminium_Ingot");
        map.put(1, "Pixlies:Heavy_Steel_Ingot");
        map.put(2, "Pixlies:Aluminium_Ingot");
        map.put(3, "Pixlies:Circuit_Board");
        map.put(4, "Pixlies:Machine_Base");
        map.put(5, "Pixlies:Circuit_Board");
        map.put(6, "Pixlies:Aluminium_Ingot");
        map.put(7, "Pixlies:Heavy_Steel_Ingot");
        map.put(8, "Pixlies:Aluminium_Ingot");
        return map;
    }

}
