package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class PrivateChestListener extends CustomListener {

    @EventHandler
    public void onChestOpen(PlayerInteractEvent event) {
        if (!event.hasBlock())
            return;
        if (event.getClickedBlock().getType() != Material.CHEST)
            return;
        if (!(event.getClickedBlock().getState() instanceof TileState))
            return;

        TileState state = (TileState) event.getClickedBlock().getState();
        PersistentDataContainer container = state.getPersistentDataContainer();

        Plugin plugin;
        NamespacedKey key = new NamespacedKey(instance, "private-chest");

        if (!container.has(key, PersistentDataType.STRING))
            return;
        if (event.getPlayer().getUniqueId().toString().equalsIgnoreCase(container.get(key, PersistentDataType.STRING)) || instance.isStaff(event.getPlayer()))
            return;
        else {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Lang.EARTH + "§cThis chest is locked.");
        }

    }

}
