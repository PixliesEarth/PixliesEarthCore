package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockWindTurbine extends CustomEnergyBlock {
	
	public EnergyBlockWindTurbine() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.RED_CONCRETE;
    }
	
	public double getCapacity() {
		return 10000D;
	}
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (timer==null) {
			h.registerTimer(location, new Timer(100L));
			int y = location.getBlockY()+4; // account for the top of the wind turbine
			double amountToGive;
			if (y<=60) {
				amountToGive = 0.05D;
			} else {
				amountToGive = ((y-60D)/10D);
			}
			if (isFull(location)) return;
			h.addPowerToLocation(location, amountToGive);
		} else {
			if (timer.hasExpired()) {
				h.unregisterTimer(location);
			} else {
				// do nothing
			}
		}
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Wind Turbine";
    }

    @Override
    public String getUUID() {
        return "Machine:Wind_Turbine"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
	
    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	return false;
    }
    
	@Override
    public boolean BlockPlaceEvent(BlockPlaceEvent event) {
    	Block b = event.getBlock();
    	Location loc = b.getLocation();
    	World w = loc.getWorld();
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	int x = loc.getBlockX();
    	int y = loc.getBlockY();
    	int z = loc.getBlockZ();
    	Block b2 = w.getBlockAt(x, y+1, z);
    	Block b3 = w.getBlockAt(x, y+2, z);
    	Block b4 = w.getBlockAt(x, y+3, z);
    	Block b5 = w.getBlockAt(x, y+4, z);
    	if (y > 250) 
    		return true;
    	if (b2!=null && !b2.getType().equals(MinecraftMaterial.AIR.getMaterial())) 
    		return true;
    	if (b3!=null && !b3.getType().equals(MinecraftMaterial.AIR.getMaterial())) 
    		return true;
    	if (b4!=null && !b4.getType().equals(MinecraftMaterial.AIR.getMaterial())) 
    		return true;
    	if (b5!=null && !b5.getType().equals(MinecraftMaterial.AIR.getMaterial())) 
    		return true;
    	h.setCustomBlockToLocation(new Location(w, x, y+1, z), "Machine:Wind_Turbine_BODY");
    	h.setCustomBlockToLocation(new Location(w, x, y+2, z), "Machine:Wind_Turbine_BODY");
    	h.setCustomBlockToLocation(new Location(w, x, y+3, z), "Machine:Wind_Turbine_BODY");
    	h.setCustomBlockToLocation(new Location(w, x, y+4, z), "Machine:Wind_Turbine_HEAD");
    	Bukkit.getScheduler().scheduleSyncDelayedTask(h.getInstance(), new Runnable() {
    		@Override
			public void run() {
				b.setType(getMaterial());
			}
    	}, 5L);
    	event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount()-1);
    	return false;
    }
    
    @Override
    public boolean BlockBreakEvent(BlockBreakEvent event) {
    	Block b = event.getBlock();
    	Location loc = b.getLocation();
    	World w = loc.getWorld();
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	int x = loc.getBlockX();
    	int y = loc.getBlockY();
    	int z = loc.getBlockZ();
    	Block b2 = w.getBlockAt(x, y+1, z);
    	Block b3 = w.getBlockAt(x, y+2, z);
    	Block b4 = w.getBlockAt(x, y+3, z);
    	Block b5 = w.getBlockAt(x, y+4, z);
    	h.removeCustomBlockFromLocation(b2.getLocation());
    	h.removeCustomBlockFromLocation(b3.getLocation());
    	h.removeCustomBlockFromLocation(b4.getLocation());
    	h.removeCustomBlockFromLocation(b5.getLocation());
    	Integer id = h.getLocationEvent(loc);
    	if (id==null) return false;
    	Bukkit.getScheduler().cancelTask(id);
    	return false;
    }
    
    @Override
    public boolean BlockExplodeEvent(BlockExplodeEvent event) {
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	Block b = null;
    	for (Block b2 : event.blockList().toArray(new Block[event.blockList().size()])){
			if(h.isCustomBlockAtLocation(b2.getLocation())) {
				if (CustomItemUtil.getUUIDFromLocation(b2.getLocation()).equalsIgnoreCase(getUUID())) {
					b = b2;
				}
			}
    	}
    	if (b==null) return true;
		Location loc = b.getLocation();
    	World w = loc.getWorld();
    	int x = loc.getBlockX();
    	int y = loc.getBlockY();
    	int z = loc.getBlockZ();
    	Block b2 = w.getBlockAt(x, y+1, z);
    	Block b3 = w.getBlockAt(x, y+2, z);
    	Block b4 = w.getBlockAt(x, y+3, z);
    	Block b5 = w.getBlockAt(x, y+4, z);
    	h.removeCustomBlockFromLocation(b2.getLocation());
    	h.removeCustomBlockFromLocation(b3.getLocation());
    	h.removeCustomBlockFromLocation(b4.getLocation());
    	h.removeCustomBlockFromLocation(b5.getLocation());
    	Integer id = h.getLocationEvent(loc);
    	if (id==null) return false;
    	Bukkit.getScheduler().cancelTask(id);
		return false;
	}
	
    @Override
	public boolean EntityExplodeEvent(EntityExplodeEvent event) {
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	Block b = null;
    	for (Block b2 : event.blockList().toArray(new Block[event.blockList().size()])){
			if(h.isCustomBlockAtLocation(b2.getLocation())) {
				if (CustomItemUtil.getUUIDFromLocation(b2.getLocation()).equalsIgnoreCase(getUUID())) {
					b = b2;
				}
			}
    	}
    	if (b==null) return true;
		Location loc = b.getLocation();
    	World w = loc.getWorld();
    	int x = loc.getBlockX();
    	int y = loc.getBlockY();
    	int z = loc.getBlockZ();
    	Block b2 = w.getBlockAt(x, y+1, z);
    	Block b3 = w.getBlockAt(x, y+2, z);
    	Block b4 = w.getBlockAt(x, y+3, z);
    	Block b5 = w.getBlockAt(x, y+4, z);
    	h.removeCustomBlockFromLocation(b2.getLocation());
    	h.removeCustomBlockFromLocation(b3.getLocation());
    	h.removeCustomBlockFromLocation(b4.getLocation());
    	h.removeCustomBlockFromLocation(b5.getLocation());
    	Integer id = h.getLocationEvent(loc);
    	if (id==null) return false;
    	Bukkit.getScheduler().cancelTask(id);
		return false;
	}
}
