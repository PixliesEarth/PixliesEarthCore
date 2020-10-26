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
			if (c instanceof CustomEnergyCable) {
				if (isFull(location)) 
					giveEnergy(location, b.getLocation());
			} else {
				giveEnergy(location, b.getLocation());
			}
		}
	}
	
	public void takeEnergy(Location cable, Location from) {
		double d = getContainedPower(from);
		if (d>=getTransferRate()) {
			takeEnergy(cable, from, getTransferRate());
		} else {
			takeEnergy(cable, from, d);
		}
	}
	
	public void giveEnergy(Location cable, Location to) {
		double d = getContainedPower(cable);
		if (d>=getTransferRate()) {
			giveEnergy(cable, to, getTransferRate());
		} else {
			giveEnergy(cable, to, d);
		}
	}
}
