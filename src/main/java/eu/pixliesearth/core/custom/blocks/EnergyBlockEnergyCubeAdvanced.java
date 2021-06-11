package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import org.bukkit.Material;

public class EnergyBlockEnergyCubeAdvanced extends CustomEnergyBlock {
	
	public EnergyBlockEnergyCubeAdvanced() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.RED_TERRACOTTA;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Advanced Energy Cube";
    }

    @Override
    public String getUUID() {
        return "Machine:Energy_Cube_Advanced"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    public double getCapacity() {
		return 450000D;
	}
}
