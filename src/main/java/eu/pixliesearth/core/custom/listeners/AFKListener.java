package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.localization.Lang;
import net.pl3x.purpur.event.PlayerAFKEvent;
import org.bukkit.event.EventHandler;

public class AFKListener extends CustomListener {

    @EventHandler
    public void handleAFK(PlayerAFKEvent event) {
        event.setShouldKick(false);
        if (event.isGoingAfk()) {
            event.setBroadcastMsg(Lang.EARTH + "§6" + event.getPlayer().getDisplayName() + " §7is now AFK.");
        } else {
            event.setBroadcastMsg(Lang.EARTH + "§6" + event.getPlayer().getDisplayName() + " §7is back.");
        }
    }

}
