package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class WarCommandListener extends CustomListener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInWar()) return;
        String[] split = event.getMessage().split(" ");
        if (split[0].equalsIgnoreCase("/n")
                || split[0].equalsIgnoreCase("/nations")
                || split[0].equalsIgnoreCase("/nation")) {
            player.sendMessage(Lang.WAR + "§7You cant use nation commands in war.");
        }
    }

}
