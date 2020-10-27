package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Material;

import eu.pixliesearth.core.custom.CustomEnergyCable;

public class EnergyCableAdvanced extends CustomEnergyCable {
	
	public EnergyCableAdvanced() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.RED_STAINED_GLASS_PANE;
    }
	
	public double getCapacity() {
		return 100000D;
	}
	
	public double getTransferRate() {
		return 5000D;
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Advanced Cable";
    }
    
    @Override
    public Rarity getRarity() {
    	return Rarity.RARE;
    }
    
    @Override
    public String getUUID() {
        return "Machine:Cable_Advanced"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
}
