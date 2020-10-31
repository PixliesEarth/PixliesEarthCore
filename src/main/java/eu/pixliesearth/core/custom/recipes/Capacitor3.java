package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class Capacitor3 extends CustomRecipe {
    
    public Capacitor3() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Capacitor_Advanced";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.AIR.getUUID());
        map.put(1, "Pixlies:Copper_Block");
        map.put(2, MinecraftMaterial.AIR.getUUID());
        map.put(3, "Pixlies:Capacitor_Intermediate");
        map.put(4, "Pixlies:Silver_Ingot");
        map.put(5, "Pixlies:Capacitor_Intermediate");
        map.put(6, MinecraftMaterial.AIR.getUUID());
        map.put(7, "Pixlies:Copper_Block");
        map.put(8, MinecraftMaterial.AIR.getUUID());
        return map;
    }
}
