package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockWindTurbine extends CustomEnergyBlock {
	
	public EnergyBlockWindTurbine() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.RED_CONCRETE;
    }
	
	@Override
	public double getCapacity() {
		return 10000D;
	}
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		int y = location.getBlockY()+4; // account for the top of the wind turbine
		if (y<60) return;
		double amountToGive = ((y-60D)/10D);
		if (amountToGive+getContainedPower(location)>=getCapacity()) return;
		h.addPowerToLocation(location, amountToGive);
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
    	int y = event.getClickedBlock().getY();
    	if (y<60) event.getPlayer().sendMessage("The windmill can't seem to catch any wind here, maybe try making it further up!");
    	return false;
    }
    
    @SuppressWarnings("deprecation") // For async stuff
	@Override
    public boolean BlockPlaceEvent(BlockPlaceEvent event) {
    	Block b = event.getBlock();
    	Location loc = b.getLocation();
    	World w = loc.getWorld();
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	if (loc.getWorld().getEnvironment().equals(Environment.NETHER)) { // No using to break bedrock
    		event.getPlayer().sendMessage("It is to hot to place a wind turbine here!");
    		return true;
    	}
    	int x = loc.getBlockX();
    	int y = loc.getBlockY();
    	int z = loc.getBlockZ();
    	Block b2 = w.getBlockAt(x, y+1, z);
    	Block b3 = w.getBlockAt(x, y+2, z);
    	Block b4 = w.getBlockAt(x, y+3, z);
    	Block b5 = w.getBlockAt(x, y+4, z);
    	if (y > 250) 
    		return true;
    	if (!b2.getType().equals(MinecraftMaterial.AIR.getMaterial())) 
    		return true;
    	if (!b3.getType().equals(MinecraftMaterial.AIR.getMaterial())) 
    		return true;
    	if (!b4.getType().equals(MinecraftMaterial.AIR.getMaterial())) 
    		return true;
    	if (!b5.getType().equals(MinecraftMaterial.AIR.getMaterial())) 
    		return true;
    	h.setCustomBlockToLocation(b2.getLocation(), "Pixlies:Wind_Turbine_BODY");
    	h.setCustomBlockToLocation(b3.getLocation(), "Pixlies:Wind_Turbine_BODY");
    	h.setCustomBlockToLocation(b4.getLocation(), "Pixlies:Wind_Turbine_BODY");
    	h.setCustomBlockToLocation(b5.getLocation(), "Pixlies:Wind_Turbine_HEAD");
    	int id = Bukkit.getScheduler().scheduleAsyncRepeatingTask(h.getInstance(), new Runnable() {
			@Override
			public void run() {
				// TODO: make banners spin
			}
    	}, 1L, 1L);
    	h.registerLocationEvent(loc, id);
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
    	int id = h.getLocationEvent(loc);
    	Bukkit.getScheduler().cancelTask(id);
    	return false;
    }
}
