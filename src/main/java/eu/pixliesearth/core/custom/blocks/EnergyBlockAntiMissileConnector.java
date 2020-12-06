package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.Timer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EnergyBlockAntiMissileConnector extends CustomEnergyBlock {
	
	public EnergyBlockAntiMissileConnector() {
		
	}
	
	public boolean enabled = true;
	public double energyCostPerOperation = getCapacity()/20; // Five percent of max energy
	public long timePerCheck = 1000l;
	
	@Override
	public double getCapacity() {
		return 1000000D;
	}
	
	@Override
    public Material getMaterial() {
        return Material.GOLD_BLOCK;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6AntiMissile Connector";
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.BUILDING;
    }

    @Override
    public String getUUID() {
        return "Pixlies:AntiMissile_Connector"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
	@Override
    public void onTick(Location location, Inventory inventory, Timer timer) {
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	if (timer==null) {
    		Location headl = new Location(location.getWorld(), location.getX(), location.getY()+1, location.getZ()); // TODO
    		if (h.getCustomBlockFromLocation(headl)==null || !(h.getCustomBlockFromLocation(headl) instanceof BlockAntiMissileHead)) {
    			h.registerTimer(location, new Timer(timePerCheck));
    			return;
    		}
        	Location radarl = new Location(location.getWorld(), location.getX(), location.getY()-1, location.getZ());
        	CustomBlock cb = h.getCustomBlockFromLocation(radarl);
        	if (cb!=null && cb instanceof EnergyBlockICBMRadar) {
        		Inventory inv = h.getInventoryFromLocation(radarl);
        		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {

					@Override
					public void run() {
						defendAgainst(location, getWhereToDefendFromInventory(inv, location.getWorld()));
					}
					
        		}, 1l);
        		h.registerTimer(location, new Timer(timePerCheck));
        	} else {
        		h.registerTimer(location, new Timer(timePerCheck));
        	}
    	} else {
    		if (timer.hasExpired()) {
    			h.unregisterTimer(location);
    		} else {
    			// Do nothing
    		}
    	}
    }
    
    public Location getWhereToDefendFromInventory(Inventory inv, World world) {
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	for (ItemStack is : inv.getContents()) {
    		if (is.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
    			String[] s = ChatColor.stripColor(is.getDisplayName()).split(", ");// "§b" + chunkX + "§8, §b" + chunkZ
    			Chunk c = world.getChunkAt((s[0].contains("-") ? -Integer.parseUnsignedInt(s[0].replaceAll("-", "")) : Integer.parseUnsignedInt(s[0])), (s[1].contains("-") ? -Integer.parseUnsignedInt(s[1].replaceAll("-", "")) : Integer.parseUnsignedInt(s[1])));
    			final int minX = c.getX() << 4;
    	        final int minZ = c.getZ() << 4;
    	        final int maxX = minX | 15;
    	        final int maxY = 245;
    	        final int maxZ = minZ | 15;
    			for (int y = maxY; y >= 0; --y) {
    	        	for (int x = minX; x <= maxX; ++x) {
    	        		for (int z = minZ; z <= maxZ; ++z) {
    	        			Block b = c.getBlock(x & 15, y, z & 15);
    	        			CustomBlock cb = h.getCustomBlockFromLocation(b.getLocation());
    	        			if (cb!=null && cb instanceof BlockMissileWarhead3) {
    	        				try {
	    	        				if (CustomItemUtil.getCustomItemFromUUID(CustomItemUtil.getUUIDFromLocation(c.getBlock(x & 15, y+1, z & 15).getLocation())) instanceof BlockMissile2) {
	    	        					return b.getLocation();
	    	        				}
    	        				} catch (Exception ingore) {/* Ignore */}
    	        			}
    	        		}
    	        	}
    			}
    		}
    	}
    	return null;
    }
    
	public void defendAgainst(Location location, Location missile) {
		if (location==null || missile==null) return;
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	h.removeCustomBlockFromLocation(new Location(location.getWorld(), location.getX(), location.getY()+1D, location.getZ()));
    	h.setCustomBlockToLocation(new Location(missile.getWorld(), missile.getX(), missile.getY()-1D, missile.getZ()), h.getCustomItemFromClass(BlockAntiMissileHead.class).getUUID(), true);
    }
	
}
