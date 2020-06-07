package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.SimpleLocation;
import eu.pixliesearth.discord.MiniMick;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class LeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        profile.setLastAt(new SimpleLocation(player.getLocation()).parseString());
        profile.backup();
        Main.getInstance().getPlayerLists().locationMap.remove(player.getUniqueId());
        if (Main.getInstance().getPlayerLists().afk.contains(player.getUniqueId()))
            Main.getInstance().getPlayerLists().afk.remove(player.getUniqueId());
        event.setQuitMessage(PlaceholderAPI.setPlaceholders(player, "§8[§c§l-§8] %vault_prefix%" + player.getName()));


        //VANISH
        if (!(Main.getInstance().getPlayerLists().vanishList.isEmpty())){
            for (UUID pUUID : Main.getInstance().getPlayerLists().vanishList) {
                Player p = Bukkit.getPlayer(pUUID);
                //UNVANISH LEAVING VANISHED PLAYER
                if (event.getPlayer() == p) {
                    Main.getInstance().getPlayerLists().vanishList.remove(p.getUniqueId());
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.showPlayer(Main.getInstance(), p);
                    }
                }
                //UNVANISHES VANISHED PLAYERS FOR LEAVING PLAYERS
                player.showPlayer(Main.getInstance(), p);
            }
    }
        //Discord Leaves
        MiniMick.getApi().getServerTextChannelById(Main.getInstance().getConfig().getString("chatchannel")).get().sendMessage(ChatColor.stripColor("<:arrowleft:716793452494454825> **" + PlaceholderAPI.setPlaceholders(player, "%vault_prefix%" + player.getDisplayName()) + "** left the server!"));
    }

}
