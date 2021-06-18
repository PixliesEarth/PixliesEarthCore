package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.utils.NBTUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PixlieFunGUIListener extends CustomListener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (NBTUtil.getTagsFromItem(event.getCurrentItem()) != null && NBTUtil.getTagsFromItem(event.getCurrentItem()).getString("guideItem") != null) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getCursor() == null) return;
        if (NBTUtil.getTagsFromItem(event.getCursor()) != null && NBTUtil.getTagsFromItem(event.getCursor()).getString("guideItem") != null) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onInventoryDrop(PlayerDropItemEvent event) {
        if (NBTUtil.getTagsFromItem(event.getItemDrop().getItemStack()) != null && NBTUtil.getTagsFromItem(event.getItemDrop().getItemStack()).getString("guideItem") != null) event.setCancelled(true);
    }

}
