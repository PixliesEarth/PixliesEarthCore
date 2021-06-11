package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomEnergyItem;
import eu.pixliesearth.core.custom.interfaces.IMissileFuel;
import org.bukkit.Material;

public class EnergyItemBattery extends CustomEnergyItem {
	
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

}