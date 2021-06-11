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
public class EnergyMachineBase extends CustomRecipe {
    
    public EnergyMachineBase() {
        
    }
    
    @Override
    public String getResultUUID() {
        return "Pixlies:Machine_Base_Energy";
    }
    
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.AIR.getUUID());
        map.put(1, MinecraftMaterial.REDSTONE.getUUID());
        map.put(2, MinecraftMaterial.AIR.getUUID());
        map.put(3, MinecraftMaterial.REDSTONE.getUUID());
        map.put(4, "Pixlies:Machine_Base");
        map.put(5, MinecraftMaterial.REDSTONE.getUUID());
        map.put(6, MinecraftMaterial.AIR.getUUID());
        map.put(7, MinecraftMaterial.REDSTONE.getUUID());
        map.put(8, MinecraftMaterial.AIR.getUUID());
        return map;
    }
}
