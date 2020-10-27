package eu.pixliesearth.core.custom;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.Timer;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to create a custom block that can store energy</h3>
 *
 */
public abstract class CustomEnergyBlock extends CustomMachine implements Energyable {
	
	public CustomEnergyBlock() {
		
	}
	
	@Override
	public void open(Player player, Location location) {
		Inventory i = CustomFeatureLoader.getLoader().getHandler().getInventoryFromLocation(location);
		if (i!=null) {
			player.openInventory(i);
			return;
		}
	}
	
	public abstract double getCapacity();
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Location location2 = new Location(location.getWorld(), location.getBlockX(), location.getBlockY()+1, location.getBlockZ());
		Double d = h.getPowerAtLocation(location2);
		if (d==null || d<=0 || isFull(location)) return;
		double amountToRemove = 1;
		h.removePowerFromLocation(location2, amountToRemove);
		h.addPowerToLocation(location, amountToRemove);
	}
	
	@Deprecated
	@Override
	public String getPlayerHeadUUID() {
		return null;
	}
	
	public boolean isFull(Location loc) {
		Double d = getContainedPower(loc);
		Double d2 = getCapacity(loc);
		return (d==null||d2==null) ? true : d>=d2; // If null send a fake true value
	}
	
	public Double getContainedPower(Location location) {
		return CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location);
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
	
	@Override
	public ItemStack buildItem() {
		return new ItemBuilder(getMaterial()) {{
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
			}}.build();
	}
	
	@Override
	public boolean BlockPlaceEvent(BlockPlaceEvent event) {
		CustomFeatureLoader.getLoader().getHandler().addPowerToLocation(event.getBlock().getLocation(), 0D); // Register that it has energy
		return false;
	}
	
	public Double getCapacity(Location location) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		CustomBlock c = h.getCustomBlockFromLocation(location);
		if (c==null) return null;
		if (c instanceof Energyable) {
			return ((Energyable)c).getCapacity();
		} else {
			return null;
		}
	}
	
	public boolean takeEnergy(Location to, Location from, double amount) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Double d = h.getPowerAtLocation(from);
		Double d2 = h.getPowerAtLocation(to);
		if (d==null || d2==null) return false;
		if (isFull(to) || (d2+amount)>getCapacity(to)) return false;
		if (d<=0 || (d-amount)<0) return false;
		h.removePowerFromLocation(from, amount);
		h.addPowerToLocation(to, amount);
		return true;
	}
	
	public boolean giveEnergy(Location from, Location to, double amount) {
		return takeEnergy(to, from, amount);
	}
}
