package eu.pixliesearth.core.custom.blocks;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.core.custom.interfaces.IHopperable;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockEnchantmentRelocator extends CustomEnergyBlock implements IHopperable {
	
	public EnergyBlockEnchantmentRelocator() {
		
	}
	
	public double getCapacity() {
		return 1000000;
	}
	
	private final double energyPerAction = 1000D;
	private final long timePerAction = 5000l;
	
	@Override
	public Material getMaterial() {
		return Material.WARPED_HYPHAE;
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Enchantment Relocator";
	}
    
    @Override
    public String getUUID() {
        return "Machine:Enchantment_Relocator"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		inv.setItem(25, buildInfoItem(loc));
		if (getCapacity(loc)<energyPerAction) return;
		if (timer==null) {
			ItemStack books = inv.getItem(13);
			if (books==null || books.getType().equals(Material.AIR)) return;
			ItemStack remove = inv.getItem(11);
			if (remove==null || remove.getType().equals(Material.AIR) || !remove.hasEnchants() || remove.getType().equals(Material.ENCHANTED_BOOK)) return;
			// Make sure slot 15 & 16 are empty
			ItemStack addTo = inv.getItem(15);
			if (addTo!=null && !addTo.getType().equals(Material.AIR)) return;
			ItemStack addTo2 = inv.getItem(16);
			if (addTo2!=null && !addTo2.getType().equals(Material.AIR)) return;
			h.registerTimer(loc, new Timer(timePerAction));
		} else {
			if (timer.hasExpired()) {
				h.unregisterTimer(loc);
				ItemStack books = inv.getItem(13);
				if (books==null || books.getType().equals(Material.AIR)) return;
				ItemStack remove = inv.getItem(11);
				if (remove==null || remove.getType().equals(Material.AIR) || !remove.hasEnchants() || remove.getType().equals(Material.ENCHANTED_BOOK)) return;
				ItemStack addTo = inv.getItem(15);
				if (addTo!=null && !addTo.getType().equals(Material.AIR)) return;
				ItemStack addTo2 = inv.getItem(16);
				if (addTo2!=null && !addTo2.getType().equals(Material.AIR)) return;
				books.setAmount(books.getAmount()-1);
				ItemBuilder give = new ItemBuilder(Material.ENCHANTED_BOOK);
				ItemStack give2 = remove.clone().asOne();
				for (Entry<Enchantment, Integer> entry : remove.getEnchantments().entrySet()) {
					give.addEnchantment(entry.getKey(), entry.getValue());
					give2.removeEnchantment(entry.getKey());
				}
				remove.setAmount(remove.getAmount()-1);
				inv.setItem(15, give2);
				inv.setItem(16, give.build());
				h.removePowerFromLocation(loc, energyPerAction);
			} else {
				// Do nothing
			}
		}
    }
    
    @Override
    public boolean InventoryClickEvent(InventoryClickEvent event) {
    	if (event.getCurrentItem()==null || event.getCurrentItem().getType().equals(Material.AIR)) return false;
		if (isUnclickable(event.getCurrentItem())) return true;
    	return false;
    }
    
    @Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(null, 3*9, getInventoryTitle());
		for (int i = 0; i < 3*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		inv.clear(11); // Item Input
		inv.clear(13); // Book Input
		inv.clear(15); // Item Output
		inv.clear(16); // Book output
		return inv;
	}

	@Override
	public ItemStack takeFirstTakeableItemFromIHopperableInventory(Location location) {
		Inventory inv = CustomFeatureLoader.getLoader().getHandler().getInventoryFromLocation(location);
		ItemStack itemStack = inv.getItem(15);
		if (itemStack!=null && !itemStack.getType().equals(Material.AIR)) {
			ItemStack itemStack2 = itemStack.clone().asOne();
			itemStack.setAmount(itemStack.getAmount()-1);
			return itemStack2;
		}
		ItemStack itemStack3 = inv.getItem(16);
		if (itemStack3!=null && !itemStack3.getType().equals(Material.AIR)) {
			ItemStack itemStack4 = itemStack3.clone().asOne();
			itemStack3.setAmount(itemStack3.getAmount()-1);
			return itemStack4;
		}
		return null;
	}

	@Override
	public boolean addItemToIHopperableInventory(Location location, ItemStack itemStack) {
		Inventory inv = CustomFeatureLoader.getLoader().getHandler().getInventoryFromLocation(location);
		if (CustomItemUtil.getUUIDFromItemStack(itemStack).equals(MinecraftMaterial.BOOK.getUUID())) {
			ItemStack itemStack2 = inv.getItem(13);
			if (itemStack2!=null && !itemStack2.getType().equals(Material.AIR)) {
				if (itemStack.equals(itemStack2)) {
					itemStack2.setAmount(itemStack2.getAmount()+itemStack.getAmount());
					return true;
				} else {
					return false;
				}
			} else {
				inv.setItem(13, itemStack);
				return true;
			}
		} else {
			ItemStack itemStack3 = inv.getItem(11);
			if (itemStack3!=null && !itemStack3.getType().equals(Material.AIR)) {
				inv.setItem(11, itemStack);
				return true;
			} else {
				return false;
			}
		}
	}
    
}
