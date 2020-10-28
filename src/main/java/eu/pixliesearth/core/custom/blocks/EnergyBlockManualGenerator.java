package eu.pixliesearth.core.custom.blocks;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.Energyable;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockManualGenerator extends CustomEnergyBlock {
	
	public EnergyBlockManualGenerator() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.SMOOTH_STONE;
    }
	
	public double getCapacity() {
		return 1D;
	}
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (timer==null) {
			h.registerTimer(location, new Timer(100L));
			if (!(h.getTempratureAtLocation(location)<= 0D)) {
				h.removeTempratureFromLocation(location, 0.01D);
			}
			Set<Block> bl = getSurroundingBlocks(location);
			for (Block b : bl) {
				CustomBlock c = h.getCustomBlockFromLocation(b.getLocation());
				if (c==null) continue;
				if (!(c instanceof Energyable)) continue;
				if (h.getPowerAtLocation(location)>0.05D) {
					giveEnergy(location, b.getLocation(), 0.05D);
				}
			}
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
        return "§6Manual Generator";
    }

    @Override
    public String getUUID() {
        return "Machine:Manual_Generator"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	if (isFull(event.getClickedBlock().getLocation())) return true;
    	if (h.getTempratureAtLocation(event.getClickedBlock().getLocation())>15D) {
    		event.getPlayer().sendMessage("It is to hot to do this!");
    	} else {
    		h.addPowerToLocation(event.getClickedBlock().getLocation(), 0.05D);
        	h.addTempratureToLocation(event.getClickedBlock().getLocation(), 0.25D);
    	}
    	return true;
    }
    
    @Override
    public boolean BlockPlaceEvent(BlockPlaceEvent event) {
    	CustomFeatureLoader.getLoader().getHandler().addPowerToLocation(event.getBlock().getLocation(), 0D); // Register that it has energy
    	CustomFeatureLoader.getLoader().getHandler().addTempratureToLocation(event.getBlock().getLocation(), 0D); // Register that it has temp
    	return false;
    }
	
}
