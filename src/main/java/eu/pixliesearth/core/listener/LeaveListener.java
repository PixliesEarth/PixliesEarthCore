package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.SimpleLocation;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        profile.setLastAt(new SimpleLocation(player.getLocation()).parseString());
        profile.backup();
        event.setQuitMessage(PlaceholderAPI.setPlaceholders(player, "§8[§c§l-§8] %vault_prefix%" + player.getName()));

    }

}
