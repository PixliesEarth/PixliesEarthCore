package eu.pixliesearth.core.custom;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
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
		if (CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location)==getCapacity()) {
			player.sendMessage("full");
		} else if (CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location)<=0) {
			player.sendMessage("empty");
		} else if (CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location)>getCapacity()) {
			player.sendMessage("over full");
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
	
	@Deprecated
	@Override
	public String getPlayerHeadUUID() {
		return null;
	}
	
	public boolean isFull(Location loc) {
		return getContainedPower(loc)>=getCapacity();
	}
	
	public double getContainedPower(Location location) {
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
}
