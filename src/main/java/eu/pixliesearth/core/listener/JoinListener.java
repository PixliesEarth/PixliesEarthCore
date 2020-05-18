package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.SimpleLocation;
import eu.pixliesearth.core.utils.AfkMap;
import eu.pixliesearth.core.utils.Methods;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        if (!profile.getKnownIps().contains(Methods.getIp(player))) {
            profile.getKnownIps().add(Methods.getIp(player));
        }
        if (!profile.getKnownUsernames().contains(player.getName())) {
            profile.getKnownUsernames().add(player.getName());
        }
        profile.backup();
        Main.getInstance().getPlayerLists().locationMap.put(player.getUniqueId(), new AfkMap(new SimpleLocation(player.getLocation()), 0));
        event.setJoinMessage(PlaceholderAPI.setPlaceholders(player, "§8[§a§l+§8] %vault_prefix%" + player.getName()));

        player.sendMessage("§aEARTH §8| §7Your profile has been §bloaded§7.");

    }

}
