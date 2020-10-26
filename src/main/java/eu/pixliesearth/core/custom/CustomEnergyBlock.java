package eu.pixliesearth.core.custom;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.block.Block;
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
public class CustomEnergyBlock extends CustomMachine {
	
	public CustomEnergyBlock() {
		
	}
	
	@Override
	public void open(Player player, Location location) {
		Inventory i = CustomFeatureLoader.getLoader().getHandler().getInventoryFromLocation(location);
		if (i!=null) {
			player.openInventory(i);
			return;
		}
		player.sendMessage(CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location)+"/"+getCapacity());
	}
	
	public double getCapacity() {
		return 100D;
	}
	
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
	/**
	 * Returns all blocks around it that have an energy value (this includes machines)
	 */
	public Set<Block> getSurroundingEnergyCustomBlocks(Location location) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Set<Block> set = new HashSet<Block>();
		for (Block b : getSurroundingBlocks(location)) {
	   		if (h.getPowerAtLocation(location)!=null)
	   			set.add(b);
	   	}
		return set;
	}
	
	@Deprecated
	@Override
	public String getPlayerHeadUUID() {
		return null;
	}
	
	public boolean isFull(Location loc) {
		return getContainedPower(loc)>=getCapacity(loc);
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
		try {
			Method m = c.getClass().getDeclaredMethod("getCapacity", (Class<?>[]) null);
			return (m==null) ? null : (Double) m.invoke(c, (Object[]) null);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return null;
		}
	}
	
	public void takeEnergy(Location to, Location from, double amount) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		double d = h.getPowerAtLocation(from);
		double d2 = h.getPowerAtLocation(to);
		if (isFull(to) || (d2+amount)>=getCapacity(to)) return;
		if (d<=0 || (d-amount)<=0) return;
		h.removePowerFromLocation(from, amount);
		h.addPowerToLocation(to, amount);
	}
	
	public void giveEnergy(Location from, Location to, double amount) {
		takeEnergy(to, from, amount);
	}
	
	public void giveAllEnergy(Location from, Location to) {
    	takeAllEnergy(to, from);
    }
	
	public void takeAllEnergy(Location to, Location from) {
    	if (isFull(to)) return;
    	Double d = getContainedPower(to);
    	Double d2 = getCapacity(to);
    	Double d3 = getContainedPower(from);
    	Double d4 = getCapacity(from);
    	if (d==null || d2==null || d3==null || d4==null) return;
    	double d5 = 0D;
    	if ((d4-d3)>=d) 
    		d5 += d;
    	else 
    		d5 += (d4-d3);
    	takeEnergy(to, from, d5);
    }
}
