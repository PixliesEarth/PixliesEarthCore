package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class Waffle extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Food:Waffle";
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Food:Flour");
        map.put(1, MinecraftMaterial.MILK_BUCKET.getUUID());
        map.put(2, MinecraftMaterial.SUGAR.getUUID());
        map.put(3, MinecraftMaterial.AIR.getUUID());
        map.put(4, MinecraftMaterial.AIR.getUUID());
        map.put(5, MinecraftMaterial.AIR.getUUID());
        map.put(6, MinecraftMaterial.AIR.getUUID());
        map.put(7, MinecraftMaterial.AIR.getUUID());
        map.put(8, MinecraftMaterial.AIR.getUUID());
        return map;
    }

}
