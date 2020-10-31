package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomSavableBlock;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class SavableChunkLoader extends CustomSavableBlock {
	
	public SavableChunkLoader() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.OBSIDIAN;
    }
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6ChunkLoader";
    }

    @Override
    public String getUUID() {
        return "Pixlies:ChunkLoader"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
		if (map.get("TIMEREX")!=null && map.get("TIMEREN")!=null)
			CustomFeatureLoader.getLoader().getHandler().registerTimer(location, new Timer(Long.parseLong(map.get("TIMEREX")), Boolean.getBoolean(map.get("TIMEREN"))));
		location.getChunk().setForceLoaded(true);
	}
	
	@Override
	public HashMap<String, String> getSaveData(Location location, Inventory inventory, Timer timer) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (timer!=null) {
			map.put("TIMEREX", Long.toString(timer.getExpiry()));
			map.put("TIMEREN", Boolean.toString(timer.isEnded()));
		}
		return map;
	}
    
    @Override
    public boolean BlockBreakEvent(BlockBreakEvent event) {
    	event.getBlock().getLocation().getChunk().setForceLoaded(false);
    	return true;
    }
    
    @Override
    public boolean BlockPlaceEvent(BlockPlaceEvent event) {
    	event.getBlock().getLocation().getChunk().setForceLoaded(true);
    	return false;
    }
}
