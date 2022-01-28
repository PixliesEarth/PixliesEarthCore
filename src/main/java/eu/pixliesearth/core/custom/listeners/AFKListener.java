package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.localization.Lang;
import net.pl3x.purpur.event.PlayerAFKEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class AFKListener extends CustomListener {

    private static final Main instance = Main.getInstance();

    @EventHandler
    public void handleAFK(PlayerAFKEvent event) {
        event.setShouldKick(false);
        Player player = event.getPlayer();
        if (instance.getUtilLists().vanishList.contains(player.getUniqueId())) return;
        if (event.isGoingAfk()) {
            event.setBroadcastMsg(Lang.EARTH + "§6" + player.getDisplayName() + " §7is now AFK.");
        } else {
            event.setBroadcastMsg(Lang.EARTH + "§6" + player.getDisplayName() + " §7is back.");
        }
    }

}
