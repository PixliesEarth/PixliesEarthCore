package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.UUID;

public class JoinListener implements Listener {

    private static final Main instance = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        final long started = System.currentTimeMillis();

        Profile profile = instance.getProfile(player.getUniqueId());

        if (!profile.getKnownUsernames().contains(player.getName()))
            profile.getKnownUsernames().add(player.getName());

        event.setJoinMessage("§8[§a§l+§8] §7" + player.getName());

        if (!player.hasPlayedBefore()) {
            player.teleport(instance.getFastConf().getSpawnLocation());
            for (Player op : Bukkit.getOnlinePlayers())
                op.sendMessage(Lang.PLAYER_JOINED_FIRST_TIME.get(op).replace("%PLAYER%", player.getDisplayName()).replace("%COUNT%", Main.getPlayerCollection().countDocuments() + ""));
            profile.openLangGui();
            profile.addTimer("Free TP", new Timer(Timer.DAY));
        }

        if (!instance.getUtilLists().vanishList.isEmpty()) {
            for (UUID pUUID : Main.getInstance().getUtilLists().vanishList) {
                Player p = Bukkit.getOfflinePlayer(pUUID).getPlayer();
                if (p == null) continue;
                if (!player.hasPermission("earth.seevanished"))
                    player.hidePlayer(Main.getInstance(), p);
            }
        }

        if (profile.getEnergy() > 10D)
            profile.setEnergy(10D);

        profile.getTimers().clear();
        if (profile.getNickname().length() > 0 && !profile.getNickname().equalsIgnoreCase("NONE"))
            player.setDisplayName(profile.getNickname());

        profile.save();
        long needed = System.currentTimeMillis() - started;
        player.sendMessage(Lang.PROFILE_LOADED.get(player).replace("%TIME%", needed + "ms"));
        // instance.getUtilLists().embedsToSend.add(new EmbedBuilder().setAuthor(player.getName(), "pixlies.net", "https://minotar/avatar/" + player.getName()).setColor(Color.GREEN).setTitle(" "));
    }

}
