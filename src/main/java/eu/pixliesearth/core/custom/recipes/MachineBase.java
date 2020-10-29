package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author BradBot_1
 *
 */
public class MachineBase extends CustomRecipe {
    
    public MachineBase() {
        
    }
    
    @Override
    public String getResultUUID() {
        return "Pixlies:Machine_Base";
    }
    
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.LAPIS_LAZULI.getUUID());
        map.put(1, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(2, MinecraftMaterial.LAPIS_LAZULI.getUUID());
        map.put(3, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(4, MinecraftMaterial.REDSTONE_BLOCK.getUUID());
        map.put(5, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(6, MinecraftMaterial.LAPIS_LAZULI.getUUID());
        map.put(7, MinecraftMaterial.IRON_INGOT.getUUID());
        map.put(8, MinecraftMaterial.LAPIS_LAZULI.getUUID());
        return map;
    }
}
