package eu.pixliesearth.core.custom.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.listener.ProtectionListener;
import eu.pixliesearth.utils.NBTUtil;
import lombok.SneakyThrows;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom blocks</h3>
 * 
 * @apiNote TODO: notes
 */
public class CustomBlockListener extends CustomListener {
	
	@EventHandler
    @SneakyThrows
    public void PlayerInteractEvent(PlayerInteractEvent event) {
		if (event.getItem()==null || event.getItem().getType().equals(Material.AIR)) return;
		if (event.getClickedBlock() == null) return;
		CustomBlock id = CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(event.getClickedBlock().getLocation());
		if (id==null) return;
		event.setCancelled(id.onBlockIsInteractedWith(event));
	}
	
	@EventHandler
    @SneakyThrows
	public void BlockPlaceEvent(BlockPlaceEvent event) {
		if (!ProtectionListener.canPlace(event)) return;
		if (event.isCancelled()) return;
		String id = NBTUtil.getTagsFromItem(event.getItemInHand()).getString("UUID");
		if (id==null) return;
		CustomItem c = CustomFeatureLoader.getLoader().getHandler().getCustomItemFromUUID(id);
		if (c==null) return;
		boolean e = false;
		for (CustomBlock b : CustomFeatureLoader.getLoader().getHandler().getCustomBlocks()) 
			if (b.getUUID().equals(id)) {
				e = b.BlockPlaceEvent(event);
				break;
			}
		if (!e) {
			CustomFeatureLoader.getLoader().getHandler().setCustomBlockToLocation(event.getBlock().getLocation(), id);
		} else 
			event.setCancelled(true);
	}
	
	@EventHandler
    @SneakyThrows
	public void BlockBreakEvent(BlockBreakEvent event) {
		if (!ProtectionListener.canBreak(event)) return;
		if (event.isCancelled()) return;
		CustomBlock c = CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(event.getBlock().getLocation());
		if (c==null) return;
		boolean b = c.BlockBreakEvent(event);
		if (!b) {
			event.setDropItems(false);
			event.setExpToDrop(c.getExperience());
			event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), CustomFeatureLoader.getLoader().getHandler().getItemStackFromUUID(c.getUUID()));
			CustomFeatureLoader.getLoader().getHandler().removeCustomBlockFromLocation(event.getBlock().getLocation());
		} else 
			event.setCancelled(b);
	}
	
	@EventHandler
    @SneakyThrows
	public void BlockPistonExtendEvent(BlockPistonExtendEvent event) {
		if (event.getBlock()==null) return;
		if (event.isCancelled()) return;
		for (Block b : event.getBlocks()) 
			if (CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(b.getLocation())!=null)
				event.setCancelled(true);
	}
	
	@EventHandler
    @SneakyThrows
	public void BlockPistonRetracEvent(BlockPistonRetractEvent event) {
		if (event.getBlock()==null) return;
		if (event.isCancelled()) return;
		for (Block b : event.getBlocks()) 
			if (CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(b.getLocation())!=null)
				event.setCancelled(true);
	}
}
