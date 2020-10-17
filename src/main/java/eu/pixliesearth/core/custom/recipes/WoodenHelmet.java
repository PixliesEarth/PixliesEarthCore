package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;

public class WoodenHelmet extends CustomRecipe {

    public WoodenHelmet() {

    }

    @Override
    public String getResultUUID() {
        return "Pixlies:Wooden_Helmet";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Minecraft:oak_planks");
        map.put(1, "Minecraft:oak_planks");
        map.put(2, "Minecraft:oak_planks");
        map.put(3, "Minecraft:oak_planks");
        map.put(4, "Minecraft:air");
        map.put(5, "Minecraft:oak_planks");
        map.put(6, "Minecraft:air");
        map.put(7, "Minecraft:air");
        map.put(8, "Minecraft:air");
        return map;
    }

}
