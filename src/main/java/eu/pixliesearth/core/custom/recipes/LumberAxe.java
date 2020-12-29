package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.core.custom.skills.SkillHandler;

public class LumberAxe extends CustomRecipe {
	
	@Override
	public String getResultUUID() {
		return "Pixlies:Lumber_Axe";
	}
	
	@Override
	public int getResultAmount() {
		return 1;
	}
	
	@Override
	public Map<Integer, String> getContentsList() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, MinecraftMaterial.SPRUCE_LOG.getUUID());
		map.put(1, MinecraftMaterial.BIRCH_LOG.getUUID());
		map.put(2, MinecraftMaterial.AIR.getUUID());
		map.put(3, MinecraftMaterial.OAK_LOG.getUUID());
		map.put(4, MinecraftMaterial.WOODEN_AXE.getUUID());
		map.put(5, MinecraftMaterial.AIR.getUUID());
		map.put(6, MinecraftMaterial.AIR.getUUID());
		map.put(7, MinecraftMaterial.STICK.getUUID());
		map.put(8, MinecraftMaterial.AIR.getUUID());
		return map;
	}
	
	@Override
	public boolean canCraft(UUID crafter) {
		return (SkillHandler.getSkillHandler().getLevelOf(crafter, "Pixlies:Lumbering")>=10);
	}
	
}
