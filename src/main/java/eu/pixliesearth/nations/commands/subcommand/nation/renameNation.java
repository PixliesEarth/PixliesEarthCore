package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.utils.Timer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class renameNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"rename", "name"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> returner = new HashMap<>();
        for (Map.Entry<String, String> entry : NationManager.names.entrySet())
            returner.put(entry.getKey(), 2);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Nation nation;
            Profile profile = instance.getProfile(player.getUniqueId());
            Map<String, String> placeholders;
            boolean rename;
            switch (args.length) {
                case 1:
                    if (!profile.isInNation()) {
                        player.sendMessage(Lang.NOT_IN_A_NATION.get(player));
                        return false;
                    }
                    if (!profile.getNationRank().equals("leader")) {
                        Lang.NO_PERMISSIONS.send(sender);
                        return false;
                    }
                    nation = profile.getCurrentNation();
                    final String oldName = nation.getName();
                    if (args[0].length() < 3 || args[0].length() > 15 || !StringUtils.isAlphanumeric(args[0])) {
                        player.sendMessage(Lang.NATION_NAME_UNVALID.get(player).replace("10", "15"));
                        return false;
                    }
                    if (nation.getExtras().containsKey("RENAME-TIMER") && !profile.isStaff()) {
                        Timer renameTimer = new Timer((Map<String, String>) nation.getExtras().get("RENAME-TIMER"));
                        if (renameTimer.hasExpired()) {
                            nation.getExtras().remove("RENAME-TIMER");
                            nation.save();
                        } else {
                            sender.sendMessage(Lang.NATION + "§7You can change your nation name again in §c§l" + renameTimer.getRemainingAsString() + "§7.");
                            return false;
                        }
                    }
                    rename = nation.rename(args[0].replace("&", ""));
                    if (!rename) {
                        Lang.NATION_WITH_NAME_ALREADY_EXISTS.send(sender);
                        return false;
                    }
                    nation.getExtras().put("RENAME-TIMER", new Timer(Timer.DAY * 14).toMap());
                    nation.save();
                    placeholders = new HashMap<>();
                    placeholders.put("%PLAYER%", player.getDisplayName());
                    placeholders.put("%OLD%", oldName);
                    placeholders.put("%NEW%", nation.getName());
                    Lang.PLAYER_NAMED_NATION_NAME.broadcast(placeholders);
                    if (MiniMick.getConfigs().containsKey(nation.getNationId())) {
                        try {
                            ServerTextChannel channel = MiniMick.getApi().getServerTextChannelById(MiniMick.getConfigs().get(nation.getNationId()).getUpdatesChannel()).get();
                            channel.sendMessage(
                                    new EmbedBuilder()
                                            .setTitle(player.getName() + " has renamed your nation to " + args[0].replace("&", ""))
                                            .setAuthor(player.getName(), "", "https://minotar.net/avatar/" + player.getUniqueId())
                                            .setColor(Color.CYAN)
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    nation = Nation.getByName(args[1]);
                    if (!instance.getUtilLists().staffMode.contains(player.getUniqueId()) && !Permission.hasForeignPermission(profile, Permission.NAME, nation)) {
                        player.sendMessage(Lang.NO_PERMISSIONS.get(player));
                        return false;
                    }
                    if (nation == null) {
                        player.sendMessage(Lang.NATION_DOESNT_EXIST.get(player));
                        return false;
                    }
                    if (nation.getExtras().containsKey("RENAME-TIMER") && !profile.isStaff()) {
                        Timer renameTimer = new Timer((Map<String, String>) nation.getExtras().get("RENAME-TIMER"));
                        if (renameTimer.hasExpired()) {
                            nation.getExtras().remove("RENAME-TIMER");
                            nation.save();
                        } else {
                            sender.sendMessage(Lang.NATION + "§7You can change your nation name again in §c§l" + renameTimer.getRemainingAsString() + "§7.");
                            return false;
                        }
                    }
                    final String oldName1 = nation.getName();
                    rename = nation.rename(args[0]);
                    if (!rename) {
                        Lang.NATION_WITH_NAME_ALREADY_EXISTS.send(sender);
                        return false;
                    }
                    nation.getExtras().put("RENAME-TIMER", new Timer(Timer.DAY * 14).toMap());
                    nation.save();
                    placeholders = new HashMap<>();
                    placeholders.put("%PLAYER%", player.getDisplayName());
                    placeholders.put("%OLD%", oldName1);
                    placeholders.put("%NEW%", nation.getName());
                    Lang.PLAYER_NAMED_NATION_NAME.broadcast(placeholders);
                    if (MiniMick.getConfigs().containsKey(nation.getNationId())) {
                        try {
                            ServerTextChannel channel = MiniMick.getApi().getServerTextChannelById(MiniMick.getConfigs().get(nation.getNationId()).getUpdatesChannel()).get();
                            channel.sendMessage(
                                    new EmbedBuilder()
                                            .setTitle(player.getName() + " has renamed your nation to " + args[0].replace("&", ""))
                                            .setAuthor(player.getName(), "", "https://minotar.net/avatar/" + player.getUniqueId())
                                            .setColor(Color.CYAN)
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        } else {
            Nation nation = Nation.getByName(args[1]);
            if (nation == null) {
                sender.sendMessage(Lang.NATION_DOESNT_EXIST.get(sender));
                return false;
            }
            final String oldName1 = nation.getName();
            boolean success = nation.rename(args[0]);
            if (!success) {
                Lang.NATION_WITH_NAME_ALREADY_EXISTS.send(sender);
                return false;
            }
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("%PLAYER%", sender.getName());
            placeholders.put("%OLD%", oldName1);
            placeholders.put("%NEW%", nation.getName());
            Lang.PLAYER_NAMED_NATION_NAME.broadcast(placeholders);
            if (MiniMick.getConfigs().containsKey(nation.getNationId())) {
                try {
                    ServerTextChannel channel = MiniMick.getApi().getServerTextChannelById(MiniMick.getConfigs().get(nation.getNationId()).getUpdatesChannel()).get();
                    channel.sendMessage(
                            new EmbedBuilder()
                                    .setTitle(sender.getName() + " has renamed your nation to " + args[0].replace("&", ""))
                                    .setAuthor(sender.getName(), "", "https://minotar.net/avatar/" + sender.getName())
                                    .setColor(Color.CYAN)
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}
