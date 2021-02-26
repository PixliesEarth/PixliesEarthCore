package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.interfaces.Constants;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.NBTTagType;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class DeathListener implements Listener, Constants {

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
            double amount = Methods.round((profile.getBalance() / 100) * 5, 2);
            ItemStack itemStack = new ItemBuilder(Material.BRICK).setDisplayName("money form death").setCustomModelData(6).addNBTTag("money", Double.toString(amount), NBTTagType.STRING).addNBTTag("owner", player.getName(), NBTTagType.STRING).build();
            Item item = player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            item.setCustomName("§2§l$§a" + amount);
            item.setCustomNameVisible(true);
            profile.withdrawMoney(amount, "Died at " + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ());
        }
    }

}
