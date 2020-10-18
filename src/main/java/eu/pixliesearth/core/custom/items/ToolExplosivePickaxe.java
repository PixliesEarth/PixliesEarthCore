package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.listener.ProtectionListener;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.Damageable;

import java.util.*;

public class ToolExplosivePickaxe extends CustomItem {
	
	public ToolExplosivePickaxe() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.DIAMOND_PICKAXE;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Explosive Pickaxe";
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
        return 3;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.TOOLS;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Explosive_Pickaxe"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
        return false;
    }
    
    @Override
    public boolean onBlockBrokeWithItem(BlockBreakEvent event) {
    	if (event.getBlock()==null) return true;
    	if (!ProtectionListener.canBreak(event)) return true;
    	if (!event.getPlayer().isSneaking()) {
	    	Block b = event.getBlock();
	    	ArrayList<Block> blocks = new ArrayList<Block>();
	        Block up = b.getRelative(BlockFace.UP, 1);
	        Block down = b.getRelative(BlockFace.DOWN, 1);
	        Block right = b.getRelative(BlockFace.EAST, 1);
	        Block left = b.getRelative(BlockFace.WEST, 1);
	        Block south = b.getRelative(BlockFace.SOUTH, 1);
	        Block north = b.getRelative(BlockFace.NORTH, 1);
	        Block downeast = down.getRelative(BlockFace.EAST, 1);
	        Block downwest = down.getRelative(BlockFace.WEST, 1);
	        Block downsouth = down.getRelative(BlockFace.SOUTH, 1);
	        Block downnorth = down.getRelative(BlockFace.NORTH, 1);
	        Block upeast = up.getRelative(BlockFace.EAST, 1);
	        Block upwest = up.getRelative(BlockFace.WEST, 1);
	        Block upsouth = up.getRelative(BlockFace.SOUTH, 1);
	        Block upnorth = up.getRelative(BlockFace.NORTH, 1);
	        Block downsoutheast = downsouth.getRelative(BlockFace.EAST, 1);
	        Block downsouthwest = downsouth.getRelative(BlockFace.WEST, 1);
	        Block downnorthwest = downnorth.getRelative(BlockFace.WEST, 1);
	        Block downnortheast = downnorth.getRelative(BlockFace.EAST, 1);
	        Block southeast = south.getRelative(BlockFace.EAST, 1);
	        Block southwest = south.getRelative(BlockFace.WEST, 1);
	        Block northeast = north.getRelative(BlockFace.EAST, 1);
	        Block northwest = north.getRelative(BlockFace.WEST, 1);
	        Block upsoutheast = upsouth.getRelative(BlockFace.EAST, 1);
	        Block upsouthwest = upsouth.getRelative(BlockFace.WEST, 1);
	        Block upnorthwest = upnorth.getRelative(BlockFace.WEST, 1);
	        Block upnortheast = upnorth.getRelative(BlockFace.EAST, 1);
	        blocks.add(b);
	        blocks.add(up);
	        blocks.add(down);
	        blocks.add(right);
	        blocks.add(left);
	        blocks.add(south);
	        blocks.add(north);
	        blocks.add(downeast);
	        blocks.add(downwest);
	        blocks.add(downsouth);
	        blocks.add(downnorth);
	        blocks.add(upeast);
	        blocks.add(upwest);
	        blocks.add(upnorth);
	        blocks.add(upsouth);
	        blocks.add(downsoutheast);
	        blocks.add(downsouthwest);
	        blocks.add(downnorthwest);
	        blocks.add(downnortheast);
	        blocks.add(southeast);
	        blocks.add(southwest);
	        blocks.add(northeast);
	        blocks.add(northwest);
	        blocks.add(upsoutheast);
	        blocks.add(upsouthwest);
	        blocks.add(upnorthwest);
	        blocks.add(upnortheast);
	        int counter = 0;
	    	for (Block block : blocks) {
	            if (!block.getType().equals(Material.BEDROCK) || !block.getType().equals(Material.COMMAND_BLOCK) || !block.getType().equals(Material.CHAIN_COMMAND_BLOCK) || !block.getType().equals(Material.REPEATING_COMMAND_BLOCK) || !block.getType().equals(Material.WATER) || !block.getType().equals(Material.LAVA) || !block.getType().equals(Material.BARRIER) || !block.getType().equals(Material.ARMOR_STAND) || !block.getType().equals(Material.AIR) || !block.getType().equals(Material.OBSIDIAN))  {
	            	CustomBlock c = CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(block.getLocation());
	            	if (c==null) {
	            		block.breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
	            	} else {
	            		block.getWorld().dropItem(b.getLocation(), CustomFeatureLoader.getLoader().getHandler().getItemStackFromUUID(c.getUUID()));
	            		CustomFeatureLoader.getLoader().getHandler().removeCustomBlockFromLocation(block.getLocation());
	            	}
	                counter++;
	            }
	        }
	    	event.getPlayer().getInventory().setItemInMainHand(new ItemBuilder(event.getPlayer().getInventory().getItemInMainHand()).setDamage(((Damageable)event.getPlayer().getInventory().getItemInMainHand().getItemMeta()).getDamage()-counter).build());
    	} else {
    		event.setCancelled(true);
    		CustomBlock c = CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(event.getBlock().getLocation());
    		if (c==null) {
    			event.getBlock().breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
    		} else {
	    		boolean b = c.BlockBreakEvent(event);
	    		if (!b) {
	    			event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), CustomFeatureLoader.getLoader().getHandler().getItemStackFromUUID(c.getUUID()));
	    			CustomFeatureLoader.getLoader().getHandler().removeCustomBlockFromLocation(event.getBlock().getLocation());
	    		} else 
	    			event.setCancelled(b);
    		}
    	}
    	return false;
    }
}
