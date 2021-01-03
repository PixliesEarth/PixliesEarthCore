package eu.pixliesearth.core.modules;

import com.vdurmont.emoji.EmojiParser;
import eu.pixliesearth.core.interfaces.Module;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.Warp;
import eu.pixliesearth.events.NationDisbandEvent;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ChatSystem implements Listener, Module {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        // CHAT INPUTS
        if (config.getBoolean("modules.chatsystem.enabled")) {
            Player player = event.getPlayer();
            Profile profile = instance.getProfile(player.getUniqueId());
            if (instance.getUtilLists().warpAdder.contains(player.getUniqueId())) {
                event.setCancelled(true);
                Material mat = Material.GRASS_BLOCK;
                if (player.getInventory().getItemInMainHand().getType() != Material.AIR)
                    mat = player.getInventory().getItemInMainHand().getType();
                new Warp(event.getMessage(), player.getLocation(), mat.name()).serialize();
                instance.getUtilLists().warpAdder.remove(player.getUniqueId());
                player.sendMessage("§aEARTH §8| §7You §asuccessfully §7created the warp §b" + event.getMessage() + "§7!");
                return;
            }

            if (instance.getUtilLists().nationDisbander.containsKey(player.getUniqueId())) {
                if (event.getMessage().equalsIgnoreCase("cancel")) {
                    event.setCancelled(true);
                    player.sendMessage(Lang.NATION + " §8| §7Nation disband process §ccancelled§7.");
                    instance.getUtilLists().nationDisbander.remove(player.getUniqueId());
                } else if (event.getMessage().equalsIgnoreCase("confirm")) {
                    event.setCancelled(true);
                    Bukkit.getScheduler().runTask(instance, () -> {
                        final Nation nation = Nation.getById(instance.getUtilLists().nationDisbander.get(player.getUniqueId()));
                        NationDisbandEvent disbandEvent = new NationDisbandEvent(player, nation);
                        Bukkit.getPluginManager().callEvent(disbandEvent);
                        if (!disbandEvent.isCancelled()) {
                            // profile.depositMoney(nation.getMoney(), "Nation disband of " + nation.getName());
                            nation.remove();
                            player.sendMessage(Lang.NATION + " §8| §7You disbanded §b" + nation.getName());
                            Bukkit.broadcastMessage(Lang.NATION + " §8| §7The nation of §b" + nation.getName() + " §7was disbanded by §6" + player.getName() + "§7.");
                            instance.getUtilLists().nationDisbander.remove(player.getUniqueId());
                        }
                    });
                }
                return;
            }

            if (instance.getUtilLists().dynmapSetters.contains(player.getUniqueId())) {
                event.setCancelled(true);
                if (event.getMessage().equalsIgnoreCase("cancel")) {
                    instance.getUtilLists().dynmapSetters.remove(player.getUniqueId());
                    player.sendMessage("§cCancelled.");
                } else {
                    String name = event.getMessage().split(" ")[0];
                    Bukkit.getScheduler().runTask(instance, () -> {
                       player.setOp(true);
                       player.performCommand("dmarker add " + name + " icon:building");
                       player.setOp(false);
                    });
                    player.sendMessage("§aDynmap marker added.");
                    instance.getUtilLists().dynmapSetters.remove(player.getUniqueId());
                    profile.getExtras().put("dynmapMarkers", (int) profile.getExtras().get("dynmapMarkers") - 1);
                    profile.save();
                }
                return;
            }

            if (instance.getUtilLists().royalGifters.contains(player.getUniqueId())) {
                event.setCancelled(true);
                if (event.getMessage().equalsIgnoreCase("cancel")) {
                    instance.getUtilLists().royalGifters.remove(player.getUniqueId());
                    player.sendMessage("§cCancelled");
                } else {
                    UUID uuid = Bukkit.getPlayerUniqueId(event.getMessage().split(" ")[0]);
                    if (uuid == null || !Bukkit.getOfflinePlayer(uuid).isOnline()) {
                        Lang.PLAYER_DOES_NOT_EXIST.send(player);
                        return;
                    }
                    Bukkit.getScheduler().runTask(instance, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + event.getMessage().split(" ")[0] + " parent addtemp amethyst 6d"));
                    profile.getExtras().put("giftedRoyal", true);
                    profile.save();
                    instance.getUtilLists().royalGifters.remove(player.getUniqueId());
                    player.sendMessage("§7You have successfully gifted your royal.");
                }
                return;
            }

            if (instance.getUtilLists().chatTypes.containsKey(player.getUniqueId())) {
                if (!profile.isInNation()) {
                    instance.getUtilLists().chatTypes.remove(player.getUniqueId());
                } else {
                    event.setCancelled(true);
                    switch (instance.getUtilLists().chatTypes.get(player.getUniqueId())) {
                        case NATION:
                            profile.getCurrentNation().broadcastMembers("§bNATION-CHAT §8| " + profile.getCurrentNationRank().getPrefix() + player.getDisplayName() + " §8» §7" + event.getMessage());
                            for (UUID uuid : instance.getUtilLists().staffMode) {
                                Player target = Bukkit.getPlayer(uuid);
                                if (target == null) continue;
                                target.sendMessage("§cSTAFF-§bNATION-CHAT §8| §d" + profile.getCurrentNation().getName() + " §8| " + profile.getCurrentNationRank().getPrefix() + player.getDisplayName() + " §8» §7" + event.getMessage());
                            }
                            break;
                        case ALLY:
                            profile.getCurrentNation().broadcastMembers("§dALLY-CHAT §8| §b" + profile.getCurrentNation().getName() + " §8| " + profile.getCurrentNationRank().getPrefix() + player.getDisplayName() + " §8» §7" + event.getMessage());
                            for (String s : profile.getCurrentNation().getAllies()) {
                                Nation ally = Nation.getById(s);
                                if (ally == null) continue;
                                ally.broadcastMembers("§dALLY-CHAT §8| §d" + profile.getCurrentNation().getName() + " §8| " + profile.getCurrentNationRank().getPrefix() + player.getDisplayName() + " §8» §7" + event.getMessage());
                            }
                            for (UUID uuid : instance.getUtilLists().staffMode) {
                                Player target = Bukkit.getPlayer(uuid);
                                if (target == null) continue;
                                target.sendMessage("§cSTAFF-§dALLY-CHAT §8| §d" + profile.getCurrentNation().getName() + " §8| " + profile.getCurrentNationRank().getPrefix() + player.getDisplayName() + " §8» §7" + event.getMessage());
                            }
                            break;
                       default :
                    	    break;
                    }
                    return;
                }
            }

            if (config.getDouble("modules.chatsystem.cooldown") != 0.0) {
                if (profile.getTimers().containsKey("Chat") && !player.hasPermission("earth.chat.bypasscooldown")) {
                    player.sendMessage(Lang.CHAT_COOLDOWN.get(player).replace("%COOLDOWN%", Methods.getTimeAsString(new Timer(profile.getTimers().get("Chat")).getRemaining(), true)));
                    event.setCancelled(true);
                    return;
                }
            }

            // ACTUAL CHAT
            if (!event.isCancelled()) {
                if (isMuted() && !player.hasPermission("earth.chat.bypassmute")) {
                    event.setCancelled(true);
                    player.sendMessage(Lang.CHAT_IS_MUTED_ATM.get(player));
                    return;
                }

                if (!player.hasPermission("earth.chat.bypassblacklist")) {
                        for (String s1 : config.getStringList("modules.chatsystem.blacklist"))
                            if (StringUtils.containsIgnoreCase(event.getMessage(), s1)) {
                                event.setCancelled(true);
                                player.sendMessage(Lang.EARTH + "§cYou are not allowed to say that here!");
                                return;
                            }
                                // event.setMessage(event.getMessage().replace(s1, Methods.replaceBadWord(s1)));
                }

                // "@" MENTIONING SYSTEM
                if (event.getMessage().contains("@")) {
                    for (String string : event.getMessage().split(" ")) {
                        if (string.startsWith("@")) {
                            Bukkit.getOnlinePlayers().forEach(oplayer -> {
                                if (string.equalsIgnoreCase("@" + oplayer.getName())) {
                                    oplayer.playSound(oplayer.getLocation(), Sound.valueOf(profile.getMessageSound()), 1, 1);
                                    event.setMessage(event.getMessage().replace(string, "§b@" + oplayer.getName() + "§r"));
                                }
                            });
                        }
                    }
                }

                if (player.hasPermission("earth.chat.hex")) {
                    event.setMessage(Methods.translateToHex("§" + profile.getChatColor() + event.getMessage().replace("%", "%%")).replace("&", "§"));
                } else if (player.hasPermission("earth.chat.colours")) {
                    event.setMessage("§" + profile.getChatColor() + event.getMessage().replace("%", "%%").replace("&", "§"));
                } else {
                    event.setMessage("§" + profile.getChatColor() + event.getMessage().replace("&", "").replace("%", "%%"));
                }

                event.setMessage(EmojiParser.parseToUnicode(event.getMessage()));

                String format = PlaceholderAPI.setPlaceholders(player, config.getString("modules.chatsystem.format").replace("%player_displayname%", player.getDisplayName()).replace("%chatcolor%", profile.getChatColor()).replace("%message%", event.getMessage()));
                event.getRecipients().clear();

                for (Profile oProfile : Profile.onlineProfiles().values()) {
                    if (!oProfile.getBlocked().contains(profile.getUniqueId())) {
                        if (profile.isInNation()) {
                            if (oProfile.isInNation()) {
                                Nation.NationRelation rel = Nation.getRelation(oProfile.getNationId(), profile.getNationId());
                                oProfile.getAsPlayer().sendMessage(format.replace("%nations_rank%", profile.getCurrentNationRank().getPrefix()).replace("%nations_nation%", "§" + rel.colChar + profile.getCurrentNation().getName()));
                            } else {
                                oProfile.getAsPlayer().sendMessage(format.replace("%nations_rank%", profile.getCurrentNationRank().getPrefix()).replace("%nations_nation%", "§f" + profile.getCurrentNation().getName()));
                            }
                        } else {
                            oProfile.getAsPlayer().sendMessage(format.replace("%nations_rank%", "").replace("%nations_nation%", ""));
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
