package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;



public class Backpack2 extends CustomRecipe {
    //@Override
    public Backpack2() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Backpack";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.LEATHER.getUUID());
        map.put(1, MinecraftMaterial.CHEST.getUUID());
        map.put(2, MinecraftMaterial.LEATHER.getUUID());
        map.put(3, MinecraftMaterial.RABBIT_HIDE.getUUID());
        map.put(4, MinecraftMaterial.IRON_NUGGET.getUUID());
        map.put(5, MinecraftMaterial.RABBIT_HIDE.getUUID());
        map.put(6, MinecraftMaterial.LEATHER.getUUID());
        map.put(7, MinecraftMaterial.RABBIT_HIDE.getUUID());
        map.put(8, MinecraftMaterial.LEATHER.getUUID());
        return map;
    }
}
