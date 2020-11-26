package eu.pixliesearth.core.custom.items;

import org.bukkit.Material;

import eu.pixliesearth.core.custom.CustomEnergyItem;
import eu.pixliesearth.core.custom.interfaces.IMissileFuel;

public class EnergyItemBattery extends CustomEnergyItem implements IMissileFuel {
	
    public EnergyItemBattery() {

    }

    @Override
    public Material getMaterial() {
        return Material.FIREWORK_STAR;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Battery";
    }

    @Override
    public Integer getCustomModelData() {
        return 4;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.MISC;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Battery"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

	@Override
	public double getCapacity() {
		return 500;
	}

	@Override
	public int getMissileExplosiveValue() {
		return 6;
	}

	@Override
	public int getMissileRangeValue() {
		return 0;
	}

	@Override
	public int getMissilePlayerDamageValue() {
		return 0;
	}

	@Override
	public int getMissileLaunchTimeValue() {
		return 1;
	}
}