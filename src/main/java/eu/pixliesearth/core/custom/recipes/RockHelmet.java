package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

public class RockHelmet extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:Rock_Helmet";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.STONE.getUUID());
        map.put(1, MinecraftMaterial.STONE.getUUID());
        map.put(2, MinecraftMaterial.STONE.getUUID());
        map.put(3, MinecraftMaterial.STONE.getUUID());
        map.put(4, MinecraftMaterial.AIR.getUUID());
        map.put(5, MinecraftMaterial.STONE.getUUID());
        map.put(6, MinecraftMaterial.AIR.getUUID());
        map.put(7, MinecraftMaterial.AIR.getUUID());
        map.put(8, MinecraftMaterial.AIR.getUUID());
        return map;
    }

}
