package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class VendorListener implements Listener {

    private static final Main instance = Main.getInstance();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onNPCRightClick(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getName().equalsIgnoreCase(instance.getVendor().getNpcName())) instance.getVendor().open(event.getPlayer());
    }

}
