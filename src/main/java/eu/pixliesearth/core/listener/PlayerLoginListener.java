package eu.pixliesearth.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
            if (event.getPlayer().hasPermission("earth.bypass.fullkick")) {
                event.allow();
            }
        } else if (event.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST) {
            if (event.getPlayer().hasPermission("earth.bypass.whitelist")) {
                event.allow();
            }
        }
    }

}
