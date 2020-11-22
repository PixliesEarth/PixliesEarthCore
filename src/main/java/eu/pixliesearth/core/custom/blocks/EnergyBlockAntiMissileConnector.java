package eu.pixliesearth.core.custom.blocks;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.utils.Timer;
import net.md_5.bungee.api.ChatColor;

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
    			h.registerTimer(location, new Timer(500l));
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
        		h.registerTimer(location, new Timer(500l));
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
    	for (ItemStack is : inv.getContents()) {
    		int i = getDangerFromInfoItem(is);
    		if (i>0) {
    			String[] s = ChatColor.stripColor(is.getDisplayName()).split(", ");// "§b" + chunkX + "§8, §b" + chunkZ
    			world.getChunkAt(Integer.parseUnsignedInt(s[0]), Integer.parseUnsignedInt(s[1]));
    			
    		} else 
    			continue;
    	}
    	return null;
    }
    
    public int getDangerFromInfoItem(ItemStack is) {
    	try {
    		return Integer.parseUnsignedInt(ChatColor.stripColor(is.getLore().get(3)).replaceAll("Launched Missiles: ", ""));
		} catch (Exception e) {
			return 0;
		}
    }
    
	public void defendAgainst(Location location, Location missile) {
		if (location==null || missile==null) return;
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	Block b = missile.getWorld().getBlockAt(missile.getBlockX(), missile.getBlockY()-1, missile.getBlockZ());
		Block b2 = missile.getWorld().getBlockAt(missile.getBlockX(), missile.getBlockY()-2, missile.getBlockZ());
		Material m = (b==null) ? Material.AIR : b.getType();
		Material m2 = (b2==null) ? Material.AIR : b2.getType();
		CustomBlock cb = h.getCustomBlockFromLocation(new Location(missile.getWorld(), missile.getBlockX(), missile.getBlockY()+1, missile.getBlockZ()));
		if (cb==null || !(cb instanceof BlockMissile)) return;
		b.setType(Material.GLASS);
		b2.setType(Material.GLASS);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {

			@Override
			public void run() {
				
				b.setType(m);
	    		b2.setType(m2);
	    		
			}
			
		}, 5l);
    }
	
}
