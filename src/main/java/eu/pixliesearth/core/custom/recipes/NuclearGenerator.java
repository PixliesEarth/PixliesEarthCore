package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class NuclearGenerator extends CustomRecipe {
    //@Override
    public NuclearGenerator() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Nuclear_Generator";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Platinum_Block");
        map.put(1, MinecraftMaterial.NETHER_STAR.getUUID());
        map.put(2, "Pixlies:Titanium_Block");
        map.put(3, MinecraftMaterial.NETHERITE_INGOT.getUUID());
        map.put(4, "Pixlies:Energy_Cube");
        map.put(5, MinecraftMaterial.NETHERITE_INGOT.getUUID());
        map.put(6, "Pixlies:Cobalt_Block");
        map.put(7, MinecraftMaterial.NETHER_STAR.getUUID());
        map.put(8, "Pixlies:Steel_Block");
        return map;
    }
}
