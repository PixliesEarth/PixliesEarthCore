package eu.pixliesearth.core.modules;

import eu.pixliesearth.core.interfaces.Module;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.SimpleLocation;
import eu.pixliesearth.core.objects.Warp;
import eu.pixliesearth.core.utils.CooldownMap;
import eu.pixliesearth.nations.entities.nation.Nation;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
                if (Warp.exists(event.getMessage())) {
                    player.sendMessage("§aEARTH §8| §7A warp with that name already exists.");
                    return;
                }
                new Warp(event.getMessage(), new SimpleLocation(player.getLocation()), Material.GRASS_BLOCK).serialize();
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
                    player.sendMessage("§7The chat is §cmuted §7at the moment.");
                    return;
                }

                if (config.getDouble("modules.chatsystem.cooldown") != 0.0) {
                    if (instance.getPlayerLists().chatCooldown.containsKey(player) && !player.hasPermission("earth.chat.bypasscooldown")) {
                        player.sendMessage("§7You have to wait §b" + instance.getPlayerLists().chatCooldown.get(player).getTimeLeft() + " second(s) §7to chat again.");
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

                if (player.hasPermission("earth.chat.colours")) {
                    event.setMessage(event.getMessage().replace("&", "§").replace("%", "%%"));
                } else {
                    event.setMessage(event.getMessage().replace("&", "").replace("%", "%%"));
                }

                final String format = PlaceholderAPI.setPlaceholders(player, config.getString("modules.chatsystem.format").replace("%player_displayname%", player.getDisplayName()).replace("%chatcolor%", profile.getChatColor())).replace("%message%", event.getMessage());
                event.getRecipients().clear();
                Bukkit.broadcastMessage(format);

                if (config.getDouble("modules.chatsystem.cooldown") != 0.0) {
                    int taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(instance, () -> {
                        if (instance.getPlayerLists().chatCooldown.get(player.getUniqueId()).getTimeLeft() - 0.1 == 0.0) {
                            Bukkit.getScheduler().cancelTask(instance.getPlayerLists().chatCooldown.get(player.getUniqueId()).getTaskId());
                            instance.getPlayerLists().chatCooldown.remove(player.getUniqueId());
                            player.sendActionBar("§aYou can chat again.");
                            return;
                        }
                        instance.getPlayerLists().chatCooldown.get(player.getUniqueId()).setTimeLeft(instance.getPlayerLists().chatCooldown.get(player.getUniqueId()).getTimeLeft() - 0.1);
                    }, 0L, 2);
                    instance.getPlayerLists().chatCooldown.put(player.getUniqueId(), new CooldownMap(config.getDouble("modules.chatsystem.cooldown"), taskId));
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
