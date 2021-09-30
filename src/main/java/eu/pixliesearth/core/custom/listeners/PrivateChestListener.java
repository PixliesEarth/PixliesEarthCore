package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Chest;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class PrivateChestListener extends CustomListener {

    @EventHandler
    public void onChestOpen(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;
        if (!event.hasBlock())
            return;
        if (!(event.getClickedBlock().getState() instanceof Chest))
            return;

        TileState state = (TileState) event.getClickedBlock().getState();
        PersistentDataContainer container = state.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(instance, "private-chests");

        if (!container.has(key, PersistentDataType.STRING))
            return;

        if (!event.getPlayer().getUniqueId().toString().equalsIgnoreCase(container.get(key, PersistentDataType.STRING)) && !instance.isStaff(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Lang.EARTH + "§cThis chest is locked.");
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChestBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.CHEST) return;

        TileState state = (TileState) event.getBlock().getState();
        PersistentDataContainer container = state.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(instance, "private-chests");

        if (!container.has(key, PersistentDataType.STRING))
            return;

        if (!event.getPlayer().getUniqueId().toString().equalsIgnoreCase(container.get(key, PersistentDataType.STRING)) && !instance.isStaff(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Lang.EARTH + "§cThis chest is locked.");
        }
    }

}
