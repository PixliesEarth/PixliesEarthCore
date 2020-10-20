package eu.pixliesearth.core.custom;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.NBTUtil;
import eu.pixliesearth.utils.NBTUtil.NBTTags;
import eu.pixliesearth.utils.Timer;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to create a machine</h3>
 *
 */
public class CustomCrafterMachine extends CustomMachine {
	// TODO: notes
	public static final List<Integer> craftSlots = Arrays.asList(0, 1, 2, 3, 9, 18, 27, 10, 11, 12, 19, 20, 21, 28, 29, 30);
	public static final List<Integer> resultSlots = Arrays.asList(5, 6, 7, 8, 17, 26, 35, 14, 15, 16, 23, 24, 25, 32, 33, 34);
	public static final List<Integer> progressSlots = Arrays.asList(36, 37, 38, 39, 40, 41, 42, 43, 44);
	
	/**
	 * Initialises the class
	 */
	public CustomCrafterMachine() {
		
	}
	/**
	 * Opens the custom inventory
	 * 
	 * @param player The {@link Player} who wants to open the {@link Inventory}
	 * @param location The {@link Location} of the {@link CustomMachine} that houses the {@link Inventory}
	 */
	@Override
	public void open(Player player, Location location) {
		Inventory i = CustomFeatureLoader.getLoader().getHandler().getInventoryFromLocation(location);
		ItemStack is = i.getItem(0);
		if (is==null) {
			player.openInventory(i);
		} else if (!isUnclickable(is)) {
			String id = CustomItemUtil.getUUIDFromItemStack(is);
			CustomRecipe r = getRecipeFromUUID(id);
			Inventory inv = getInventory2(r);
			player.openInventory(inv);
			CustomFeatureLoader.getLoader().getHandler().setInventoryFromLocation(location, inv);
		} else {
			player.openInventory(i);
		}
	}
	/**
	 * @return The {@link CustomCrafterMachine}'s {@link Inventory}
	 */
	@Override
	public Inventory getInventory() { 
		Inventory inv = Bukkit.createInventory(null, 6*9, getInventoryTitle());
		for (int i = 0; i < 6*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		Set<CustomRecipe> rs = CustomFeatureLoader.getLoader().getHandler().getRecipesFromUUID(getUUID());
		for (CustomRecipe r : rs)
			a(inv, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(r.getResultUUID())).addNBTTag("EXTRA", "RECIPE", NBTTagType.STRING).build());
		return inv;
	}
	/**
	 * Gets the next free slot and sets the item to it
	 */
	public boolean a(Inventory inv, ItemStack is) {
		final List<Integer> ints = Arrays.asList(10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43);
		for (int i : ints) {
			ItemStack is2 = inv.getItem(i);
			if (is==null || is.getType().equals(MinecraftMaterial.AIR.getMaterial())) {
				inv.setItem(i, is);
				return true;
			} else if (CustomItemUtil.getUUIDFromItemStack(is2).equalsIgnoreCase(CustomInventoryListener.getUnclickableItemUUID())) {
				inv.setItem(i, is);
				return true;
			}
			else 
				continue;
		}
		return false;
	}
	/**
	 * The book at the bottom left that says the ingredients needed
	 */
	public ItemStack getRecipeBook(CustomRecipe r) {
		return new ItemBuilder(MinecraftMaterial.BOOK.getMaterial()) {{
			addLoreLine("§b§lRecipe");
			if (r==null) {
				addLoreLine("§c§lRecipe invalid!");
			} else {
				Map<String, Integer> map = r.getAsUUIDToAmountMap();
				for (Entry<String, Integer> entry : map.entrySet()) {
					addLoreLine("§b"+Integer.toString(entry.getValue())+"x "+entry.getKey());
				}
			}
			addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING); // make item not click-able
		}}.build();
	}
	// TODO: notes
	public Inventory getInventory2(CustomRecipe r) {
		Inventory inv = Bukkit.createInventory(null, 6*9, getInventoryTitle());
		for (int i = 0; i < 6*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		for (int i : craftSlots)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(MinecraftMaterial.AIR.getUUID()));
		for (int i : resultSlots)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(MinecraftMaterial.AIR.getUUID()));
		inv.setItem(45, getRecipeBook(r));
		return inv;
	}

	// TODO: notes
	public static Set<ItemStack> getItemsInCraftingSection(Inventory inv) {
		Set<ItemStack> list = new HashSet<ItemStack>();
		for (int i : craftSlots) 
			if (inv.getItem(i)!=null || !inv.getItem(i).getType().equals(MinecraftMaterial.AIR.getMaterial())) 
				list.add(inv.getItem(i));
		return list;
	}
	// TODO: notes
	@Override
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
		if (map.get("TIMEREX")!=null && map.get("TIMEREN")!=null)
			CustomFeatureLoader.getLoader().getHandler().registerTimer(location, new Timer(Long.parseLong(map.get("TIMEREX")), Boolean.getBoolean(map.get("TIMEREN"))));
	}
	// TODO: notes
	@Override
	public HashMap<String, String> getSaveData(Inventory inventory, Timer timer) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (timer!=null) {
			map.put("TIMEREX", Long.toString(timer.getExpiry()));
			map.put("TIMEREN", Boolean.toString(timer.isEnded()));
		}
		return map;
	}
	/**
	 * Called when a player clicks on the {@link Inventory}
	 * 
	 * @param event The {@link InventoryClickEvent} that occurred
	 * @return If the event should be cancelled
	 */
	@Override
	public boolean InventoryClickEvent(InventoryClickEvent event) {
		if (event.isCancelled()) return true;
		ItemStack is = event.getCurrentItem();
		if (is==null || is.getType().equals(MinecraftMaterial.AIR.getMaterial())) return false;
		NBTTags tags = NBTUtil.getTagsFromItem(is);
		// Main GUI select recipe
		if (tags.getString("EXTRA")!=null) {
			if (tags.getString("EXTRA").equalsIgnoreCase("RECIPE")) {
				String id = CustomItemUtil.getUUIDFromItemStack(is);
				CustomRecipe r = getRecipeFromUUID(id);
				event.getWhoClicked().openInventory(getInventory2(r));
				event.getInventory().setItem(0, is);
				return true;
			}
		}
		return false;
	}
	// TODO: notes
	public CustomRecipe getRecipeFromUUID(String resultUUID) {
		for (CustomRecipe r : CustomFeatureLoader.getLoader().getHandler().getRecipesFromUUID(getUUID())) 
			if (r.getResultUUID().equalsIgnoreCase(resultUUID)) 
				return r;
		return null;
	}
	// TODO: notes
	public boolean isUnclickable(ItemStack is) {
		return CustomItemUtil.getUUIDFromItemStack(is).equalsIgnoreCase(CustomInventoryListener.getUnclickableItemUUID());
	}
}
