package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class Capacitor4 extends CustomRecipe {
    
    public Capacitor4() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Capacitor_Ultimate";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.AIR.getUUID());
        map.put(1, "Pixlies:Lithium_Ingot");
        map.put(2, MinecraftMaterial.REDSTONE.getUUID());
        map.put(3, "Pixlies:Capacitor_Advanced");
        map.put(4, "Pixlies:Solder_Dust");
        map.put(5, "Pixlies:Capacitor_Advanced");
        map.put(6, MinecraftMaterial.REDSTONE.getUUID());
        map.put(7, "Pixlies:Lithium_Ingot");
        map.put(8, MinecraftMaterial.AIR.getUUID());
        return map;
    }
}
