package eu.pixliesearth.core.custom;

import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
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
public abstract class CustomEnergyCrafterMachine extends CustomCrafterMachine implements Energyable {
	/**
	 * Initialises the class
	 */
	public CustomEnergyCrafterMachine() {
		
	}
	/**
	 * The amount of energy the machine can hold
	 */
	public abstract double getCapacity();
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
	
	@Override
	public ItemStack buildItem() {
		return new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/"+getPlayerHeadUUID())) {{
			setGlow(isGlowing());
			setUnbreakable(isUnbreakable());
			if (getDefaultDisplayName()==null) 
					setNoName();
			else 
				setDisplayName(getDefaultDisplayName());
			if (getCustomModelData()!=null) 
				setCustomModelData(getCustomModelData());
			for (Entry<Enchantment, Integer> entry : getDefaultEnchants().entrySet()) 
				addEnchantment(entry.getKey(), entry.getValue());
			if (getDefaultLore()!=null)
				addLoreAll(getDefaultLore());
			for (ItemFlag flag : getItemFlags()) 
				addItemFlag(flag);
			addLoreLine("§fRarity: "+getRarity().getName());
			for (Entry<String, Object> entry : getDefaultNBT().entrySet()) 
				addNBTTag(entry.getKey(), entry.getValue().toString(), NBTTagType.STRING);
			addNBTTag("UUID", getUUID(), NBTTagType.STRING);
			addNBTTag("RARITY", getRarity().getUUID(), NBTTagType.STRING);
			addNBTTag("ENERGY", Double.toString(0), NBTTagType.STRING);
		}}.build();
	}
	
	@Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		if (loc==null) return;
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		getEnergyFromBlockAbove(loc);
		if (inv==null) return;
		inv.setItem(52, buildInfoItem(loc));
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
	
	public void getEnergyFromBlockAbove(Location location) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Location location2 = new Location(location.getWorld(), location.getBlockX(), location.getBlockY()+1, location.getBlockZ());
		Double d = h.getPowerAtLocation(location2);
		if (d==null || d<=0 || isFull(location)) return;
		double amountToRemove = 1;
		h.removePowerFromLocation(location2, amountToRemove);
		h.addPowerToLocation(location, amountToRemove);
	}
	
	public boolean isFull(Location loc) {
		return getContainedPower(loc)>=getCapacity();
	}
	
	@Override
	public boolean craft(Location loc, Inventory inv, CustomRecipe r) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (r.getEnergyCost()==null) {
			// Do nothing as it does not cost power to craft
		} else if (!(getContainedPower(loc)>=r.getEnergyCost())) {
			return false;
		}
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
		h.removePowerFromLocation(loc, r.getEnergyCost()); // Take energy
		return true;
	}
	
	@Override
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
		if (map.get("TIMEREX")!=null && map.get("TIMEREN")!=null)
			CustomFeatureLoader.getLoader().getHandler().registerTimer(location, new Timer(Long.parseLong(map.get("TIMEREX")), Boolean.getBoolean(map.get("TIMEREN"))));
		CustomFeatureLoader.getLoader().getHandler().addPowerToLocation(location, Double.parseDouble(map.get("ENERGY")));
	}
	
	@Override
	public HashMap<String, String> getSaveData(Location location, Inventory inventory, Timer timer) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (timer!=null) {
			map.put("TIMEREX", Long.toString(timer.getExpiry()));
			map.put("TIMEREN", Boolean.toString(timer.isEnded()));
		}
		map.put("ENERGY", Double.toString(CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location)));
		return map;
	}
	
	public ItemStack buildInfoItem(Location location) {
		return new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName("§6Energy").addLoreLine("§eContained: "+Methods.convertEnergyDouble(Methods.round(getContainedPower(location), 3))).addLoreLine("§eCapacity: "+Methods.convertEnergyDouble(getCapacity())).addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build();
	}
	
	public double getContainedPower(Location location) {
		return CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location);
	}
}
