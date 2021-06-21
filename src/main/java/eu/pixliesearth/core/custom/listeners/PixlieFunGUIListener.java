package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.utils.NBTUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PixlieFunGUIListener extends CustomListener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        if (NBTUtil.getTagsFromItem(event.getCurrentItem()) != null && NBTUtil.getTagsFromItem(event.getCurrentItem()).getString("guideItem") != null && !NBTUtil.getTagsFromItem(event.getCurrentItem()).getString("guideItem").equals("ONLY_STAFF")) return;
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getCursor() == null || event.getCursor().getType() == Material.AIR) return;
        if (NBTUtil.getTagsFromItem(event.getCursor()) != null && NBTUtil.getTagsFromItem(event.getCursor()).getString("guideItem") != null && !NBTUtil.getTagsFromItem(event.getCursor()).getString("guideItem").equals("ONLY_STAFF")) return;
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onInventoryDrop(PlayerDropItemEvent event) {
        if (NBTUtil.getTagsFromItem(event.getItemDrop().getItemStack()) != null && NBTUtil.getTagsFromItem(event.getItemDrop().getItemStack()).getString("guideItem") != null && !NBTUtil.getTagsFromItem(event.getItemDrop().getItemStack()).getString("guideItem").equals("ONLY_STAFF")) return;
        event.setCancelled(true);
    }

}
