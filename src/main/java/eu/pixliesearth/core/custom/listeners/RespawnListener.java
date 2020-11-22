package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener extends CustomListener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (player.getBedSpawnLocation() != null) {
            event.setRespawnLocation(player.getBedSpawnLocation());
        } else {
            event.setRespawnLocation(instance.getFastConf().getSpawnLocation());
        }
    }

}
