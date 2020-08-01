package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.MiniMick;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class DeathListener implements Listener {

    Main instance = Main.getInstance();

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if (e.getEntityType() != EntityType.PLAYER) return;
        Player player = e.getEntity();
        String deathMessage = e.getDeathMessage();

        String finalDeathMessage = StringUtils.isNotBlank(deathMessage) ? deathMessage : "";
        String endDeathMessage = finalDeathMessage.replace(player.getName(), ChatColor.stripColor(PlaceholderAPI.setPlaceholders(player, "%vault_prefix%" + player.getDisplayName())));

        MiniMick.getApi().getServerTextChannelById(Main.getInstance().getConfig().getString("chatchannel")).get().sendMessage("**" + endDeathMessage + "**");

        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        profile.getTimers().remove("§c§lCombat");
        if (player.getKiller() != null) {
            Profile killer = Main.getInstance().getProfile(player.getKiller().getUniqueId());
            killer.getTimers().remove("§c§lCombat");
            int randomNum = ThreadLocalRandom.current().nextInt(2, 5 + 1);
            profile.setElo(profile.getElo() - randomNum);
            killer.setElo(killer.getElo() + randomNum);
            killer.save();
        }
        instance.getUtilLists().claimAuto.remove(player.getUniqueId());
        instance.getUtilLists().unclaimAuto.remove(player.getUniqueId());
        TextComponent comp = new TextComponent(PlaceholderAPI.setPlaceholders(player, "§c☠§7%vault_prefix%" + player.getDisplayName() + "§8[§b" + profile.getElo() + "§8]"));
        comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7§o" + e.getDeathMessage() + "\n\n§cElo: §b" + profile.getElo())));
        Bukkit.broadcast(comp);
        e.setDeathMessage("");
        profile.save();
    }

}
