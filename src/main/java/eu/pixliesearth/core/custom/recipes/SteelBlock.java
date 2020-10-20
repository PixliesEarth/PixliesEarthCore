package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import java.util.HashMap;
import java.util.Map;

public class SteelBlock extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:Steel_Block";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Steel_Ingot");
        map.put(1, "Pixlies:Steel_Ingot");
        map.put(2, "Pixlies:Steel_Ingot");
        map.put(3, "Pixlies:Steel_Ingot");
        map.put(4, "Pixlies:Steel_Ingot");
        map.put(5, "Pixlies:Steel_Ingot");
        map.put(6, "Pixlies:Steel_Ingot");
        map.put(7, "Pixlies:Steel_Ingot");
        map.put(8, "Pixlies:Steel_Ingot");
        return map;
    }

}