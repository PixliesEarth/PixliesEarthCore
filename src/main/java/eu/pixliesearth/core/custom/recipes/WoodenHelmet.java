package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

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
        map.put(0, MinecraftMaterial.OAK_PLANKS.getUUID());
        map.put(1, MinecraftMaterial.OAK_PLANKS.getUUID());
        map.put(2, MinecraftMaterial.OAK_PLANKS.getUUID());
        map.put(3, MinecraftMaterial.OAK_PLANKS.getUUID());
        map.put(4, MinecraftMaterial.AIR.getUUID());
        map.put(5, MinecraftMaterial.OAK_PLANKS.getUUID());
        map.put(6, MinecraftMaterial.AIR.getUUID());
        map.put(7, MinecraftMaterial.AIR.getUUID());
        map.put(8, MinecraftMaterial.AIR.getUUID());
        return map;
    }

}
