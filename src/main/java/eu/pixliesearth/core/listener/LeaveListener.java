package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.SimpleLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class LeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        profile.setLastAt(new SimpleLocation(player.getLocation()).parseString());
        event.setQuitMessage("§8[§c§l-§8] §7" + player.getName());


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

        if (profile.getTimers().containsKey("§c§lCombat") && event.getReason().equals(PlayerQuitEvent.QuitReason.DISCONNECTED)) {
            if (player.getLastDamageCause() == null) return;
            Location chestLoc = new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
            chestLoc.getBlock().setType(Material.CHEST);
            Main.getInstance().getUtilLists().deathChests.put(chestLoc.getBlock(), player.getInventory().getContents());
            player.getInventory().clear();
            player.setHealth(0.0);
            if (player.getLastDamageCause().getEntity() != null)
                if (player.getLastDamageCause().getEntity() instanceof Player) {
                    player.setKiller((Player) player.getLastDamageCause().getEntity());
                }
        }

        if (Main.getInstance().getUtilLists().playersInWar.containsKey(player.getUniqueId()) && event.getReason().equals(PlayerQuitEvent.QuitReason.DISCONNECTED))
            Main.getInstance().getCurrentWar().handleLeave(profile);

        profile.getTimers().clear();

        if (player.getKiller() != null) {
            Profile killer = Main.getInstance().getProfile(player.getKiller().getUniqueId());
            killer.getTimers().remove("§c§lCombat");
            killer.save();
        }

        profile.backup();
        // Main.getInstance().getUtilLists().embedsToSend.add(new EmbedBuilder().setAuthor(player.getName(), "pixlies.net", "https://minotar/avatar/" + player.getName()).setColor(Color.RED).setTitle(" "));
    }

}
