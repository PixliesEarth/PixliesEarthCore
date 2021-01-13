package eu.pixliesearth.core.custom;

import eu.pixliesearth.core.custom.interfaces.Energyable;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Set;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to create a custom block that can store energy</h3>
 *
 */
public abstract class CustomEnergyCable extends CustomEnergyBlock {
	
	public CustomEnergyCable() {
		
	}
	
	public abstract double getTransferRate();
	
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
		Set<Block> bs = getSurroundingBlocks(location);
		if (bs.isEmpty()) return;
		for (Block b : bs) {
			CustomBlock c = h.getCustomBlockFromLocation(b.getLocation());
			if (c==null) continue;
			if (!(c instanceof Energyable)) continue;
			if (isFull(b.getLocation())) continue;
			Double d = getCapacity(b.getLocation());
			if (d==null) continue;
			if (c instanceof CustomEnergyCable) {
				if (getContainedPower(location)>(getCapacity(location)/50D)) {
					if (timer == null) {
						giveEnergy(location, b.getLocation());
						h.registerTimer(location, new Timer(15L));
					} else {
						if (timer.hasExpired()) {
							h.unregisterTimer(location);
						} else {
							// Do nothing
						}
					}
				}
			} else {
				if (c.getUUID().equalsIgnoreCase("Machine:Cable_Input")) 
					continue;
				giveEnergy(location, b.getLocation());
			}
		}
	}
	
	public boolean giveEnergy(Location cable, Location to) {
		double d = getContainedPower(cable); //TODO: make this check the block its giving it tos power
		if (d>=getTransferRate()) {
			return giveEnergy(cable, to, getTransferRate());
		} else {
			return giveEnergy(cable, to, d);
		}
	}
}
