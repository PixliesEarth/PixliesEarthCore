package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomEnergyItem;
import org.bukkit.Material;

public class EnergyItemCapacitor3 extends CustomEnergyItem {
	
    public EnergyItemCapacitor3() {

    }

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Advanced Capacitor";
    }

    @Override
    public Integer getCustomModelData() {
        return 3;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.MISC;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Capacitor_Advanced"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

	@Override
	public double getCapacity() {
		return 1500D;
	}
}