package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;


public class ElectricalForge extends CustomRecipe {
    //@Override
    public ElectricalForge() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Forge_Electric";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.IRON_BLOCK.getUUID());
        map.put(1, "Machine:Forge");
        map.put(2, MinecraftMaterial.IRON_BLOCK.getUUID());
        map.put(3, "Pixlies:Capacitor_Advanced");
        map.put(4, "Pixlies:Machine_Base");
        map.put(5, "Pixlies:Capacitor_Advanced");
        map.put(6, MinecraftMaterial.IRON_BLOCK.getUUID());
        map.put(7, "Pixlies:Circuit_Board");
        map.put(8, MinecraftMaterial.IRON_BLOCK.getUUID());
        return map;
    }
}
