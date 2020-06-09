package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.SimpleLocation;
import eu.pixliesearth.discord.MiniMick;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class LeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        profile.setLastAt(new SimpleLocation(player.getLocation()).parseString());
        Main.getInstance().getUtilLists().locationMap.remove(player.getUniqueId());
        if (Main.getInstance().getUtilLists().afk.contains(player.getUniqueId()))
            Main.getInstance().getUtilLists().afk.remove(player.getUniqueId());
        event.setQuitMessage(PlaceholderAPI.setPlaceholders(player, "§8[§c§l-§8] %vault_prefix%" + player.getName()));


        //VANISH
        if (!(Main.getInstance().getUtilLists().vanishList.isEmpty())) {
            for (UUID pUUID : Main.getInstance().getUtilLists().vanishList) {
                Player p = Bukkit.getPlayer(pUUID);
                //UNVANISH LEAVING VANISHED PLAYER
                if (event.getPlayer() == p) {
                    Main.getInstance().getUtilLists().vanishList.remove(p.getUniqueId());
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.showPlayer(Main.getInstance(), p);
                    }
                }
                //UNVANISHES VANISHED PLAYERS FOR LEAVING PLAYERS
                player.showPlayer(Main.getInstance(), p);
            }
        }

        if (profile.getTimers().containsKey("§c§lCombat")) {
            Location chestLoc = new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
            chestLoc.getBlock().setType(Material.CHEST);
            Main.getInstance().getUtilLists().deathChests.put(chestLoc.getBlock(), player.getInventory().getContents());
            player.getInventory().clear();
            player.setHealth(0.0);
            if (player.getLastDamageCause().getEntity() != null)
                if (player.getLastDamageCause().getEntity() instanceof Player)
                    player.setKiller((Player) player.getLastDamageCause().getEntity());
        }

        profile.getTimers().clear();

        if (player.getKiller() != null) {
            Profile killer = Main.getInstance().getProfile(player.getKiller().getUniqueId());
            killer.getTimers().remove("§c§lCombat");
            killer.save();
        }

        profile.backup();

        //Discord Leaves
        MiniMick.getApi().getServerTextChannelById(Main.getInstance().getConfig().getString("chatchannel")).get().sendMessage(ChatColor.stripColor("<:arrowleft:716793452494454825> **" + PlaceholderAPI.setPlaceholders(player, "%vault_prefix%" + player.getDisplayName()) + "** left the server!"));
    }

}
