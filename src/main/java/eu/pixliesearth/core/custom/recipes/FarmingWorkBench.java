package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;


public class FarmingWorkBench extends CustomRecipe {
    //@Override
    public FarmingWorkBench() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:FarmingWorkBench";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(1, MinecraftMaterial.WHEAT_SEEDS.getUUID());
        map.put(2, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(3, MinecraftMaterial.OAK_PLANKS.getUUID());
        map.put(4, "Pixlies:Machine_Base");
        map.put(5, MinecraftMaterial.OAK_PLANKS.getUUID());
        map.put(6, MinecraftMaterial.OAK_PLANKS.getUUID());
        map.put(7, MinecraftMaterial.AIR.getUUID());
        map.put(8, MinecraftMaterial.OAK_PLANKS.getUUID());
        return map;
    }
}
