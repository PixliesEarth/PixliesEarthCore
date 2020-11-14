package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.utils.Timer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class EnergyBlockEnergyCubeCreative extends CustomEnergyBlock {
	
	public EnergyBlockEnergyCubeCreative() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.PURPLE_TERRACOTTA;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Creative Energy Cube";
    }

    @Override
    public String getUUID() {
        return "Machine:Energy_Cube_Creative"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
	public double getCapacity() {
		return 1000000000D;
	}
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		CustomFeatureLoader.getLoader().getHandler().addPowerToLocation(location, getCapacity()-CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location));
	}
}
