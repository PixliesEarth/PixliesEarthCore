package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;


public class OreCrusher extends CustomRecipe {
    //@Override
    public OreCrusher() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Ore_Crusher";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.COBBLESTONE.getUUID());
        map.put(1, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(2, MinecraftMaterial.COBBLESTONE.getUUID());
        map.put(3, MinecraftMaterial.PISTON.getUUID());
        map.put(4, "Pixlies:Machine_Base");
        map.put(5, MinecraftMaterial.PISTON.getUUID());
        map.put(6, MinecraftMaterial.COBBLESTONE.getUUID());
        map.put(7, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(8, MinecraftMaterial.COBBLESTONE.getUUID());
        return map;
    }
}
