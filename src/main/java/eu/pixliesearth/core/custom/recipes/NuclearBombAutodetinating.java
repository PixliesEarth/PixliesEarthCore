package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;



public class NuclearBombAutodetinating extends CustomRecipe {
    //@Override
    public NuclearBombAutodetinating() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Nuclear_Bomb_Autodetinating";
    }
    @Override 
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.REDSTONE.getUUID());
        map.put(1, MinecraftMaterial.REDSTONE.getUUID());
        map.put(2, MinecraftMaterial.REDSTONE.getUUID());
        map.put(3, MinecraftMaterial.REDSTONE.getUUID());
        map.put(4, "Pixlies:Nuclear_Bomb");
        map.put(5, MinecraftMaterial.REDSTONE.getUUID());
        map.put(6, MinecraftMaterial.REDSTONE.getUUID());
        map.put(7, MinecraftMaterial.REDSTONE.getUUID());
        map.put(8, MinecraftMaterial.REDSTONE.getUUID());
        return map;
    }
}
