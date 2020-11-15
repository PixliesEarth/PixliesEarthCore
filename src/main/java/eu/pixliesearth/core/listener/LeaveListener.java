package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.SimpleLocation;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class LeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        //TODO Do something lmao
/*        if(Main.getInstance().getUtilLists().awaitingGulag1.contains(player.getUniqueId()) || Main.getInstance().getUtilLists().awaitingGulag2.contains(player.getUniqueId())){
            Date date = new Date(System.currentTimeMillis()+60*60*1000*24);
            if(!player.hasPermission("gulag.bypass.ban")) {
                player.banPlayer("Trying to avoid gulag", date, "The gulag");
            }
            if(Main.getInstance().getUtilLists().awaitingGulag1.contains(player.getUniqueId())){
                Main.getInstance().getUtilLists().awaitingGulag1.remove(player.getUniqueId());
            }else{
                Main.getInstance().getUtilLists().awaitingGulag2.remove(player.getUniqueId());
            }
        }
        if(Main.getInstance().getUtilLists().fightingGulag.containsKey(player.getUniqueId()) || Main.getInstance().getUtilLists().fightingGulag.containsValue(player.getUniqueId())){
            if(Main.getInstance().getUtilLists().fightingGulag.containsKey(player.getUniqueId())){
                GulagStartListener.fightOver(player, Bukkit.getPlayer(Main.getInstance().getUtilLists().fightingGulag.get(player.getUniqueId())));
            }else{
                GulagStartListener.fightOver(player, Bukkit.getPlayer(getKeyByValue(Main.getInstance().getUtilLists().fightingGulag, player.getUniqueId())));
            }
        }*/
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        profile.setLastAt(new SimpleLocation(player.getLocation()).parseString());
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
                if (player.getLastDamageCause().getEntity() instanceof Player) {
                    player.setKiller((Player) player.getLastDamageCause().getEntity());
                    int randomNum = ThreadLocalRandom.current().nextInt(2, 5 + 1);
                    profile.setElo(profile.getElo() - randomNum);
                    Profile opponent = Main.getInstance().getProfile(player.getLastDamageCause().getEntity().getUniqueId());
                    opponent.setElo(opponent.getElo() + randomNum);
                    opponent.save();
                }
        }

        profile.getTimers().clear();

        if (player.getKiller() != null) {
            Profile killer = Main.getInstance().getProfile(player.getKiller().getUniqueId());
            killer.getTimers().remove("§c§lCombat");
            killer.save();
        }

        profile.backup();
/*
        //Discord Leaves
        MiniMick.getApi().getServerTextChannelById(Main.getInstance().getConfig().getString("chatchannel")).get().sendMessage(ChatColor.stripColor("<:arrowleft:716793452494454825> **" + PlaceholderAPI.setPlaceholders(player, "%vault_prefix%" + player.getDisplayName()) + "** left the server!"));*/
    }
    public static UUID getKeyByValue(Map<UUID, UUID> map, UUID value) {
        for (Map.Entry<UUID, UUID> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

}
