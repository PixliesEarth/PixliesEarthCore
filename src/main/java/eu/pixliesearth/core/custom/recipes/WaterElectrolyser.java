package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;

public class WaterElectrolyser extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Machine:Electrolyser_Water";
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Steel_Ingot");
        map.put(1, "Pixlies:Circuit_Board");
        map.put(2, "Pixlies:Steel_Ingot");
        map.put(3, "Pixlies:Canister");
        map.put(4, "Pixlies:Machine_Base_Energy");
        map.put(5, "Pixlies:Canister");
        map.put(6, "Pixlies:Steel_Ingot");
        map.put(7, "Pixlies:Canister");
        map.put(8, "Pixlies:Steel_Ingot");
        return map;
    }

}
