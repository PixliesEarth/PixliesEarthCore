package eu.pixliesearth.core.custom;

import eu.pixliesearth.core.custom.interfaces.Constants;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.Timer;
import eu.pixliesearth.utils.*;
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
	
	// ?
	
	/**
	 * Initialises the class
	 */
	public CustomCrafterMachine() {
		
	}
	
	// Global variables
	
	/**
	 * The slots where the crafting ingredients are stored
	 */
	public static final List<Integer> craftSlots = Arrays.asList(0, 1, 2, 3, 9, 18, 27, 10, 11, 12, 19, 20, 21, 28, 29, 30);
	/**
	 * The slots where the results are stored
	 */
	public static final List<Integer> resultSlots = Arrays.asList(5, 6, 7, 8, 17, 26, 35, 14, 15, 16, 23, 24, 25, 32, 33, 34);
	/**
	 * The slots where the crafting indicators are shown
	 * 
	 * @deprecated No longer used as other information is shown there
	 */
	@Deprecated
	public static final List<Integer> progressSlots = Arrays.asList(36, 37, 38, 39, 40, 41, 42, 43, 44);
	
	// ?
	
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
		} else {
			ItemStack is = i.getItem(Constants.getGUIDataSlot);
			if (is==null || is.getType().equals(Material.AIR)) {
				player.openInventory(i);
			} else {
				if (is.getType().equals(Material.BARRIER)) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getHandler().getInstance(), new Runnable() {

						@Override
						public void run() {
							for (int slot : craftSlots) {
								ItemStack is = i.getItem(slot);
								if (is!=null && !is.getType().equals(Material.AIR)) 
									player.getLocation().getWorld().dropItemNaturally(player.getLocation(), is);
								i.clear(slot);
							}
						}
						
					}, 1l);
					Inventory inv = getInventory();
					player.openInventory(inv);
					CustomFeatureLoader.getLoader().getHandler().setInventoryFromLocation(location, inv);
				} else if(Constants.getExtraData(is).equalsIgnoreCase("RECIPE")) {
					String id = CustomItemUtil.getUUIDFromItemStack(is);
					CustomRecipe r = getRecipesOfUUIDInOrderedList(id).get(0);
					Inventory inv = getInventory2(r, 0);
					player.openInventory(inv);
					CustomFeatureLoader.getLoader().getHandler().setInventoryFromLocation(location, inv);
				} else {
					player.openInventory(i);
				}
			}
		}
	}
	
	@Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		if (inv==null || loc==null) return;
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (inv.getItem(0)==null || !isUnclickable(inv.getItem(0))) {
			try {
				CustomRecipe r = getRecipesOfUUIDInOrderedList(CustomItemUtil.getUUIDFromItemStack(inv.getItem(53))).get(Integer.parseInt(NBTUtil.getTagsFromItem(inv.getItem(53)).getString("RECIPE")));
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
			} catch (Exception ignore) { return; }
		}
	}
	/**
	 * Called to check if an item can be crafted and if so, it is crafted
	 * 
	 * TODO: add param notes
	 * 
	 * @return If the item was crafted
	 */
	public boolean craft(Location loc, Inventory inv, CustomRecipe r) {
		if (!hasCost(loc, r)) return false;
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
		addToResult(loc, inv, CustomItemUtil.getItemStackFromUUID(r.getResultUUID())); // Give result
		takeCost(loc, r); // Take cost
		return true;
	}
	/**
	 * Creates an {@link Inventory} and returns it
	 * 
	 * @return The {@link CustomCrafterMachine}'s {@link Inventory}
	 */
	@Override
	public Inventory getInventory() { 
		Inventory inv = Bukkit.createInventory(null, 6*9, getInventoryTitle());
		for (int i = 0; i < 6*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		set(inv);
		inv.setItem(49, Constants.closeItem.clone());
		inv.setItem(48, Constants.backItem.clone());
		inv.setItem(50, Constants.nextItem.clone());
		return inv;
	}
	
	public void set(Inventory inv) { // Next page
		
		final List<Integer> ints = Arrays.asList(10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43);
		
		List<List<CustomRecipe>> rll = getRecipes();
		
		if (rll.isEmpty()) return;
		
		if (inv.getItem(ints.get(0))==null || isUnclickable(inv.getItem(ints.get(0)))) {
			int i = 0;
			for (int i2 : ints) {
				try {
					inv.setItem(i2, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(rll.get(i).get(0).getResultUUID())).addNBTTag("EXTRA", "RECIPE", NBTTagType.STRING).addNBTTag("RECIPE", Integer.toString(i), NBTTagType.STRING).build());
				} catch (Exception ignore) {
					inv.setItem(i2, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
				}
				i++;
			}
		} else {
			ItemStack is = inv.getItem(43);
			if (is==null || is.getType().equals(Material.AIR)) return;
			if (Constants.getExtraData(is).equalsIgnoreCase("RECIPE")) {
				int i = Integer.parseInt(NBTUtil.getTagsFromItem(is).getString("RECIPE"))+1;
				for (int i2 : ints) {
					try {
						inv.setItem(i2, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(rll.get(i).get(0).getResultUUID())).addNBTTag("EXTRA", "RECIPE", NBTTagType.STRING).addNBTTag("RECIPE", Integer.toString(i), NBTTagType.STRING).build());
					} catch (Exception ignore) {
						inv.setItem(i2, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
					}
					i++;
				}
			}
		}
	}
	
	public void set2(Inventory inv) { // Last Page
		
		final List<Integer> ints = Arrays.asList(10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43);
		
		List<List<CustomRecipe>> rll = getRecipes();
		
		if (rll.isEmpty()) return;
		
		if (inv.getItem(ints.get(0))==null || isUnclickable(inv.getItem(ints.get(0)))) {
			int i = 0;
			for (int i2 : ints) {
				try {
					inv.setItem(i2, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(rll.get(i).get(0).getResultUUID())).addNBTTag("EXTRA", "RECIPE", NBTTagType.STRING).addNBTTag("RECIPE", Integer.toString(i), NBTTagType.STRING).build());
				} catch (Exception ignore) {
					inv.setItem(i2, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
				}
				i++;
			}
		} else {
			ItemStack is = inv.getItem(10);
			if (is==null || is.getType().equals(Material.AIR)) return;
			if (Constants.getExtraData(is).equalsIgnoreCase("RECIPE")) {
				int i = Integer.parseInt(NBTUtil.getTagsFromItem(is).getString("RECIPE"))-28;
				for (int i2 : ints) {
					try {
						inv.setItem(i2, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(rll.get(i).get(0).getResultUUID())).addNBTTag("EXTRA", "RECIPE", NBTTagType.STRING).addNBTTag("RECIPE", Integer.toString(i), NBTTagType.STRING).build());
					} catch (Exception ignore) {
						inv.setItem(i2, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
					}
					i++;
				}
			}
		}
	}
	
	public void set3(Inventory inv) { // Next page
		
		List<List<CustomRecipe>> rll = getRecipes();
		
		if (rll.isEmpty()) return;
		
		ItemStack is = inv.getItem(53);
		
		if (is==null || is.getType().equals(Material.AIR)) return;
		
		String id = CustomItemUtil.getUUIDFromItemStack(is);
		
		for (List<CustomRecipe> rl : rll) {
			if (rl.get(0).getResultUUID().equalsIgnoreCase(id)) {
				String s = NBTUtil.getTagsFromItem(is).getString("RECIPE");
				if (s==null || s.equalsIgnoreCase("")) {
					continue;
				} else {
					Integer i = Integer.parseInt(s)+1;
					if (i>=rl.size()) return; // Out of bounds
					inv.setItem(53, new ItemBuilder(id).addNBTTag("RECIPE", Integer.toString(i), NBTTagType.STRING).addNBTTag("EXTRA", "MCRAFTING", NBTTagType.STRING).build());
					/*List<ItemStack> list = new ArrayList<>();
					List<ItemStack> list2 = new ArrayList<>();
					for (int i2 : craftSlots) 
						list.add(inv.getItem(i2));
					for (int i2 : resultSlots) 
						list2.add(inv.getItem(i2));
					inv.setContents(getInventory2(rl.get(i), i).getContents());
					int i3 = 0;
					for (int i2 : craftSlots) 
						inv.setItem(i2, list.get(i3));
					i3 = 0;
					for (int i2 : resultSlots) 
						inv.setItem(i2, list2.get(i3));*/
				}
				break;
			}
		}
	}
	
	public void set4(Inventory inv) { // Next page
		
		List<List<CustomRecipe>> rll = getRecipes();

		if (rll.isEmpty()) return;
		
		ItemStack is = inv.getItem(53);
		
		if (is==null || is.getType().equals(Material.AIR)) return;
		
		String id = CustomItemUtil.getUUIDFromItemStack(is);
		
		for (List<CustomRecipe> rl : rll) {
			if (rl.get(0).getResultUUID().equalsIgnoreCase(id)) {
				String s = NBTUtil.getTagsFromItem(is).getString("RECIPE");
				if (s==null || s.equalsIgnoreCase("")) {
					continue;
				} else {
					Integer i = Integer.parseInt(s)-1;
					if (i<0) return; // Out of bounds
					inv.setItem(53, new ItemBuilder(id).addNBTTag("RECIPE", Integer.toString(i), NBTTagType.STRING).addNBTTag("EXTRA", "MCRAFTING", NBTTagType.STRING).build());
					/*List<ItemStack> list = new ArrayList<>();
					List<ItemStack> list2 = new ArrayList<>();
					for (int i2 : craftSlots) 
						list.add(inv.getItem(i2));
					for (int i2 : resultSlots) 
						list2.add(inv.getItem(i2));
					inv.setContents(getInventory2(rl.get(i), i).getContents());
					int i3 = 0;
					for (int i2 : craftSlots) 
						inv.setItem(i2, list.get(i3));
					i3 = 0;
					for (int i2 : resultSlots) 
						inv.setItem(i2, list2.get(i3));*/
				}
				break;
			}
		}
	}
	
	// TODO: notes
	public Inventory getInventory2(CustomRecipe r, int listValue) {
		Inventory inv = Bukkit.createInventory(null, 6*9, getInventoryTitle());
		for (int i = 0; i < 6*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		for (int i : craftSlots)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(MinecraftMaterial.AIR.getUUID()));
		for (int i : resultSlots)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(MinecraftMaterial.AIR.getUUID()));
		inv.setItem(53, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(r.getResultUUID()))
				.setAmount(1)
				.addNBTTag("EXTRA", "MCRAFTING", NBTTagType.STRING)
				.addNBTTag("RECIPE", Integer.toString(listValue), NBTTagType.STRING)
				.build());
		inv.setItem(49, Constants.closeItem.clone());
		inv.setItem(48, Constants.backItem.clone());
		inv.setItem(50, Constants.nextItem.clone());
		inv.setItem(45, r.getRecipeBook());
		return inv;
	}
	/**
	 * Called when a player clicks on the {@link Inventory}
	 * 
	 * @param event The {@link InventoryClickEvent} that occurred
	 * @return If the event should be cancelled
	 */
	@Override
	public boolean InventoryClickEvent(InventoryClickEvent event) {
		try { // Cool code to stop invalid inventory sizes sending their inventory click event to this gui
			event.getInventory().getItem(53);
		} catch (Exception e) {
			return true;
		}
		ItemStack is = event.getCurrentItem();
		if (is==null || is.getType().equals(MinecraftMaterial.AIR.getMaterial())) return false;
		if (Constants.isCloseItem(is)) {
			closeForAll(event.getInventory());
			event.getInventory().setItem(Constants.getGUIDataSlot, new ItemBuilder(Material.BARRIER).addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build());
			event.setCancelled(true);
			return true;
		} else if (Constants.getExtraData(is).equalsIgnoreCase("MRECIPE")) {
			closeForAll(event.getInventory());
			event.getInventory().setItem(Constants.getGUIDataSlot, is);
			event.setCancelled(true);
			return true;
		}
		ItemStack is2 = event.getInventory().getItem(53);
		if (Constants.getExtraData(is2).equalsIgnoreCase("MCRAFTING")) {
			// Crafting Recipe gui
			if (Constants.isNextItem(is)) {
				set3(event.getInventory());
				event.setCancelled(true);
			} else if (Constants.isBackItem(is)) {
				set4(event.getInventory());
				event.setCancelled(true);
			}
		} else {
			// Recipe selector gui
			if (Constants.isNextItem(is)) {
				set(event.getInventory());
			} else if (Constants.isBackItem(is)) {
				set2(event.getInventory());
			} else if (Constants.getExtraData(is).equalsIgnoreCase("RECIPE")) {
				closeForAll(event.getInventory());
				event.getInventory().setItem(Constants.getGUIDataSlot, is);
			}
			event.setCancelled(true);
		}
		if (isUnclickable(is)) {
			event.setCancelled(true);
			return true;
		}
		if (!Constants.hasExtraData(is)) return false;
		return true;
	}
	
	// SAVING AND LOADING
	
	/**
	 * Uses the provided {@link Inventory}, {@link Location} and {@link Map} to load the {@link CustomCrafterMachine}
	 */
	@Override
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
		if (map.get("TIMEREX")!=null && map.get("TIMEREN")!=null)
			CustomFeatureLoader.getLoader().getHandler().registerTimer(location, new Timer(Long.parseLong(map.get("TIMEREX")), Boolean.getBoolean(map.get("TIMEREN"))));
	}
	/**
	 * Uses the provided {@link Inventory}, {@link Location} and {@link Timer} to save the {@link CustomCrafterMachine}
	 */
	@Override
	public HashMap<String, String> getSaveData(Location location, Inventory inventory, Timer timer) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (timer!=null) {
			map.put("TIMEREX", Long.toString(timer.getExpiry()));
			map.put("TIMEREN", Boolean.toString(timer.isEnded()));
		}
		return map;
	}
	
	// GENERAL UTILITIES
	
	/**
	 * Gets and returns all recipes of an item based on its uuid
	 * 
	 * @param id The item id
	 * @return All recipes of an item based on its uuid
	 */
	public Set<CustomRecipe> getRecipesOfUUID(String id) {
		Set<CustomRecipe> set = new HashSet<CustomRecipe>();
		for (CustomRecipe cr : CustomFeatureLoader.getLoader().getHandler().getCustomRecipes()) {
			if (cr.getResultUUID().equalsIgnoreCase(id)) {
				set.add(cr);
			}
		}
		return set;
	}
	/**
	 * Gets and returns all recipes of an item based on its uuid
	 * 
	 * @param id The item id
	 * @return All recipes of an item based on its uuid in an ordered list
	 */
	public static List<CustomRecipe> getRecipesOfUUIDInOrderedList(String id) {
		List<String> list = new ArrayList<>();
		List<CustomRecipe> list2 = new ArrayList<>();
		for (CustomRecipe cr : CustomFeatureLoader.getLoader().getHandler().getCustomRecipes()) {
			if (cr.getResultUUID().equalsIgnoreCase(id)) {
				list.add(cr.getClass().getName());
			}
		}
		Collections.sort(list);
		for (String s : list) {
			for (CustomRecipe cr : CustomFeatureLoader.getLoader().getHandler().getCustomRecipes()) {
				if (cr.getClass().getName().equals(s)) {
					list2.add(cr);
				}
			}
		}
		return list2;
	}
	/**
	 * Returns if the inputed item has the unclickable item uuid
	 */
	public boolean isUnclickable(ItemStack is) {
		return CustomItemUtil.getUUIDFromItemStack(is).equalsIgnoreCase(CustomInventoryListener.getUnclickableItemUUID());
	}
	/**
	 * Returns all items in the craft slots in the {@link Inventory} provided
	 * 
	 * @param inv The {@link Inventory} to check/look threw
	 * @return All items in the crafting section in a {@link Set}
	 */
	public static Set<ItemStack> getItemsInCraftingSection(Inventory inv) {
		Set<ItemStack> list = new HashSet<ItemStack>();
		for (int i : craftSlots) 
			if (inv.getItem(i)==null || inv.getItem(i).getType().equals(MinecraftMaterial.AIR.getMaterial())) 
				continue;
			else 
				list.add(inv.getItem(i));
		return list;
	}
	/**
	 * Add the given item to an available result slot or drops it at the location provided
	 * 
	 * @apiNote If location is null it will instead do nothing
	 * 
	 * @param location The {@link Location} the {@link ItemStack} will be dropped at if the {@link Inventory} is full
	 * @param inventory The {@link Inventory} that the {@link ItemStack} will be added to
	 * @param itemStack The {@link ItemStack} that will be added to the results slot
	 */
	public void addToResult(Location location, Inventory inventory, ItemStack itemStack) {
		for (int i : resultSlots) {
			ItemStack is2 = inventory.getItem(i);
			if (is2==null || is2.getType().equals(Material.AIR)) {
				inventory.setItem(i, itemStack);
				return;
			} else {
				ItemStack is3 = is2.asOne();
				if (is3.equals(itemStack)) {
					if (is2.getAmount()<64) {
						is2.setAmount(is2.getAmount()+1);
						return;
					} else {
						continue;
					}
				} else {
					continue;
				}
			}
		}
		if (location==null) return; // Stop item from dropping
		Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
			@Override
			public void run() {
				location.getWorld().dropItemNaturally(location, itemStack);
			}
		}, 0L);
	}
	/**
	 * Adds the provided {@link Map} to the {@link CustomCrafterMachine}'s {@link Inventory}'s craft slots
	 * 
	 * @param loc The {@link CustomCrafterMachine}'s {@link Location}
	 * @param inv The {@link CustomCrafterMachine}'s {@link Inventory}
	 * @param map The {@link Map} to set to the {@link Inventory}'s crafting slots
	 * 
	 * TODO: optimise like {@link #addToResult(Location, Inventory, ItemStack)}
	 * 
	 */
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
	/**
	 * Gets the next available slot in the {@link Inventory}'s crafting slots
	 * 
	 * @param inventory The {@link Inventory} to search threw
	 * @return The next slot that does not contain any item
	 * 
	 *  TODO: remove
	 *  
	 */
	public int getNextFreeSlotInCrafting(Inventory inventory) {
		for (int i : craftSlots) 
			if (inventory.getItem(i)==null || inventory.getItem(i).getType().equals(Material.AIR)) 
				return i;
		return -1;
	}
	/**
	 * Gets the next free slot and sets the item to it
	 */
	public boolean addItemToNextAvailableSlot(Inventory inv, ItemStack is) {
		int i = inv.firstEmpty();
		
		if (i==-1) {
			for (ItemStack is2 : inv.getContents()) {
				if (CustomItemUtil.getUUIDFromItemStack(is2).equalsIgnoreCase(CustomInventoryListener.getUnclickableItemUUID())) {
					inv.setItem(inv.first(is2), is);
					return true;
				}
			}
			return false;
		} else {
			inv.setItem(i, is);
			return true;
		}
	}
	/**
	 * Gets the next free slot based on the provided list and sets the item to it
	 * 
	 * @apiNote <b>WARINING</b> Will override any item with the unclickable uuid!
	 */
	public boolean addItemToNextAvailableSlotBasedOnList(Inventory inv, ItemStack is, List<Integer> ints) {
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
	 * Gets the next free slot based on the provided list and sets the item to it
	 * 
	 * @apiNote <b>WARINING</b> Will override any item with the unclickable uuid!
	 */
	public boolean addItemToNextAvailableSlotBasedOnList(Inventory inv, ItemStack is, int[] ints) {
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
	 * Gets the next free slot based on the provided list and sets the item to it
	 * 
	 * @apiNote <b>WARINING</b> Will override any item with the unclickable uuid!
	 */
	public boolean addItemToNextAvailableSlotBasedOnList(Inventory inv, ItemStack is, Set<Integer> ints) {
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
	
	public List<List<CustomRecipe>> getRecipes() {
		List<String> list = new ArrayList<>();
		List<List<CustomRecipe>> list2 = new ArrayList<>();
		for (CustomRecipe cr : CustomFeatureLoader.getLoader().getHandler().getRecipesFromUUID(getUUID())) {
			list.add(cr.getResultUUID());
		}
		Collections.sort(list);
		for (String s : list) {
			if (!getRecipesOfUUIDInOrderedList(s).isEmpty()) list2.add(getRecipesOfUUIDInOrderedList(s));
		}
		return list2;
	}
	
	// FOR EXTENDED CUSTOM CLASSES
	
	/**
	 * Called to check if the item can be crafted
	 * 
	 * @param location The {@link CustomCrafterMachine} {@link Location}
	 * @param customRecipe The current {@link CustomRecipe}
	 * @return If the crafting can go ahead
	 */
	public boolean hasCost(Location location, CustomRecipe customRecipe) { return true; }
	/**
	 * Called to take the cost of the crafting
	 * 
	 * @param location The {@link CustomCrafterMachine} {@link Location}
	 * @param customRecipe The current {@link CustomRecipe}
	 */
	public void takeCost(Location location, CustomRecipe customRecipe) {}
}
