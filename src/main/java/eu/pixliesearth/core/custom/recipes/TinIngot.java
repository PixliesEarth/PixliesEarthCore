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
		map.put(1, null);
		map.put(2, null);
		map.put(3, null);
		map.put(4, null);
		map.put(5, "Pixlies:Tin_Block");
		map.put(6, null);
		map.put(7, null);
		map.put(8, null);
		map.put(9, null);
		return map;
	}
}
