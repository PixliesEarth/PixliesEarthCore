package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.interfaces.Energyable;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnergyBlockBatBox2 extends CustomEnergyBlock {
	
	public EnergyBlockBatBox2() {
		
	}
	
	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(null, 4*9, getInventoryTitle());
		for (int i = 0; i < 4*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		inv.clear(34);
		inv.clear(13);
		inv.clear(22);
		return inv;
	}
	
	@Override
    public Material getMaterial() {
        return Material.BROWN_CONCRETE;
    }
	
	public double getCapacity() {
		return 150000D;
	}
	
	@Override
    public boolean InventoryClickEvent(InventoryClickEvent event) {
    	ItemStack is = event.getCurrentItem();
    	if (is==null) return false;
    	String s = CustomItemUtil.getUUIDFromItemStack(is);
    	if (s==null) return false;
    	if (s.equalsIgnoreCase(CustomInventoryListener.getUnclickableItemUUID())) return true;
    	return false;
    }
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		if (location==null) return;
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (inventory==null) return;
		inventory.setItem(34, buildInfoItem(location));
		if (timer==null) {
			h.registerTimer(location, new Timer(100L)); // One tenth of a second
			ItemStack is = inventory.getItem(13);
			if (is==null) {
				inventory.setItem(22, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§8No item inputted").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build());
				return;
			}
			if (CustomItemUtil.getCustomItemFromUUID(CustomItemUtil.getUUIDFromItemStack(is))==null || !(CustomItemUtil.getCustomItemFromUUID(CustomItemUtil.getUUIDFromItemStack(is)) instanceof Energyable)) {
				inventory.setItem(22, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§8This item cannot contain energy!").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build());
				return;
			}
			if (isFull(is)) {
				inventory.setItem(22, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§aFully charged!").addLoreLine("§e"+Methods.convertEnergyDouble(Methods.round(getContainedPower(is), 3))+"/"+Methods.convertEnergyDouble(getCapacity(is))).addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build());
				return;
			} else {
				inventory.setItem(22, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName("§6Charging!").addLoreLine("§e"+Methods.convertEnergyDouble(Methods.round(getContainedPower(is), 3))+"/"+Methods.convertEnergyDouble(getCapacity(is))).addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build());
			}
			Double d = getContainedPower(is);
			if (d==null) return;
			inventory.setItem(13, giveEnergy(location, is, 1D));
		} else {
			if (timer.hasExpired()) {
				h.unregisterTimer(location);
			} else {
				// Do nothing
			}
		}
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Intermediate Charger";
    }

    @Override
    public String getUUID() {
        return "Machine:Charger_Intermediate"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
}
