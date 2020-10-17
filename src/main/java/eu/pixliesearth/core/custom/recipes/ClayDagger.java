package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;

public class ClayDagger extends CustomRecipe {

    public ClayDagger() {

    }

    @Override
    public String getResultUUID() {
        return "Pixlies:Clay_Dagger";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Minecraft:air");
        map.put(1, "Minecraft:clay_ball");
        map.put(2, "Minecraft:air");
        map.put(3, "Minecraft:air");
        map.put(4, "Minecraft:clay_ball");
        map.put(5, "Minecraft:air");
        map.put(6, "Minecraft:air");
        map.put(7, "Minecraft:stick");
        map.put(8, "Minecraft:air");
        return map;
    }

}
