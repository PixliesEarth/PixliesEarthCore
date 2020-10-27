package eu.pixliesearth.core.custom.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomSavableBlock;
import eu.pixliesearth.utils.Timer;

public class SavablePrivateChest extends CustomSavableBlock {
	
	public SavablePrivateChest() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.CHEST;
    }
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Private Chest";
    }

    @Override
    public String getUUID() {
        return "Pixlies:Chest_Private"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
		if (map.get("TIMEREX")!=null && map.get("TIMEREN")!=null)
			CustomFeatureLoader.getLoader().getHandler().registerTimer(location, new Timer(Long.parseLong(map.get("TIMEREX")), Boolean.getBoolean(map.get("TIMEREN"))));
		CustomFeatureLoader.getLoader().getHandler().registerPrivateLocation(location, Bukkit.getOfflinePlayer(UUID.fromString(map.get("OWNER"))));
	}
	
	@Override
	public HashMap<String, String> getSaveData(Location location, Inventory inventory, Timer timer) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (timer!=null) {
			map.put("TIMEREX", Long.toString(timer.getExpiry()));
			map.put("TIMEREN", Boolean.toString(timer.isEnded()));
		}
		map.put("OWNER", CustomFeatureLoader.getLoader().getHandler().getPrivateLocation(location).toString());
		return map;
	}
    
    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	Player p = event.getPlayer();
    	Location l = event.getClickedBlock().getLocation();
    	if (event.getPlayer().equals(CustomFeatureLoader.getLoader().getHandler().getPrivateLocation(l))) {
    		return false;
    	}
    	p.sendMessage("This chest is locked to the player "+CustomFeatureLoader.getLoader().getHandler().getPrivateLocation(l).getName());
    	return true;
    }
    
    /**@Override
    public boolean BlockBreakEvent(BlockBreakEvent event) {
    	Location l = event.getBlock().getLocation();
    	Player p = event.getPlayer();
    	if (event.getPlayer().equals(CustomFeatureLoader.getLoader().getHandler().getPrivateLocation(l))) {
    		return false;
    	}
    	p.sendMessage("This chest is locked to the player "+CustomFeatureLoader.getLoader().getHandler().getPrivateLocation(l).getName());
    	return true;
    } */
    
    @Override
    public boolean BlockPlaceEvent(BlockPlaceEvent event) {
    	CustomFeatureLoader.getLoader().getHandler().registerPrivateLocation(event.getBlock().getLocation(), event.getPlayer());
    	event.getPlayer().sendMessage("You have placed a lock chest");
    	return false;
    }
}
