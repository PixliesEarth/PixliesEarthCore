package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.discord.MiniMick;
import me.clip.placeholderapi.PlaceholderAPI;
import net.minecraft.server.v1_15_R1.AdvancementFrameType;
import net.minecraft.server.v1_15_R1.Advancements;
import org.bukkit.ChatColor;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AchievmentListener implements Listener {
    @EventHandler
    public void onAchievment(PlayerAdvancementDoneEvent e){
        Player p = e.getPlayer();
        Advancement a = e.getAdvancement();
        String rawAdvancementName = a.getKey().getKey();
        String advancementName = Arrays.stream(rawAdvancementName.substring(rawAdvancementName.lastIndexOf("/") + 1).toLowerCase().split("_"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining(" "));
        MiniMick.getApi().getServerTextChannelById(Main.getInstance().getConfig().getString("chatchannel")).get().sendMessage(ChatColor.stripColor( "**" + PlaceholderAPI.setPlaceholders(p, "%vault_prefix%" + p.getDisplayName()) + " has made the advancement " + advancementName + "!**"));
    }
}
