package eu.pixliesearth.core.custom.blocks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import eu.pixliesearth.core.custom.CustomBlock;

public class BlockPlastic3 extends CustomBlock {
	
	public BlockPlastic3() {
		
	}
	
	@Override
    public Material getMaterial() {
		return Material.WHITE_STAINED_GLASS;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Hard Plastic Block";
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
        return "Pixlies:Plastic_Hard_Block"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
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
		Location l = event.getBlock().getLocation().clone();
		Location l2 = l.clone();
		l2.setY(l.getY()-1);
		if (l2.getBlock()==null) {
			event.getPlayer().sendMessage("§aThis block seems to be to hard for you to break, maybe try heating it up?");
			return true;
		} else if (!l2.getBlock().getType().equals(Material.FIRE)) {
			event.getPlayer().sendMessage("§aThis block seems to be to hard for you to break, maybe try heating it up?");
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean BlockPlaceEvent(BlockPlaceEvent event) {
		return false;
	}
	
}
