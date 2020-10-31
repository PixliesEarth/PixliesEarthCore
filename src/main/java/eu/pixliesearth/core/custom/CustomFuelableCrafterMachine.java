package eu.pixliesearth.core.custom;

import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to create a machine</h3>
 *
 */
public abstract class CustomFuelableCrafterMachine extends CustomCrafterMachine {
	/**
	 * Initialises the class
	 */
	public CustomFuelableCrafterMachine() {
		
	}
	/**
	 * The fuel to use
	 */
	public abstract String getFuelUUID();
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
		ItemStack fuel = i.getItem(52);
		if (is==null) {
			player.openInventory(i);
		} else if (!isUnclickable(is)) {
			if (is.getType().equals(Material.BARRIER)) {
				Inventory inv = getInventory();
				inv.setItem(52, fuel);
				player.openInventory(inv);
				CustomFeatureLoader.getLoader().getHandler().setInventoryFromLocation(location, inv);
			} else {
				String id = CustomItemUtil.getUUIDFromItemStack(is);
				CustomRecipe r = getRecipeFromUUID(id);
				Inventory inv = getInventory2(r);
				inv.setItem(52, fuel);
				player.openInventory(inv);
				CustomFeatureLoader.getLoader().getHandler().setInventoryFromLocation(location, inv);
			}
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
		inv.clear(52);
		return inv;
	}
	// TODO: notes
	@Override
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
		inv.setItem(49, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(r.getResultUUID())).setAmount(1).addNBTTag("EXTRA", "RECIPERESULTITEM", NBTTagType.STRING).build());
		inv.clear(52);
		return inv;
	}
	
	@Override
	public boolean craft(Location loc, Inventory inv, CustomRecipe r) {
		if (inv.getItem(52)==null || !hadFuel(inv)) return false;
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
		inv.getItem(52).setAmount(inv.getItem(52).getAmount()-1); // Remove one fuel
		return true;
	}
	// TODO: notes
	public boolean hadFuel(Inventory inv) {
		return CustomItemUtil.getUUIDFromItemStack(inv.getItem(52)).equalsIgnoreCase(getFuelUUID());
	}
}
