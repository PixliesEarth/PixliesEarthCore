package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomEnergyCable;
import org.bukkit.Material;

public class EnergyCableIntermediate extends CustomEnergyCable {
	
	public EnergyCableIntermediate() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.BROWN_STAINED_GLASS_PANE;
    }
	
	public double getCapacity() {
		return 10000D;
	}
	
	public double getTransferRate() {
		return 500D;
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Intermediate Cable";
    }
    
    @Override
    public Rarity getRarity() {
    	return Rarity.UNCOMMON;
    }
    
    @Override
    public String getUUID() {
        return "Machine:Cable_Intermediate"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
}
