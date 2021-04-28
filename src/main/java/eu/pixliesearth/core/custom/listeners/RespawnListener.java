package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.utils.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener extends CustomListener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Profile profile = instance.getProfile(player.getUniqueId());
        if (profile.isInWar()) {
            event.setRespawnLocation(Methods.locationFromSaveableString(instance.getGulag().getSpectatorLocation()));
            return;
        }
        if (player.getBedSpawnLocation() != null) {
            event.setRespawnLocation(player.getBedSpawnLocation());
        } else {
            event.setRespawnLocation(instance.getFastConf().getSpawnLocation() != null ? instance.getFastConf().getSpawnLocation() : player.getLocation());
        }
    }

}
