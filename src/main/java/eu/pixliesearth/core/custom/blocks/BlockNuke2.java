package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

public class BlockNuke2 extends CustomBlock {
	
	public BlockNuke2() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.TNT;
    }

    @Override
    public List<String> getDefaultLore() {
        return new ArrayList<String>() {/** * */private static final long serialVersionUID = -208046975515613148L;{
        	add("§c§lUpon being placed the fuse goes off, then you only have 60 seconds to run.");
        }};
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Autodetinating Nuclear Bomb";
    }

    @Override
    public boolean isGlowing() {
        return true;
    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }

    @Override
    public Map<String, Object> getDefaultNBT() {
        return new HashMap<String, Object>();
    }

    @Override
    public Map<Enchantment, Integer> getDefaultEnchants() {
        return new HashMap<Enchantment, Integer>();
    }

    @Override
    public Set<ItemFlag> getItemFlags(){
        return new HashSet<ItemFlag>();
    }

    @Override
    public Integer getCustomModelData() {
        return null;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.COMBAT;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Nuclear_Bomb_Autodetinating"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	return false;
    }
    
    @Override
    public Rarity getRarity() {
    	return Rarity.LEGENDARY;
    }
    
	@Override
	public boolean onBlockIsInteractedWith(PlayerInteractEvent event) {
		return false;
	}

	@Override
	public boolean BlockBreakEvent(BlockBreakEvent event) {
		event.getPlayer().sendMessage("Its too late for this now!");
		return true;
	}
	
	@Override
	public boolean BlockPlaceEvent(BlockPlaceEvent event) {
		if (event.isCancelled()) return true;
		Block b = event.getBlock();
		Location loc = b.getLocation();
		event.getPlayer().sendMessage("Nuclear bomb planted! It will explode in 60 seconds!");
		Integer i = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
			public void run() {
				b.setType(Material.AIR);
				loc.getWorld().createExplosion(loc, 200f);
				CustomFeatureLoader.getLoader().getHandler().removeCustomBlockFromLocation(event.getBlock().getLocation());
				CustomFeatureLoader.getLoader().getHandler().unregisterLocationEvent(loc);
			}
		}, 1200L);
		CustomFeatureLoader.getLoader().getHandler().registerLocationEvent(loc, i);
		return false;
	}
	
	@Override
	public boolean BlockExplodeEvent(BlockExplodeEvent event) {
		return true;
	}
	
	@Override
	public boolean EntityExplodeEvent(EntityExplodeEvent event) {
		return true;
	}
	
}
