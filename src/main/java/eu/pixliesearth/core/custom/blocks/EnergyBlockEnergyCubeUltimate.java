package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import org.bukkit.Material;

public class EnergyBlockEnergyCubeUltimate extends CustomEnergyBlock {
	
	public EnergyBlockEnergyCubeUltimate() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.PINK_TERRACOTTA;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Ultimate Energy Cube";
    }

    @Override
    public String getUUID() {
        return "Machine:Energy_Cube_Ultimate"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
	public double getCapacity() {
		return 1000000D;
	}
}
