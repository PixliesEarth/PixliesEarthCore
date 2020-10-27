package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockNuclearGenerator extends CustomEnergyBlock {
	
	public EnergyBlockNuclearGenerator() {
		
	}
	
	public double getCapacity() {
		return 1000000D;
	}
	
	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(null, 5*9);
		for (int i = 0; i < 5*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		// TODO: make inv
		return inv;
	}
	
	@Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		
	}
	
	@Override
	public Material getMaterial() {
		return Material.LODESTONE;
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Nuclear Reactor";
	}
    
    @Override
    public String getUUID() {
        return "Machine:Nuclear_Generator"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
}
