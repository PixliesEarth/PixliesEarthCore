package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomEnergyCable;
import org.bukkit.Material;

public class EnergyCableBasic extends CustomEnergyCable {
	
	public EnergyCableBasic() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.LIGHT_GRAY_STAINED_GLASS_PANE;
    }
	
	public double getCapacity() {
		return 100D;
	}
	
	public double getTransferRate() {
		return 5D;
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Basic Cable";
    }

    @Override
    public Integer getCustomModelData() {
        return 2;
    }

    @Override
    public String getUUID() {
        return "Machine:Cable_Basic"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
}
