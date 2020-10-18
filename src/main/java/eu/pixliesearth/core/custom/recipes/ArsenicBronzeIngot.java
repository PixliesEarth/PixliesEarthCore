package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class ArsenicBronzeIngot extends CustomRecipe {
	
	public ArsenicBronzeIngot() {
		
	}
	
	@Override
	public String getResultUUID() {
		return "Pixlies:Arsenic_Bronze_Ingot";
	}
	
	@Override
	public int getResultAmount() {
		return 9;
	}
	
	@Override
	public Map<Integer, String> getContentsList() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, MinecraftMaterial.AIR.getUUID());
		map.put(1, MinecraftMaterial.AIR.getUUID());
		map.put(2, MinecraftMaterial.AIR.getUUID());
		map.put(3, MinecraftMaterial.AIR.getUUID());
		map.put(4, "Pixlies:Arsenic_Bronze_Block");
		map.put(5, MinecraftMaterial.AIR.getUUID());
		map.put(6, MinecraftMaterial.AIR.getUUID());
		map.put(7, MinecraftMaterial.AIR.getUUID());
		map.put(8, MinecraftMaterial.AIR.getUUID());
		return map;
	}
}
