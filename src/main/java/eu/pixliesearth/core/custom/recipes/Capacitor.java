package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class Capacitor extends CustomRecipe {
    
    public Capacitor() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Capacitor_Basic";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.AIR.getUUID());
        map.put(1, MinecraftMaterial.GOLD_NUGGET.getUUID());
        map.put(2, MinecraftMaterial.REDSTONE.getUUID());
        map.put(3, MinecraftMaterial.GOLD_NUGGET.getUUID());
        map.put(4, "Pixlies:Copper_Ingot");
        map.put(5, MinecraftMaterial.GOLD_NUGGET.getUUID());
        map.put(6, MinecraftMaterial.REDSTONE.getUUID());
        map.put(7, MinecraftMaterial.GOLD_NUGGET.getUUID());
        map.put(8, MinecraftMaterial.AIR.getUUID());
        return map;
    }
}
