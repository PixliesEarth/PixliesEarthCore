package eu.pixliesearth.core.custom.items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.utils.CustomItemUtil;

public class ToolTreeTap extends CustomItem {

    public ToolTreeTap() {

    }

    @Override
    public Material getMaterial() {
        return Material.WOODEN_HOE;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§7§lTree Tap";
    }

    @Override
    public boolean isGlowing() {
        return false;
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
        return new HashMap<Enchantment, Integer>(){private static final long serialVersionUID = -3047091438050015655L;{
            put(Enchantment.DIG_SPEED, 2);
        }};
    }

    @Override
    public Set<ItemFlag> getItemFlags(){
        return new HashSet<ItemFlag>();
    }

    @Override
    public Integer getCustomModelData() {
        return 64;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.TOOLS;
    }

    @Override
    public String getUUID() {
        return "Pixlies:TreeTap"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	if (event.getClickedBlock()==null) return false;
    	if (event.getClickedBlock().getType().equals(Material.DIRT) || event.getClickedBlock().getType().equals(Material.GRASS_BLOCK) || event.getClickedBlock().getType().equals(Material.GRASS_PATH)) return true;
    	if (event.getClickedBlock().getType().equals(Material.OAK_LOG)) {
    		event.getClickedBlock().setType(Material.STRIPPED_OAK_LOG);
    	} else if (event.getClickedBlock().getType().equals(Material.JUNGLE_LOG)) {
    		event.getClickedBlock().setType(Material.STRIPPED_JUNGLE_LOG);
    	} else if (event.getClickedBlock().getType().equals(Material.SPRUCE_LOG)) {
    		event.getClickedBlock().setType(Material.STRIPPED_SPRUCE_LOG);
    	} else if (event.getClickedBlock().getType().equals(Material.ACACIA_LOG)) {
    		event.getClickedBlock().setType(Material.STRIPPED_ACACIA_LOG);
    	} else if(event.getClickedBlock().getType().equals(Material.BIRCH_LOG)) {
    		event.getClickedBlock().setType(Material.STRIPPED_BIRCH_LOG);
    	} else if (event.getClickedBlock().getType().equals(Material.DARK_OAK_LOG)) {
    		event.getClickedBlock().setType(Material.STRIPPED_DARK_OAK_LOG);
    	} else 
    		return false;
    	if (Math.random() > 0.9) {
			event.getClickedBlock().getLocation().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), CustomItemUtil.getItemStackFromUUID("Pixlies:Rubber"));
		}
        return false;
    }

}
