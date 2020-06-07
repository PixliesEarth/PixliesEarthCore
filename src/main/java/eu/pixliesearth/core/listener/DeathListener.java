package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.MiniMick;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringUtils;
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

public class DeathListener implements Listener {

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
        profile.save();
        if (player.getKiller() != null) {
            Profile killer = Main.getInstance().getProfile(player.getKiller().getUniqueId());
            killer.getTimers().remove("§c§lCombat");
            killer.save();
        }
    }

}
