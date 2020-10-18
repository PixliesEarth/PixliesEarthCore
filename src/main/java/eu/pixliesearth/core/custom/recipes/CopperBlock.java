package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;

public class CopperBlock extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:Copper_Block";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Copper_Ingot");
        map.put(1, "Pixlies:Copper_Ingot");
        map.put(2, "Pixlies:Copper_Ingot");
        map.put(3, "Pixlies:Copper_Ingot");
        map.put(4, "Pixlies:Copper_Ingot");
        map.put(5, "Pixlies:Copper_Ingot");
        map.put(6, "Pixlies:Copper_Ingot");
        map.put(7, "Pixlies:Copper_Ingot");
        map.put(8, "Pixlies:Copper_Ingot");
        return map;
    }

}
