package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Material;

import eu.pixliesearth.core.custom.CustomEnergyBlock;

public class EnergyBlockEnergyCubeBasic extends CustomEnergyBlock {
	
	public EnergyBlockEnergyCubeBasic() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.LIGHT_GRAY_TERRACOTTA;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Basic Energy Cube";
    }

    @Override
    public String getUUID() {
        return "Machine:Energy_Cube_Basic"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
	public double getCapacity() {
		return 1000D;
	}
}
