package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.Inventory;

public class EnergyBlockTickBooster extends CustomEnergyBlock {
	
	public EnergyBlockTickBooster() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.BLUE_GLAZED_TERRACOTTA;
    }
	
	@Override
	public Inventory getInventory() {
		return null;
	}
	
	public double getCapacity() {
		return 350000D;
	}
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		double energyPerOperation = 500D;
		if (timer==null) {
			h.registerTimer(location, new Timer(500L));
			for (Block b : getSurroundingCustomBlocks(location)) {
				CustomBlock cb = h.getCustomBlockFromLocation(b.getLocation());
				if (cb==null) continue;
				if (getContainedPower(location)<energyPerOperation) continue;
				cb.onTick(b.getLocation());
				h.removePowerFromLocation(location, energyPerOperation);
			}
			return;
		} else {
			if (timer.hasExpired()) {
				h.unregisterTimer(location);
				return;
			} else {
				// Do nothing
				return;
			}
		}
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6TickBooster";
    }

    @Override
    public String getUUID() {
        return "Pixlies:TickBooster"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
    public boolean BlockExplodeEvent(BlockExplodeEvent event) {
		return true;
	}
	
    @Override
	public boolean EntityExplodeEvent(EntityExplodeEvent event) {
		return true;
	}
    
}
