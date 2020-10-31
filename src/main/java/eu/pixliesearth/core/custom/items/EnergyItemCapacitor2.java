package eu.pixliesearth.core.custom.items;

import org.bukkit.Material;

import eu.pixliesearth.core.custom.CustomEnergyItem;

public class EnergyItemCapacitor2 extends CustomEnergyItem {
	
    public EnergyItemCapacitor2() {

    }

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Intermediate Capacitor";
    }

    @Override
    public Integer getCustomModelData() {
        return null;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.MISC;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Capacitor_Intermediate"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

	@Override
	public double getCapacity() {
		return 150D;
	}
}