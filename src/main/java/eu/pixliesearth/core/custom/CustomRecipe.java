package eu.pixliesearth.core.custom;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>The base of a custom recipe!</h3>
 * 
 */
public class CustomRecipe {
	
	public CustomRecipe() {
		
	}
	
	public String getResultUUID() {
		return MinecraftMaterial.DIRT.getUUID();
	}
	
	public Map<Integer, String> getContentsList() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, null);
		map.put(2, null);
		map.put(3, null);
		map.put(4, null);
		map.put(5, null);
		map.put(6, null);
		map.put(7, null);
		map.put(8, null);
		map.put(9, null);
		return map;
	}
}
