package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomBlock;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

public class EnergyBlockWindTurbine3 extends CustomBlock {
	
	public EnergyBlockWindTurbine3() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.WHITE_CONCRETE;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Wind Turbine (2)";
    }

    @Override
    public boolean isGlowing() {
        return true;
    }

    @Override
    public boolean isUnbreakable() {
        return true;
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
        return CreativeTabs.NONE;
    }

    @Override
    public String getUUID() {
        return "Machine:Wind_Turbine_HEAD"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	return false;
    }
    
	@Override
	public boolean onBlockIsInteractedWith(PlayerInteractEvent event) {
		return true;
	}

	@Override
	public boolean BlockBreakEvent(BlockBreakEvent event) {
		return true;
	}
	
	@Override
	public boolean BlockPlaceEvent(BlockPlaceEvent event) {
		return true;
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
