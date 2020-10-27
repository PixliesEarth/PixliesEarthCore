package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Material;

import eu.pixliesearth.core.custom.CustomEnergyBlock;

public class TestEnergyBlock extends CustomEnergyBlock {
	
	public TestEnergyBlock() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.GOLD_BLOCK;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Test Energy Block";
    }

    @Override
    public String getUUID() {
        return "Pixlies:Test_Energy_Block"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
	public double getCapacity() {
		return 100D;
	}
}
