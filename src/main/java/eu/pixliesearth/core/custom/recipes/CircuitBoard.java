package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;



public class CircuitBoard extends CustomRecipe {
    //@Override
    public CircuitBoard() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Circuit_Board";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.GOLD_NUGGET.getUUID());
        map.put(1, "Pixlies:Capacitor_Basic");
        map.put(2, "Pixlies:Copper_Ingot");
        map.put(3, "Pixlies:Capacitor_Basic");
        map.put(4, MinecraftMaterial.REDSTONE_TORCH.getUUID());
        map.put(5, "Pixlies:Capacitor_Basic");
        map.put(6, "Pixlies:Solder_Ingot");
        map.put(7, MinecraftMaterial.IRON_NUGGET.getUUID());
        map.put(8, "Pixlies:Solder_Ingot");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
