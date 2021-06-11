package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;

public class CarbonBlock extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:Carbon_Block";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Carbon");
        map.put(1, "Pixlies:Carbon");
        map.put(2, "Pixlies:Carbon");
        map.put(3, "Pixlies:Carbon");
        map.put(4, "Pixlies:Carbon");
        map.put(5, "Pixlies:Carbon");
        map.put(6, "Pixlies:Carbon");
        map.put(7, "Pixlies:Carbon");
        map.put(8, "Pixlies:Carbon");
        return map;
    }

}
