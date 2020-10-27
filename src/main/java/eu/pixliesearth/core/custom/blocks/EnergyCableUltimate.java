package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Material;

import eu.pixliesearth.core.custom.CustomEnergyCable;

public class EnergyCableUltimate extends CustomEnergyCable {
	
	public EnergyCableUltimate() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.PINK_STAINED_GLASS_PANE;
    }
	
	public double getCapacity() {
		return 1000000D;
	}
	
	public double getTransferRate() {
		return 500000D;
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Ultimate Cable";
    }
    
    @Override
    public Rarity getRarity() {
    	return Rarity.VERYRARE;
    }

    @Override
    public String getUUID() {
        return "Machine:Cable_Ultimate"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
}
