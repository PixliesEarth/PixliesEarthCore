package eu.pixliesearth.core.modules;

import eu.pixliesearth.core.interfaces.Module;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.SimpleLocation;
import eu.pixliesearth.core.objects.Warp;
import eu.pixliesearth.core.utils.Methods;
import eu.pixliesearth.core.utils.Timer;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.nation.Nation;
import me.clip.placeholderapi.PlaceholderAPI;
import net.ranktw.DiscordWebHooks.DiscordMessage;
import net.ranktw.DiscordWebHooks.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatSystem implements Listener, Module {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        // CHAT INPUTS
        if (config.getBoolean("modules.chatsystem.enabled")) {
            Player player = event.getPlayer();
            Profile profile = instance.getProfile(player.getUniqueId());
            if (instance.getPlayerLists().warpAdder.contains(player.getUniqueId())) {
                event.setCancelled(true);
                Material mat = Material.GRASS_BLOCK;
                if (player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() != Material.AIR)
                    mat = player.getInventory().getItemInMainHand().getType();
                new Warp(event.getMessage(), player.getLocation(), mat.name()).serialize();
                player.sendMessage("§aEARTH §8| §7You §asuccessfully §7created the warp §b" + event.getMessage() + "§7!");
                return;
            }

            if (instance.getPlayerLists().nationDisbander.containsKey(player.getUniqueId())) {
                if (event.getMessage().equalsIgnoreCase("cancel")) {
                    player.sendMessage("§bNATION §8| §7Nation disband process §ccancelled§7.");
                    instance.getPlayerLists().nationDisbander.remove(player.getUniqueId());
                    event.setCancelled(true);
                    return;
                } else if (event.getMessage().equalsIgnoreCase("confirm")) {
                    Nation nation = Nation.getById(instance.getPlayerLists().nationDisbander.get(player.getUniqueId()));
                    nation.remove();
                    player.sendMessage("§bNATION §8| §7You disbanded §b" + nation.getName());
                    Bukkit.broadcastMessage("§bNATION §8| §7The nation of §b" + nation.getName() + " §7was disbanded by §6" + player.getName() + "§7.");
                    instance.getPlayerLists().nationDisbander.remove(player.getUniqueId());
                    event.setCancelled(true);
                }
            }

            // ACTUAL CHAT
            if (!event.isCancelled()) {
                if (muted() && !player.hasPermission("earth.chat.bypassmute")) {
                    event.setCancelled(true);
                    player.sendMessage(Lang.CHAT_IS_MUTED_ATM.get(player));
                    return;
                }

                if (config.getDouble("modules.chatsystem.cooldown") != 0.0) {
                    if (profile.getTimers().containsKey("chat") && !player.hasPermission("earth.chat.bypasscooldown")) {
                        player.sendMessage(Lang.CHAT_COOLDOWN.get(player).replace("%COOLDOWN%", Methods.getTimeAsString(profile.getTimers().get("Chat").getRemaining(), true)));
                        event.setCancelled(true);
                        return;
                    }
                }

                if (!player.hasPermission("earth.chat.bypassblacklist")) {
                    String[] split = event.getMessage().split(" ");
                    for (String s : split)
                        for (String s1 : config.getStringList("modules.chatsystem.blacklist"))
                            if (s.equalsIgnoreCase(s1)) {
                                player.sendMessage("§aEARTH §8| §7You are not allowed to say §b" + s1 + " §7here.");
                                event.setCancelled(true);
                                return;
                            }
                }

                // "@" MENTIONING SYSTEM
                if (event.getMessage().contains("@")) {
                    for (String string : event.getMessage().split(" ")) {
                        if (string.startsWith("@")) {
                            Bukkit.getOnlinePlayers().forEach(oplayer -> {
                                if (string.equalsIgnoreCase("@" + oplayer.getName())) {
                                    oplayer.playSound(oplayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                                    event.setMessage(event.getMessage().replace(string, "§b@§b" + oplayer.getName() + "§r"));
                                }
                            });
                        }
                    }
                }

                if (player.hasPermission("earth.chat.colours")) {
                    event.setMessage(event.getMessage().replace("&", "§").replace("%", "%%"));
                } else {
                    event.setMessage(event.getMessage().replace("&", "").replace("%", "%%"));
                }

                final String format = PlaceholderAPI.setPlaceholders(player, config.getString("modules.chatsystem.format").replace("%player_displayname%", player.getDisplayName()).replace("%chatcolor%", profile.getChatColor())).replace("%message%", event.getMessage());
                event.getRecipients().clear();
                //TODO Do relations later
                Bukkit.broadcastMessage(format);

                String webhook = config.getString("webhook");
                DiscordWebhook discord = new DiscordWebhook(webhook); // Create the webhook client

                DiscordMessage dm = new DiscordMessage.Builder()
                        .withUsername(event.getPlayer().getName())
                        .withContent(ChatColor.stripColor(event.getMessage().replace("@", "")))
                        .withAvatarURL("https://minotar.net/avatar/" + event.getPlayer().getName())
                        .build();

                discord.sendMessage(dm);

                if (config.getDouble("modules.chatsystem.cooldown") != 0.0 && !player.hasPermission("earth.chat.bypasscooldown")) {
                    Timer timer = new Timer(config.getLong("modules.chatsystem.cooldown") * 1000);
                    profile.getTimers().put("Chat", timer);
                    profile.save();
                    Bukkit.getScheduler().runTaskLater(instance, () -> {
                        profile.getTimers().remove("Chat");
                        profile.save();
                        player.sendActionBar("§aYou may now chat again.");
                    }, (long) config.getDouble("modules.chatsystem.cooldown") * 20);
                }
            }
        }
    }

    @Override
    public String name() {
        return "ChatSystem";
    }

    @Override
    public boolean enabled() {
        return config.getBoolean("modules.chatsystem.enabled");
    }

    public boolean muted() {
        return config.getBoolean("modules.chatsystem.muted");
    }

}
