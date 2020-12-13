package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.inventory.Inventory;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.core.custom.interfaces.IRedstoneable;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockRemoteInteractorBlock extends CustomEnergyBlock implements IRedstoneable {

	public final int locationSaverSlot = 12;
    public final int itemSlot = 14;
    public final int energySlot = 13;

	@Override
	public Material getMaterial() {
		return Material.OBSIDIAN;
	}

	@Override
	public String getDefaultDisplayName() {
		return "§cRemote Interactor Block";
	}

	@Override
	public String getUUID() {
		return "Pixlies:Remote_Interactor_Block";
	}

	@Override
	public Inventory getInventory() {
		Inventory inventory = Bukkit.createInventory(null, 3*9, getInventoryTitle());
		for (int i = 0; i < 27; i++) {
			if (i==locationSaverSlot || i==itemSlot) {
				
			} else {
				inventory.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
			}
		}
		return inventory;
	}
	
	@Override
	public boolean InventoryClickEvent(org.bukkit.event.inventory.InventoryClickEvent event) {
		String id = CustomItemUtil.getUUIDFromItemStack(event.getCurrentItem());
		if (id==null) {
			return true;
		} else {
			String id2 = CustomItemUtil.getUUIDFromItemStack(event.getInventory().getItem(12));
			if (id2==null || id2.equalsIgnoreCase(MinecraftMaterial.AIR.getUUID())) return false;
			if (!id2.equalsIgnoreCase("Pixlies:ICBM_Location_Holder")) {
				event.getWhoClicked().getInventory().addItem(event.getInventory().getItem(12));
				event.getInventory().clear(12);
				return true;
			}
		}
		return event.isCancelled();
	}
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		inventory.setItem(energySlot, buildInfoItem(location));
	}

	@Override
	public void onRecievedRedstoneSignal(Location location, int strength, BlockRedstoneEvent event) {
		if (strength>5) {
			
		}
	}

	@Override
	public double getCapacity() {
		return 100000D;
	}

}
