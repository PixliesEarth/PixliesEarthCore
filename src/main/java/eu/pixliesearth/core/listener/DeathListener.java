package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

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

        MiniMick.getApi().getServerTextChannelById(Main.getInstance().getConfig().getString("chatchannel")).get().sendMessage("**" + ChatColor.stripColor(endDeathMessage) + "**");
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        profile.getTimers().remove("§c§lCombat");

        EntityDamageEvent damageEvent = player.getLastDamageCause();
        if (damageEvent instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent) damageEvent).getDamager() instanceof Player) {
            Profile killer = Main.getInstance().getProfile(damageEvent.getEntity().getUniqueId());
            killer.getTimers().remove("§c§lCombat");
        }
        instance.getUtilLists().claimAuto.remove(player.getUniqueId());
        instance.getUtilLists().unclaimAuto.remove(player.getUniqueId());
        e.setDeathMessage("§c☠ §7" + e.getDeathMessage().replace("§r", "§7").replace("§f", "§r"));
        profile.save();
        instance.getUtilLists().lastLocation.put(player.getUniqueId(), player.getLocation());
        if (profile.getBalance() > 5.0) {
            double amount = (profile.getBalance() / 100) * 5;
            profile.withdrawMoney(amount, "Death at " + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ());
            ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/d7d7f8fd87fe7e34f9113dd385aab7b24ef221c19d455175b2578af7ff46eecf")).setDisplayName("money " + amount + " " + player.getName()).build();
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        }
    }

}
