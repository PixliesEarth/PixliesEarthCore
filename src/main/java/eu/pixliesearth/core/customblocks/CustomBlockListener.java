package eu.pixliesearth.core.customblocks;

import java.util.UUID;

import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.listener.ProtectionListener;
import lombok.SneakyThrows;

public class CustomBlockListener implements Listener {
	@EventHandler
    @SneakyThrows
    public void BlockPlaceEvent(BlockPlaceEvent event) {
        if (!ProtectionListener.canPlace(event)) return;
        ItemStack item = event.getItemInHand();
        if (CustomBlocks.getFromItemStack(item)!=null) {
        	CustomBlock cb = CustomBlocks.getFromItemStack(item).getConstructor(UUID.class, Location.class).newInstance(UUID.randomUUID(), event.getBlockPlaced().getLocation());
        	CustomBlocks.placedCustomBlock(cb);

        } else 
        	return;
    }
	@EventHandler
    @SneakyThrows
    public void BlockBreakEvent(BlockBreakEvent event) {
        if (!ProtectionListener.canBreak(event)) return;
        if (CustomBlocks.getBlockFromLocation(event.getBlock().getLocation())==null) return; // is not a custom block
        event.setDropItems(false);
        event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), CustomBlocks.getBlockFromLocation(event.getBlock().getLocation()).getItem());
        CustomBlocks.brokeCustomBlock(event.getBlock().getLocation());
    }
}
