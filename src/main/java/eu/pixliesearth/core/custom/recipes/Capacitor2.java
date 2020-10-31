package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class Capacitor2 extends CustomRecipe {
    
    public Capacitor2() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Capacitor_Intermediate";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.AIR.getUUID());
        map.put(1, "Pixlies:Silver_Ingot");
        map.put(2, MinecraftMaterial.AIR.getUUID());
        map.put(3, "Pixlies:Capacitor_Basic");
        map.put(4, "Pixlies:Copper_Block");
        map.put(5, "Pixlies:Capacitor_Basic");
        map.put(6, MinecraftMaterial.AIR.getUUID());
        map.put(7, "Pixlies:Silver_Ingot");
        map.put(8, MinecraftMaterial.AIR.getUUID());
        return map;
    }
}
