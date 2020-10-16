package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;

public class TinIngot extends CustomRecipe {
	
	public TinIngot() {
		
	}
	
	@Override
	public String getResultUUID() {
		return "Pixlies:Tin_Ingot";
	}
	
	@Override
	public int getResultAmount() {
		return 9;
	}
	
	@Override
	public Map<Integer, String> getContentsList() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "Minecraft:air");
		map.put(2, "Minecraft:air");
		map.put(3, "Minecraft:air");
		map.put(4, "Minecraft:air");
		map.put(5, "Pixlies:Tin_Block");
		map.put(6, "Minecraft:air");
		map.put(7, "Minecraft:air");
		map.put(8, "Minecraft:air");
		map.put(9, "Minecraft:air");
		return map;
	}
}
