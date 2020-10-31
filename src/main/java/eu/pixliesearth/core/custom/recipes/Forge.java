package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;


public class Forge extends CustomRecipe {
    //@Override
    public Forge() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Forge";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(1, MinecraftMaterial.IRON_BARS.getUUID());
        map.put(2, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(3, MinecraftMaterial.LAVA_BUCKET.getUUID());
        map.put(4, "Pixlies:Machine_Base");
        map.put(5, MinecraftMaterial.LAVA_BUCKET.getUUID());
        map.put(6, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(7, MinecraftMaterial.IRON_BARS.getUUID());
        map.put(8, MinecraftMaterial.IRON_INGOT.getUUID());
        return map;
    }
}
