package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;

public class TinBlock extends CustomRecipe {
	
	public TinBlock() {
		
	}
	
	public String getResultUUID() {
		return "Pixlies:Tin_Block";
	}
	
	public Map<Integer, String> getContentsList() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, "Pixlies:Tin_Ingot");
		map.put(1, "Pixlies:Tin_Ingot");
		map.put(2, "Pixlies:Tin_Ingot");
		map.put(3, "Pixlies:Tin_Ingot");
		map.put(4, "Pixlies:Tin_Ingot");
		map.put(5, "Pixlies:Tin_Ingot");
		map.put(6, "Pixlies:Tin_Ingot");
		map.put(7, "Pixlies:Tin_Ingot");
		map.put(8, "Pixlies:Tin_Ingot");
		return map;
	}
}
