package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.core.custom.skills.SkillHandler;

public class ElectronicLumberAxe extends CustomRecipe {
	
	@Override
	public String getResultUUID() {
		return "Pixlies:Lumber_Axe_Electric";
	}
	
	@Override
	public int getResultAmount() {
		return 1;
	}
	
	@Override
	public Map<Integer, String> getContentsList() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, "Pixlies:Platinum_Ingot");
		map.put(1, "Pixlies:Titanium_Ingot");
		map.put(2, MinecraftMaterial.AIR.getUUID());
		map.put(3, "Pixlies:Titanium_Ingot");
		map.put(4, "Pixlies:Heavy_Steel_Axe");
		map.put(5, MinecraftMaterial.AIR.getUUID());
		map.put(6, "Pixlies:Circuit_Board");
		map.put(7, "Pixlies:Plastic_Hard");
		map.put(8, "Pixlies:Capacitor_Advanced");
		return map;
	}
	
	@Override
	public boolean canCraft(UUID crafter) {
		return (SkillHandler.getSkillHandler().getLevelOf(crafter, "Pixlies:Lumbering")>=35);
	}
	
}
