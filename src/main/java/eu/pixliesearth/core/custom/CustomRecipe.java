package eu.pixliesearth.core.custom;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import eu.pixliesearth.nations.entities.nation.Era;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>The base of a custom recipe!</h3>
 * 
 */
public class CustomRecipe {
	/**
	 * Initialises the class
	 */
	public CustomRecipe() {
		
	}
	// TODO: notes
	public String craftedInUUID() {
		return "Pixlies:Crafting_Table";
	}
	// TODO: notes
	public String getResultUUID() {
		return MinecraftMaterial.DIRT.getUUID();
	}
	// TODO: notes
	public int getResultAmount() {
		return 1;
	}
	// TODO: notes
	public Era getEraNeeded() {
		return Era.TRIBAL;
	}
	// TODO: notes
	public Map<Integer, String> getContentsList() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, null);
		map.put(1, null);
		map.put(2, null);
		map.put(3, null);
		map.put(4, null);
		map.put(5, null);
		map.put(6, null);
		map.put(7, null);
		map.put(8, null);
		return map;
	}
	
	public Long getCraftTime() {
		return null;
	}
	
	public Map<String, Integer> getAsUUIDToAmountMap() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Entry<Integer, String> entry : getContentsList().entrySet()) {
			Integer i = map.get(entry.getValue());
			if (i==null) {
				map.put(entry.getValue(), 1);
			} else {
				map.remove(entry.getValue());
				map.put(entry.getValue(), i+1);
			}
		}
		return map;
	}
}
