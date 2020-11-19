package eu.pixliesearth.core.custom.interfaces;

import java.util.Map;

import eu.pixliesearth.core.custom.MinecraftMaterial;

public interface ILiquidable {
	
	public Map<String, Integer> getLiquidCapacities();
	
	public static final String waterID = MinecraftMaterial.WATER.getUUID();
	public static final String lavaID = MinecraftMaterial.LAVA.getUUID();
}
