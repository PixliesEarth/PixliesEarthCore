package eu.pixliesearth.core.custom;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import eu.pixliesearth.utils.Timer;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to create a custom block that can store energy</h3>
 *
 */
public class CustomEnergyCable extends CustomEnergyBlock {
	
	public CustomEnergyCable() {
		
	}
	
	public double getTransferRate() {
		return 1.0D;
	}
	
	@Override
	public void open(Player player, Location location) {
		Inventory i = CustomFeatureLoader.getLoader().getHandler().getInventoryFromLocation(location);
		if (i!=null) {
			player.openInventory(i);
			return;
		}
	}
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Set<Block> bs = getSurroundingEnergyCustomBlocks(location);
		if (bs.isEmpty()) return;
		for (Block b : bs) {
			CustomBlock c = h.getCustomBlockFromLocation(location);
			Double d = getCapacity(b.getLocation());
			if (d==null) continue;
			
		}
	}
	
	public void takeEnergy(Location cable, Location from) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		double d = h.getPowerAtLocation(from);
		if (d>getTransferRate()) {
			h.removePowerFromLocation(from, getTransferRate());
			h.addPowerToLocation(cable, getTransferRate());
		} else {
			h.removePowerFromLocation(from, d);
			h.addPowerToLocation(cable, d);
		}
	}
	
	public void giveEnergy(Location cable, Location to) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Double d = getCapacity(cable);
		if (d==null) return;
		if (d>getTransferRate()) {
			h.removePowerFromLocation(cable, getTransferRate());
			h.addPowerToLocation(to, getTransferRate());
		} else {
			h.removePowerFromLocation(cable, d);
			h.addPowerToLocation(to, d);
		}
	}
}
