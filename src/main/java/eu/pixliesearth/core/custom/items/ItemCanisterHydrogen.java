package eu.pixliesearth.core.custom.items;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.interfaces.ILiquidable;

public class ItemCanisterHydrogen extends CustomItem {
	
	public ItemCanisterHydrogen() {
		
	}
	
	private String id = ILiquidable.hydrogenID;
	private String name = id.split(":")[1];
	
	@Override
	public Material getMaterial() {
		return Material.BUCKET;
	}
	@Override
	public String getDefaultDisplayName() {
		return "§6"+name+" Canister";
	}

	@Override
	public Integer getCustomModelData() {
		return null;
	}
	
	@Override
	public String getUUID() {
		return "Pixlies:Canister_"+name; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
	}
	
	@Override
	public boolean PlayerInteractEvent(PlayerInteractEvent event) {
		return true;
	}
	
	@Override
    public boolean isUnstackable() {
    	return true;
    }
}
