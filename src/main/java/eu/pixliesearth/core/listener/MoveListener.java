package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getFrom().getX() != event.getTo().getX() && event.getFrom().getY() != event.getTo().getY() && event.getFrom().getZ() != event.getTo().getZ()) {
            if (Main.getInstance().getPlayerLists().teleportCooldown.containsKey(event.getPlayer().getUniqueId())) {
                Main.getInstance().getPlayerLists().teleportCooldown.remove(event.getPlayer().getUniqueId());
                event.getPlayer().sendMessage("§cTeleportation cancelled due to your inability to stand still.");
            }
            if (Main.getInstance().getPlayerLists().afk.contains(event.getPlayer().getUniqueId())) {
                Main.getInstance().getPlayerLists().afk.remove(event.getPlayer().getUniqueId());
                Bukkit.broadcastMessage("§8Player §7" + event.getPlayer().getDisplayName() + " §8is §aback§8.");
            }
        }
    }

}
