package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomEnergyCable;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.Energyable;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockCableInput extends CustomEnergyBlock {
	
	public EnergyBlockCableInput() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.YELLOW_CONCRETE;
    }
	
	public double getCapacity() {
		return 1000000000D;
	}
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		for (Block b : getSurroundingBlocks(location)) {
			CustomBlock c = h.getCustomBlockFromLocation(b.getLocation());
			if (c==null) continue;
			if (!(c instanceof Energyable)) continue;
			if (c instanceof CustomEnergyCable) {
				Double d = getContainedPower(location);
				Double d2 = getCapacity(b.getLocation());
				Double d3 = getContainedPower(b.getLocation());
				if (d==null || d2==null || d3==null) continue;
				if (d<(d2-d3)) {
					giveEnergy(location, b.getLocation(), getContainedPower(location));
				} else {
					if (!giveEnergy(location, b.getLocation(), (getCapacity(b.getLocation())-getContainedPower(b.getLocation())))) {
						giveEnergy(location, b.getLocation(), 1D);
					}
				}
			} else {
				Double d = getContainedPower(b.getLocation());
				if (d==null) continue;
				takeEnergy(location, b.getLocation(), d);
			}
		}
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Cable Input";
    }

    @Override
    public String getUUID() {
        return "Machine:Cable_Input"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
}
