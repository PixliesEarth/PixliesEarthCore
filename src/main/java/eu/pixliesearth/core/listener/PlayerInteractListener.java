package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType().equals(Material.CHEST))
            if (Main.getInstance().getUtilLists().deathChests.contains((Chest) event.getClickedBlock()))
                event.getClickedBlock().setType(Material.AIR);
    }

}
