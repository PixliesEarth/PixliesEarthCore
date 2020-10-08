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

public class TestBlock extends CustomBlock {
	
	public TestBlock() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.RED_STAINED_GLASS;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Test Block";
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
        return CreativeTabs.REDSTONE;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Test_Block"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	event.getPlayer().sendMessage("§rYou clicked with the custom block §a"+getDefaultDisplayName()+"§r(§a"+getUUID()+"§r)");
        return false;
    }
    
	@Override
	public boolean onBlockIsInteractedWith(PlayerInteractEvent event) {
		Location l = event.getClickedBlock().getLocation();
		event.getPlayer().sendMessage("§rYou clicked on the custom block §a"+getDefaultDisplayName()+"§r(§a"+getUUID()+"§r) at §a"+l.getBlockX()+"§r, §a"+l.getBlockY()+"§r, §a"+l.getBlockZ());
		return false;
	}

	@Override
	public boolean BlockBreakEvent(BlockBreakEvent event) {
		Location l = event.getBlock().getLocation();
		event.getPlayer().sendMessage("§rYou broke the custom block §a"+getDefaultDisplayName()+"§r(§a"+getUUID()+"§r) at §a"+l.getBlockX()+"§r, §a"+l.getBlockY()+"§r, §a"+l.getBlockZ());
		return false;
	}
	
	@Override
	public boolean BlockPlaceEvent(BlockPlaceEvent event) {
		Location l = event.getBlock().getLocation();
		event.getPlayer().sendMessage("§rYou placed the custom block §a"+getDefaultDisplayName()+"§r(§a"+getUUID()+"§r) at §a"+l.getBlockX()+"§r, §a"+l.getBlockY()+"§r, §a"+l.getBlockZ());
		return false;
	}
	
}
