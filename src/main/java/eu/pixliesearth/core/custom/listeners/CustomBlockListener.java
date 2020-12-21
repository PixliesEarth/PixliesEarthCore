package eu.pixliesearth.core.custom.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.listener.ProtectionManager;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
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
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if (!ProtectionManager.canInteract(event)) {
			event.setCancelled(true);
			event.getPlayer().sendActionBar(Lang.CANT_INTERACT_TERRITORY.get(event.getPlayer()));
			return;
		}
		if (event.getClickedBlock() == null) return;
		CustomBlock id = CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(event.getClickedBlock().getLocation());
		if (id==null) return;
		event.setCancelled(id.onBlockIsInteractedWith(event));
	}
	
	@EventHandler
    @SneakyThrows
	public void BlockPlaceEvent(BlockPlaceEvent event) {
		if (event.getBlock()==null || event.getBlock().getType().equals(Material.AIR)) return;
		if (!ProtectionManager.canPlace(event)) return;
		if (event.isCancelled()) return;
		String id = NBTUtil.getTagsFromItem(event.getPlayer().getInventory().getItemInMainHand()).getString("UUID");
		if (id==null) return;
		CustomItem c = CustomFeatureLoader.getLoader().getHandler().getCustomItemFromUUID(id);
		if (c==null) return;
		if (!(c instanceof CustomBlock)) return; // To stop the custom item errors
		boolean e = false;
		for (CustomBlock b : CustomFeatureLoader.getLoader().getHandler().getCustomBlocks()) {
			if (b.getUUID().equals(id)) {
				e = b.BlockPlaceEvent(event);
				break;
			}
		}
		if (!e) {
			CustomFeatureLoader.getLoader().getHandler().setCustomBlockToLocation(event.getBlock().getLocation(), id);
			String s = NBTUtil.getTagsFromItem(event.getPlayer().getInventory().getItemInMainHand()).getString("ENERGY");
			if (s!=null && !s.equalsIgnoreCase("")) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
					@Override
					public void run() {
						CustomFeatureLoader.getLoader().getHandler().addPowerToLocation(event.getBlock().getLocation(), Double.parseDouble(s));
					}
				}, 3l);
			}
		} else 
			event.setCancelled(true);
	}
	
	@EventHandler
    @SneakyThrows
	public void BlockBreakEvent(BlockBreakEvent event) {
		if (!ProtectionManager.canBreak(event)) return;
		if (event.isCancelled()) return;
		CustomBlock c = CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(event.getBlock().getLocation());
		if (c==null) return;
		boolean b = c.BlockBreakEvent(event);
		if (!b) {
			event.setDropItems(false);
			event.setExpToDrop(c.getExperience());
			ItemStack itemStack;
			Double energy = CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(event.getBlock().getLocation());
			if (energy==null||energy<=0) {
				itemStack = CustomFeatureLoader.getLoader().getHandler().getItemStackFromUUID(c.getUUID());
			} else {
				itemStack = new ItemBuilder(c.getUUID()).addNBTTag("ENERGY", Double.toString(energy), NBTTagType.STRING).build();
			}
			event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), itemStack);
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
	public void BlockPistonRetractEvent(BlockPistonRetractEvent event) {
		if (event.getBlock()==null) return;
		if (event.isCancelled()) return;
		for (Block b : event.getBlocks()) 
			if (CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(b.getLocation())!=null)
				event.setCancelled(true);
	}
	
	@EventHandler
    public void EntityExplodeEvent(EntityExplodeEvent event) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		for (Block b : event.blockList().toArray(new Block[event.blockList().size()])){
			if(h.isCustomBlockAtLocation(b.getLocation())){
				boolean v = h.getCustomBlockFromLocation(b.getLocation()).EntityExplodeEvent(event);
				if (!v) {
					event.blockList().remove(b);
					h.removeCustomBlockFromLocation(b.getLocation());
				} else {
					event.blockList().remove(b);
				}
			}
		}
	}
	
	@EventHandler
    public void BlockExplodeEvent(BlockExplodeEvent event) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		for (Block b : event.blockList().toArray(new Block[event.blockList().size()])){
			if(h.isCustomBlockAtLocation(b.getLocation())) {
				boolean v = h.getCustomBlockFromLocation(b.getLocation()).BlockExplodeEvent(event);
				if (!v) {
					event.blockList().remove(b);
					h.removeCustomBlockFromLocation(b.getLocation());
				} else {
					event.blockList().remove(b);
				}
			}
		}
	}
}
