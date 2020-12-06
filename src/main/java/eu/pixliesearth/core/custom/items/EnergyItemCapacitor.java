package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomEnergyItem;
import org.bukkit.Material;

public class EnergyItemCapacitor extends CustomEnergyItem {
	
    public EnergyItemCapacitor() {

    }

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Basic Capacitor";
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
        return "Pixlies:Capacitor_Basic"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

	@Override
	public double getCapacity() {
		return 15D;
	}
}