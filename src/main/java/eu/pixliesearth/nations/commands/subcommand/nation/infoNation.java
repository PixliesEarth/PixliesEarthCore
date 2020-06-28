package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;

public class infoNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"info", "n"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        for (String n : NationManager.names.keySet())
            returner.put(n, 1);
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
    public boolean execute(CommandSender sender, String[] args) {
        Nation nation;
        switch (args.length) {
            case 0:
                if (!(sender instanceof Player)) {
                    Lang.ONLY_PLAYERS_EXEC.send(sender);
                    return false;
                }
                Player player = (Player) sender;
                Profile profile = instance.getProfile(player.getUniqueId());
                if (!profile.isInNation()) {
                    Lang.NOT_IN_A_NATION.send(player);
                    return false;
                }
                sendNationInfo(profile.getCurrentNation(), sender);
                break;
            case 1:
                nation = Nation.getById(args[0]);
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

    public void sendNationInfo(Nation nation, CommandSender sender) {
        sender.sendMessage("§b§n" + nation.getName());
        sender.sendMessage("§7Description: §b" + nation.getDescription());
        sender.sendMessage("§7Balance: §2§l$§a" + nation.getMoney());
        sender.sendMessage("§7Era: §b" + nation.getEra());
        sender.sendMessage("§7Leader: §6" + Bukkit.getOfflinePlayer(UUID.fromString(nation.getLeader())).getName());
        StringJoiner memberJoiner = new StringJoiner("§8, ");
        for (String s : nation.getMembers()) {
            UUID uuid = UUID.fromString(s);
            Profile profile = instance.getProfile(uuid);
            if (profile.isOnline()) {
                memberJoiner.add("§a" + profile.getAsOfflinePlayer().getName());
            } else {
                memberJoiner.add("§7" + profile.getAsOfflinePlayer().getName());
            }
        }
        StringJoiner allyJoiner = new StringJoiner("§8, ");
        for (String s : nation.getAllies()) {
            Nation ally = Nation.getById(s);
            if (ally != null)
                allyJoiner.add("§d" + ally.getName());
        }
        sender.sendMessage("§7Allies: " + allyJoiner.toString());
        sender.sendMessage("§7Members: " + memberJoiner.toString());
    }

}
