package eu.pixliesearth.core.custom;

import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.*;
import eu.pixliesearth.utils.Timer;
import eu.pixliesearth.utils.NBTUtil.NBTTags;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

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
		if (i==null) {
			player.sendMessage("This machine has no inventory!");
			return;
		}
		ItemStack is = i.getItem(47);
		if (is==null) {
			player.openInventory(i);
		} else if (!isUnclickable(is)) {
			if (is.getType().equals(Material.BARRIER)) {
				Inventory inv = getInventory();
				player.openInventory(inv);
				CustomFeatureLoader.getLoader().getHandler().setInventoryFromLocation(location, inv);
			} else {
				String id = CustomItemUtil.getUUIDFromItemStack(is);
				CustomRecipe r = getRecipeFromUUID(id);
				Inventory inv = getInventory2(r);
				player.openInventory(inv);
				CustomFeatureLoader.getLoader().getHandler().setInventoryFromLocation(location, inv);
			}
		} else {
			player.openInventory(i);
		}
	}
	// TODO: notes
	@Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		if (inv==null || loc==null) return;
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (inv.getItem(0)==null || !isUnclickable(inv.getItem(0))) {
			CustomRecipe r = getRecipeFromUUID(CustomItemUtil.getUUIDFromItemStack(inv.getItem(49)));
			if (r==null) return;
			if (timer==null) { // No timer
				if (r.getCraftTime()==null) {
					craft(loc, inv, r);
				} else {
					h.registerTimer(loc, new Timer(r.getCraftTime()));
				}
			} else {
				if (timer.hasExpired()) { // Timer ended
					h.unregisterTimer(loc);
					craft(loc, inv, r);
				} else {
					// Do nothing as we want to do stuff when timer has ended
				}
			}
		}
	}
	// TODO: notes
	public boolean craft(Location loc, Inventory inv, CustomRecipe r) {
		Set<ItemStack> items = getItemsInCraftingSection(inv);
		if (items == null || items.isEmpty()) return false;
		Map<String, Integer> m = new ConcurrentHashMap<String, Integer>();
		for (ItemStack is : items) {
			Integer i = m.get(CustomItemUtil.getUUIDFromItemStack(is));
			if (i==null || i==0) {
				m.put(CustomItemUtil.getUUIDFromItemStack(is), is.getAmount());
			} else {
				m.remove(CustomItemUtil.getUUIDFromItemStack(is));
				m.put(CustomItemUtil.getUUIDFromItemStack(is), i+is.getAmount());
			}
		}
		if (m==null || m.isEmpty()) return false;
		Map<String, Integer> m2 = r.getAsUUIDToAmountMap(); // Recipe map
		if (m2==null || m2.isEmpty()) return false;
		Map<String, Integer> m3 = new ConcurrentHashMap<String, Integer>(); // Left over items
		for (Entry<String, Integer> entry : m2.entrySet()) {
			if (!m.containsKey(entry.getKey())) return false;
			Integer i = m.get(entry.getKey());
			if (i==null || i==0) {
				return false; // Don't have the materials to craft it
			} else {
				if (entry.getValue()>i) return false; // Don't have the materials to craft it
				m.remove(entry.getKey());
				m2.remove(entry.getKey());
				m3.put(entry.getKey(), i-entry.getValue());
			}
		}
		for (Entry<String, Integer> entry : m.entrySet()) 
			m3.put(entry.getKey(), entry.getValue());
		setMapToCraftSlots(loc, inv, m3); // Give extras back
		addToResultSlots(loc, inv, CustomItemUtil.getItemStackFromUUID(r.getResultUUID())); // Give result
		return true;
	}
	
	public void setMapToCraftSlots(Location loc, Inventory inv, Map<String, Integer> map) {
		for (int i : craftSlots) 
			inv.clear(i);
		for (Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue()>64) {
				int a = entry.getValue();
				while (a!=0) {
					if (entry.getValue()>64) {
						int i2 = getNextFreeSlotInCrafting(inv);
						if (i2==-1) 
							loc.getWorld().dropItemNaturally(loc, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(entry.getKey())).setAmount(64).build());
						else 
							inv.setItem(i2, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(entry.getKey())).setAmount(64).build());
						a -= 64; // decrease the integer a by the amount given
					} else {
						int i2 = getNextFreeSlotInCrafting(inv);
						if (i2==-1) 
							loc.getWorld().dropItemNaturally(loc, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(entry.getKey())).setAmount(a).build());
						else 
							inv.setItem(i2, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(entry.getKey())).setAmount(a).build());
						a = 0;
					}
				}
			} else {
				int i2 = getNextFreeSlotInCrafting(inv);
				if (i2==-1) 
					loc.getWorld().dropItemNaturally(loc, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(entry.getKey())).setAmount(entry.getValue()).build());
				else 
					inv.setItem(i2, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(entry.getKey())).setAmount(entry.getValue()).build());
			}
		}
	}
	
	public int getNextFreeSlotInCrafting(Inventory inv) {
		for (int i : craftSlots) 
			if (inv.getItem(i)==null || inv.getItem(i).getType().equals(Material.AIR)) 
				return i;
		return -1;
	}
	// TODO: make this stack the same items
	public boolean addToResultSlots(Location loc, Inventory inv, ItemStack is) {
		for (int i : resultSlots) {
			if (inv.getItem(i)==null || inv.getItem(i).getType().equals(Material.AIR)) {
				inv.setItem(i, is);
				return true;
			}
		}
		if (loc==null) return false;
		Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
			@Override
			public void run() {
				loc.getWorld().dropItemNaturally(loc, is);
			}
		}, 1L);
		return true;
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
			setDisplayName("§b§lRecipe");
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
		inv.setItem(53, new ItemBuilder(MinecraftMaterial.BARRIER.getMaterial()).setDisplayName("§cClose").addNBTTag("EXTRA", "CLOSE", NBTTagType.STRING).build()); // Close item (back)
		ItemStack is = CustomItemUtil.getItemStackFromUUID(r.getResultUUID());
		inv.setItem(49, new ItemBuilder((is==null) ? CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()) : is).setAmount(1).addNBTTag("EXTRA", "RECIPERESULTITEM", NBTTagType.STRING).build());
		return inv;
	}

	// TODO: notes
	public static Set<ItemStack> getItemsInCraftingSection(Inventory inv) {
		Set<ItemStack> list = new HashSet<ItemStack>();
		for (int i : craftSlots) 
			if (inv.getItem(i)==null || inv.getItem(i).getType().equals(MinecraftMaterial.AIR.getMaterial())) 
				continue;
			else 
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
	public HashMap<String, String> getSaveData(Location location, Inventory inventory, Timer timer) {
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
				//String id = CustomItemUtil.getUUIDFromItemStack(is);
				//CustomRecipe r = getRecipeFromUUID(id);
				//event.getWhoClicked().openInventory(getInventory2(r));
				event.getWhoClicked().closeInventory();
				event.getInventory().setItem(47, is);
				return true;
			} else if (tags.getString("EXTRA").equalsIgnoreCase("CLOSE")) {
				//event.getWhoClicked().openInventory(getInventory());
				event.getWhoClicked().closeInventory();
				event.getInventory().setItem(47, new ItemStack(Material.BARRIER));
				return true;
			} else if (tags.getString("EXTRA").equalsIgnoreCase("RECIPERESULTITEM")) {
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
