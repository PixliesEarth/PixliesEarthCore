package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class Refrigerator extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Machine:Refrigerator";
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.IRON_BARS.getUUID());
        map.put(1, MinecraftMaterial.IRON_BARS.getUUID());
        map.put(2, MinecraftMaterial.IRON_BARS.getUUID());
        map.put(3, MinecraftMaterial.IRON_BLOCK.getUUID());
        map.put(4, "Pixlies:Machine_Base_Energy");
        map.put(5, MinecraftMaterial.IRON_BLOCK.getUUID());
        map.put(6, MinecraftMaterial.IRON_BARS.getUUID());
        map.put(7, MinecraftMaterial.IRON_BARS.getUUID());
        map.put(8, MinecraftMaterial.IRON_BARS.getUUID());
        return map;
    }

}
