package eu.pixliesearth.core.custom.blocks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import eu.pixliesearth.core.custom.CustomBlock;

public class BlockSteel extends CustomBlock {
	
	public BlockSteel() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.IRON_BLOCK;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Steel Block";
    }

    @Override
    public boolean isGlowing() {
        return false;
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
        return CreativeTabs.BUILDING;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Steel_Block"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
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
		return false;
	}
	
}