package eu.pixliesearth.core.custom.machines;

import eu.pixliesearth.core.custom.CustomEnergyCrafterMachine;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MachineElectricForge extends CustomEnergyCrafterMachine { //TODO: make use fuel
	
	public MachineElectricForge() {
		
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "368ab8cf594da55f90f593bdc0b41e05925d75daa3ee7f1b49c2421d2bdead0";
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Electric Forge";
    }

    @Override
    public String getUUID() {
        return "Machine:Forge_Electric"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

	@Override
	public double getCapacity() {
		return 150000D;
	}
	
	@Override
	public Inventory getInventory() { 
		Inventory inv = Bukkit.createInventory(null, 6*9, getInventoryTitle());
		for (int i = 0; i < 6*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		Set<CustomRecipe> rs = CustomFeatureLoader.getLoader().getHandler().getRecipesFromUUID("Machine:Forge");
		for (CustomRecipe r : rs)
			a(inv, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(r.getResultUUID())).addNBTTag("EXTRA", "RECIPE", NBTTagType.STRING).build());
		inv.clear(52);
		return inv;
	}
	
	@Override
	public boolean craft(Location loc, Inventory inv, CustomRecipe r) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (!(getContainedPower(loc)>=150D)) {
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
		h.removePowerFromLocation(loc, 150D); // Take energy
		return true;
	}
}
