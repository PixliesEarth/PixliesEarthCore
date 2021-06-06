package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Ideology;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.Religion;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.utils.Methods;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class infoNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"info", "n"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> returner = new HashMap<>();
        for (String s : NationManager.names.keySet())
            returner.put(s, 1);
        returner.put("player", 1);
        for (Player player : Bukkit.getOnlinePlayers())
            returner.put(player.getName(), 2);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        Nation nation;
        switch (args.length) {
            case 0:
                if (!checkIfPlayer(sender)) return false;
                Player player = (Player) sender;
                Profile profile = instance.getProfile(player.getUniqueId());
                if (!profile.isInNation()) {
                    Lang.NOT_IN_A_NATION.send(player);
                    return false;
                }
                sendNationInfo(profile.getCurrentNation(), sender);
                break;
            case 1:
                nation = Nation.getByName(args[0]);
                if (nation == null) {
                    Lang.NATION_DOESNT_EXIST.send(sender);
                    return false;
                }
                sendNationInfo(nation, sender);
                break;
            case 2:
                if (args[0].equalsIgnoreCase("player")) {
                    UUID targetUUID = Bukkit.getPlayerUniqueId(args[1]);
                    if (targetUUID == null) {
                        Lang.PLAYER_DOES_NOT_EXIST.send(sender);
                        return false;
                    }
                    Profile target = instance.getProfile(targetUUID);
                    if (!target.isInNation()) {
                        Lang.PLAYER_NOT_IN_NATION.send(sender);
                        return false;
                    }
                    sendNationInfo(target.getCurrentNation(), sender);
                }
                break;
        }
        return false;
    }

    public static void sendNationInfo(Nation nation, CommandSender sender) {
        sender.sendMessage(" ");
        sender.sendMessage("§3§l" + nation.getName() + " §8(" + Religion.valueOf(nation.getReligion()).getIcon() + "§8)");
        sender.sendMessage("    §7Description: §b" + nation.getDescription());
        sender.sendMessage("    §7Ideology/Religion: §" + Ideology.valueOf(nation.getIdeology()).getColour() + WordUtils.capitalize(nation.getIdeology().toLowerCase().replace("_", " ")) + "§7/§" + Religion.valueOf(nation.getReligion()).getColour() + WordUtils.capitalize(nation.getReligion().toLowerCase().replace("_", " ")));
        long age = System.currentTimeMillis() - Long.parseLong(nation.getCreated());
        sender.sendMessage("    §7Age: §b" + Methods.formatTime(age));
        sender.sendMessage("    §7Era: §b" + StringUtils.capitalize(nation.getEra().toLowerCase()));
        String leader = nation.getLeader().equals("NONE") ? "SERVER" : Bukkit.getOfflinePlayer(UUID.fromString(nation.getLeader())).getName();
        sender.sendMessage("    §7Leader: §6" + leader);
        sender.sendMessage("    §7Territory: §b" + nation.getChunks().size() + "§8/§b" + nation.getMaxClaimingPower() + " §7(§e" + nation.getClaimBoost() + "§7)");
        StringJoiner memberJoiner = new StringJoiner("§8, ");
        for (String s : nation.getMembers()) {
            UUID uuid = UUID.fromString(s);
            OfflinePlayer member = Bukkit.getOfflinePlayer(uuid);
            if (!member.hasPlayedBefore()) continue;
            if (member.isOnline()) memberJoiner.add("§a" + member.getName()); else memberJoiner.add("§7" + member.getName());
        }
        StringJoiner allyJoiner = new StringJoiner("§8, ");
        for (String s : nation.getAllies()) {
            Nation ally = Nation.getById(s);
            if (ally != null)
                allyJoiner.add("§d" + ally.getName());
        }
        sender.sendMessage("    §7Allies: " + allyJoiner.toString());
        sender.sendMessage("    §7Population: " + memberJoiner.toString());
        sender.sendMessage(" ");
    }

}
