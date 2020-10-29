package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Material;

import eu.pixliesearth.core.custom.CustomEnergyBlock;

public class EnergyBlockEnergyCubeIntermediate extends CustomEnergyBlock {
	
	public EnergyBlockEnergyCubeIntermediate() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.BROWN_TERRACOTTA;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Intermediate Energy Cube";
    }

    @Override
    public String getUUID() {
        return "Machine:Energy_Cube_Intermediate"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
	public double getCapacity() {
		return 50000D;
	}
}
