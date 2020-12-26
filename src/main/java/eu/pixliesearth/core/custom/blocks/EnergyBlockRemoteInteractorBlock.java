package eu.pixliesearth.core.custom.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.core.custom.interfaces.IRedstoneable;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.NBTUtil;
import eu.pixliesearth.utils.NBTUtil.NBTTags;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockRemoteInteractorBlock extends CustomEnergyBlock implements IRedstoneable {

	public final int locationSaverSlot = 12;
    public final int itemSlot = 14;
    public final int energySlot = 13;
    public final double energyPerAction = 1000D;

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
		if (id==null || id.equals("") || id.equals(CustomInventoryListener.getUnclickableItemUUID())) {
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
	public void onRecievedRedstoneSignal(Location location) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (getContainedPower(location)>=energyPerAction) {
			NBTTags tags = NBTUtil.getTagsFromItem(h.getInventoryFromLocation(location).getItem(locationSaverSlot));
    		Location l = new Location(Bukkit.getWorld(UUID.fromString(tags.getString("w"))), Integer.parseInt(tags.getString("x")), Integer.parseInt(tags.getString("y")), Integer.parseInt(tags.getString("z")));
			Event event2 = new PlayerInteractEvent(Bukkit.getOfflinePlayer(h.getPrivateLocation(location)).getPlayer(), Action.RIGHT_CLICK_BLOCK, h.getInventoryFromLocation(location).getItem(itemSlot), l.getBlock(), BlockFace.UP);
	   		event2.callEvent();
			h.removePowerFromLocation(location, energyPerAction);
		}
	}
	
	@Override
	public HashMap<String, String> getSaveData(Location location, Inventory inventory, Timer timer) {
		HashMap<String, String> map = super.getSaveData(location, inventory, timer);
		map.put("locked", CustomFeatureLoader.getLoader().getHandler().getPrivateLocation(location).toString());
		return map;
	}
	
	@Override
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
		super.loadFromSaveData(inventory, location, map);
		CustomFeatureLoader.getLoader().getHandler().registerPrivateLocation(location, UUID.fromString(map.get("locked")));
	}
	
	@Override
	public boolean BlockPlaceEvent(org.bukkit.event.block.BlockPlaceEvent event) {
		CustomFeatureLoader.getLoader().getHandler().registerPrivateLocation(event.getBlock().getLocation(), event.getPlayer().getUniqueId());
		return super.BlockPlaceEvent(event);
	}

	@Override
	public double getCapacity() {
		return 100000D;
	}

}
