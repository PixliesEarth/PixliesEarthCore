package eu.pixliesearth.core.custom;

import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.nations.entities.nation.Era;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

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
	/**
	 * How long in ms the crafting process should take
	 * 
	 * @apiNote If null it will default to zero
	 */
	public Long getCraftTime() {
		return null;
	}
	/**
	 * The cost of the crafting process in terms of watts
	 * 
	 * @apiNote If null it will default to zero
	 * 
	 * @apiNote Will only charge energy if in an energy machine
	 */
	public Double getEnergyCost() {
		return null;
	}
	
	public Map<String, Integer> getAsUUIDToAmountMap() {
		Map<String, Integer> map = new ConcurrentHashMap<String, Integer>();
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
	
	public ItemStack getRecipeBook() {
		return new ItemBuilder(MinecraftMaterial.BOOK.getMaterial()) {{
			setDisplayName("§b§lRecipe");
			Map<String, Integer> map = getAsUUIDToAmountMap();
			for (Entry<String, Integer> entry : map.entrySet()) {
				if (CustomItemUtil.getItemStackFromUUID(entry.getKey()) == null) continue;
				addLoreLine("§b"+Integer.toString(entry.getValue())+"x " + entry.getKey());
			}
			addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING); // make item not click-able
		}}.build();
	}
}
