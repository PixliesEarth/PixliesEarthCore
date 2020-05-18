package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (Main.getInstance().getPlayerLists().teleportCooldown.containsKey(event.getPlayer().getUniqueId())) {
            Main.getInstance().getPlayerLists().teleportCooldown.remove(event.getPlayer().getUniqueId());
            event.getPlayer().sendMessage("§cTeleportation cancelled due to your inability to stand still.");
        }
    }

}
