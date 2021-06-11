package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemCanister extends CustomItem {
	
	public ItemCanister() {
		
	}
	
	@Override
	public Material getMaterial() {
		return Material.BUCKET;
	}
	@Override
	public String getDefaultDisplayName() {
		return "§6Empty Canister";
	}

	@Override
	public Integer getCustomModelData() {
		return 4;
	}
	
	@Override
	public String getUUID() {
		return "Pixlies:Canister"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
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
