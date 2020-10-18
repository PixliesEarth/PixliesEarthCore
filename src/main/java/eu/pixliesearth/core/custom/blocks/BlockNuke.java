package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

public class BlockNuke extends CustomBlock {
	
	public BlockNuke() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.TNT;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Nuclear Bomb";
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
    public Rarity getRarity() {
    	return Rarity.LEGENDARY;
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
        return "Pixlies:Nuclear_Bomb"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	return false;
    }
    
	@Override
	public boolean onBlockIsInteractedWith(PlayerInteractEvent event) {
		return false;
	}

	@Override
	public boolean BlockBreakEvent(BlockBreakEvent event) {
		return false;
	}
	
	@Override
	public boolean BlockPlaceEvent(BlockPlaceEvent event) {
		if (event.isCancelled()) return true;
		event.getPlayer().sendMessage("Nuclear bomb planted! Its fuse will activate upon recieveing a redston signal!");
		return false;
	}
	
	@Override
	public void onTick(Location location) {
		if (location.getBlock().isBlockPowered()) {
			location.getBlock().setType(Material.AIR);
			CustomFeatureLoader.getLoader().getHandler().removeCustomBlockFromLocation(location);
			CustomFeatureLoader.getLoader().getHandler().setCustomBlockToLocation(location, "Pixlies:Nuclear_Bomb_Autodetinating");
		}
	}
}
