package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockSolarPanel extends CustomEnergyBlock {
	
	public EnergyBlockSolarPanel() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.DAYLIGHT_DETECTOR;
    }
	
	public double getCapacity() {
		return 1000D;
	}
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		byte byt = location.getBlock().getLightFromSky();
		if (byt > (byte)7) {
			if (isFull(location)) return;
			h.addPowerToLocation(location, 0.1D);
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
