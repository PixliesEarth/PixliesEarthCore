package eu.pixliesearth.core.modules;

import eu.pixliesearth.core.interfaces.Module;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.Warp;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
            if (instance.getUtilLists().warpAdder.contains(player.getUniqueId())) {
                event.setCancelled(true);
                Material mat = Material.GRASS_BLOCK;
                if (player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() != Material.AIR)
                    mat = player.getInventory().getItemInMainHand().getType();
                new Warp(event.getMessage(), player.getLocation(), mat.name()).serialize();
                instance.getUtilLists().warpAdder.remove(player.getUniqueId());
                player.sendMessage("§aEARTH §8| §7You §asuccessfully §7created the warp §b" + event.getMessage() + "§7!");
                return;
            }

            if (instance.getUtilLists().nationDisbander.containsKey(player.getUniqueId())) {
                if (event.getMessage().equalsIgnoreCase("cancel")) {
                    event.setCancelled(true);
                    player.sendMessage("§bNATION §8| §7Nation disband process §ccancelled§7.");
                    instance.getUtilLists().nationDisbander.remove(player.getUniqueId());
                    return;
                } else if (event.getMessage().equalsIgnoreCase("confirm")) {
                    event.setCancelled(true);
                    Nation nation = Nation.getById(instance.getUtilLists().nationDisbander.get(player.getUniqueId()));
                    nation.remove();
                    player.sendMessage("§bNATION §8| §7You disbanded §b" + nation.getName());
                    Bukkit.broadcastMessage("§bNATION §8| §7The nation of §b" + nation.getName() + " §7was disbanded by §6" + player.getName() + "§7.");
                    instance.getUtilLists().nationDisbander.remove(player.getUniqueId());
                }
            }

            // ACTUAL CHAT
            if (!event.isCancelled()) {
                if (isMuted() && !player.hasPermission("earth.chat.bypassmute")) {
                    event.setCancelled(true);
                    player.sendMessage(Lang.CHAT_IS_MUTED_ATM.get(player));
                    return;
                }

                if (config.getDouble("modules.chatsystem.cooldown") != 0.0) {
                    if (profile.getTimers().containsKey("chat") && !player.hasPermission("earth.chat.bypasscooldown")) {
                        player.sendMessage(Lang.CHAT_COOLDOWN.get(player).replace("%COOLDOWN%", Methods.getTimeAsString(new Timer(profile.getTimers().get("Chat")).getRemaining(), true)));
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
                                    event.setMessage(event.getMessage().replace(string, "§b@" + oplayer.getName() + "§r"));
                                }
                            });
                        }
                    }
                }

                if (player.hasPermission("earth.chat.hex")) {
                    event.setMessage(Methods.translateToHex(event.getMessage().replace("%", "%%")).replace("&", "§"));
                } else if (player.hasPermission("earth.chat.colours")) {
                    event.setMessage(event.getMessage().replace("%", "%%").replace("&", "§"));
                } else {
                    event.setMessage(event.getMessage().replace("&", "").replace("%", "%%"));
                }

                final String format = PlaceholderAPI.setPlaceholders(player, config.getString("modules.chatsystem.format").replace("%player_displayname%", player.getDisplayName()).replace("%chatcolor%", profile.getChatColor())).replace("%message%", event.getMessage());
                event.getRecipients().clear();

                for (Profile oProfile : Profile.onlineProfiles().values()) {
                    if (!oProfile.getBlocked().contains(profile.getUniqueId())) {
                        if (profile.isInNation()) {
                            if (oProfile.isInNation()) {
                                Nation.NationRelation rel = Nation.getRelation(oProfile.getNationId(), profile.getNationId());
                                net.md_5.bungee.api.chat.TextComponent component = new TextComponent(format.replace("%nations_rank%", profile.getCurrentNationRank().getPrefix()).replace("%nations_nation%", "§" + rel.colChar + profile.getCurrentNation().getName()));
                                component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/pm " + player.getName()));
                                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7" + Lang.CLICK_TO_PM.get(oProfile.getAsOfflinePlayer().getPlayer()).replace("%PLAYER%", player.getName())).create()));
                                oProfile.getAsOfflinePlayer().getPlayer().spigot().sendMessage(component);
                            } else {
                                TextComponent component = new TextComponent(format.replace("%nations_rank%", profile.getCurrentNationRank().getPrefix()).replace("%nations_nation%", "§f" + profile.getCurrentNation().getName()));
                                component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/pm " + player.getName()));
                                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7" + Lang.CLICK_TO_PM.get(oProfile.getAsOfflinePlayer().getPlayer()).replace("%PLAYER%", player.getName())).create()));
                                oProfile.getAsOfflinePlayer().getPlayer().spigot().sendMessage(component);
                            }
                        } else {
                            TextComponent component = new TextComponent(format.replace("%nations_rank%", "").replace("%nations_nation% " + ChatColor.DARK_GRAY + "| ", ""));
                            component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/pm " + player.getName()));
                            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7" + Lang.CLICK_TO_PM.get(oProfile.getAsOfflinePlayer().getPlayer()).replace("%PLAYER%", player.getName())).create()));
                            oProfile.getAsOfflinePlayer().getPlayer().spigot().sendMessage(component);
                        }
                    }
                }

                instance.getUtilLists().chatQueue.put(player.getUniqueId(), event.getMessage());

                if (config.getDouble("modules.chatsystem.cooldown") != 0.0 && !player.hasPermission("earth.chat.bypasscooldown")) {
                    Timer timer = new Timer(config.getLong("modules.chatsystem.cooldown") * 1000);
                    profile.getTimers().put("Chat", timer.toMap());
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
    public boolean isEnabled() {
        return config.getBoolean("modules.chatsystem.enabled");
    }

    public boolean isMuted() {
        return config.getBoolean("modules.chatsystem.muted");
    }

}
