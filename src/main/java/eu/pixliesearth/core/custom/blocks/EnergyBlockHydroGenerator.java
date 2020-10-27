package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockHydroGenerator extends CustomEnergyBlock {
	
	public EnergyBlockHydroGenerator() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.BLUE_STAINED_GLASS;
    }
	
	public double getCapacity() {
		return 10D;
	}
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (timer==null) {
			h.registerTimer(location, new Timer(1000L));
			if (isFull(location)) return;
			h.addPowerToLocation(location, (location.getBlockY()<17) ? 0.2 : 0.1);
			return;
		} else {
			if (timer.hasExpired()) {
				h.unregisterTimer(location);
			} else {
				// Do nothing
				return;
			}
		}
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Solar Panel";
    }

    @Override
    public String getUUID() {
        return "Machine:Solar_Panel"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
	
}
