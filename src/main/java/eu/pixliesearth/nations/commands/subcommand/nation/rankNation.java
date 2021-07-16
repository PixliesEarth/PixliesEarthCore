package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.entities.nation.ranks.Rank;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class rankNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"rank", "permissions", "perms"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> map = new HashMap<>();
        if (args.length == 2) {
            map.put("create", 1);
            map.put("remove", 1);
            map.put("addpermission", 1);
            map.put("removepermission", 1);
            map.put("set", 1);
            map.put("rename", 1);
        } else {
            switch (args[1].toLowerCase()) {
                case "create":
                    map.put("§bRank Name", 2);
                    map.put("§cRank Prefix", 3);
                    map.put("§6Rank Priority (0-665)", 4);
                    break;
                case "remove":
                    if (sender instanceof Player player) {
                        Profile profile = instance.getProfile(player.getUniqueId());
                        if (profile.isInNation()) {
                            Nation nation = profile.getCurrentNation();
                            for (Map<String, Object> rankObject : nation.getRanks().values()) {
                                Rank rank = Rank.get(rankObject);
                                if (profile.isStaff() || rank.getPriority() <= profile.getCurrentNationRank().getPriority() && Permission.EDIT_RANKS.hasPermission(sender))
                                    map.put(rank.getName(), 2);
                            }
                        }
                    }
                    break;
                case "set":
                    Bukkit.getOnlinePlayers().forEach(p -> map.put(p.getName(), 2));
                    if (sender instanceof Player player) {
                        Profile profile = instance.getProfile(player.getUniqueId());
                        if (profile.isInNation()) {
                            Nation nation = profile.getCurrentNation();
                            for (Map<String, Object> rankObject : nation.getRanks().values()) {
                                Rank rank = Rank.get(rankObject);
                                if (profile.isStaff() || rank.getPriority() <= profile.getCurrentNationRank().getPriority() && Permission.EDIT_RANKS.hasPermission(sender))
                                    map.put(rank.getName(), 3);
                            }
                        }
                    }
                    break;
                case "removepermission":
                case "addpermission":
                    if (sender instanceof Player player) {
                        Profile profile = instance.getProfile(player.getUniqueId());
                        if (profile.isInNation()) {
                            Nation nation = profile.getCurrentNation();
                            for (Map<String, Object> rankObject : nation.getRanks().values()) {
                                Rank rank = Rank.get(rankObject);
                                if (profile.isStaff() || rank.getPriority() <= profile.getCurrentNationRank().getPriority() && Permission.EDIT_RANKS.hasPermission(sender))
                                    map.put(rank.getName(), 3);
                            }
                            for (Permission permission : Permission.values()) if (permission.hasPermission(sender)) map.put(permission.name(), 2);
                        }
                    }
                    break;
                case "rename":
                    if (sender instanceof Player player) {
                        Profile profile = instance.getProfile(player.getUniqueId());
                        if (profile.isInNation()) {
                            Nation nation = profile.getCurrentNation();
                            for (Map<String, Object> rankObject : nation.getRanks().values()) {
                                Rank rank = Rank.get(rankObject);
                                if (profile.isStaff() || rank.getPriority() <= profile.getCurrentNationRank().getPriority() && Permission.EDIT_RANKS.hasPermission(sender))
                                    map.put(rank.getName(), 2);
                            }
                            for (int i = 3; i < 30; i++) map.put("§b\"Rank Prefix\"", i);
                        }
                    }
                    break;
            }
        }
        return map;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public String getSyntax() {
        return """
                §7Create a rank: §b/n rank create §eRANK-NAME §cRANK-PREFIX §6RANK-PRIORITY (Number between 0 and 665)
                §7Remove a rank: §b/n rank remove §eRANK-NAME
                §7Set a players rank: §b/n rank set §ePLAYER §cRANK-NAME
                §7Add a permission to a rank: §b/n rank addpermission §ePERMISSION §cRANK-NAME
                §7Remove a permission from a rank: §b/n rank removepermission §ePERMISSION §cRANK-NAME
                §7Rename a rank prefix: §b/n rank rename §eRANK §cPREFIX""";
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        if (!Permission.hasNationPermission(profile, Permission.EDIT_RANKS) && !profile.isStaff()) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        Nation n = profile.getCurrentNation();

        if (args[0].equalsIgnoreCase("rename")) {
            if (n.getRanks().get(args[1]) == null) {
                Lang.RANK_DOESNT_EXIST.send(player);
                return false;
            }
            Rank rank = Rank.get(n.getRanks().get(args[1]));
            if (!profile.isStaff() && profile.getCurrentNationRank().getPriority() <= rank.getPriority() && profile.getCurrentNationRank().getPriority() != 666) {
                Lang.CANT_SET_RANK_WITH_HIGHER_OR_EQUAL_PRIORITY.send(player);
                return false;
            }
            StringBuilder allArgs = new StringBuilder();
            for (String s : args)
                allArgs.append(s).append(" ");
            String prefix = StringUtils.substringBetween(allArgs.toString(), "\"", "\"") != null ? StringUtils.substringBetween(allArgs.toString(), "\"", "\"") : args[2];
            if (prefix.length() > 15) {
                Lang.INVALID_INPUT.send(sender);
                return false;
            }
            rank.setPrefix(prefix.replace("&", "§"));
            n.getRanks().put(rank.getName(), rank.toMap());
            n.save();
            sender.sendMessage(Lang.NATION + "§7You successfully renamed the rank to §b" + rank.getName() + "§7.");
            return false;
        }

        switch (args.length) {
            case 4:
                if (args[0].equalsIgnoreCase("create")) {
                    if (!StringUtils.isNumeric(args[3])) {
                        Lang.INVALID_INPUT.send(player);
                        return false;
                    }
                    if (Integer.parseInt(args[3]) >= 666) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    if (n.getRanks().get(args[1]) != null || n.getRankByPriority(Integer.parseInt(args[3])) != null) {
                        Lang.RANK_ALREADY_EXISTS.send(player);
                        return false;
                    }
                    n.getRanks().put(args[1], new Rank(args[1], args[2].replace("&", "§"), Integer.parseInt(args[3]), new ArrayList<>()).toMap());
                    n.save();
                    Lang.RANK_CREATED.send(player, "%RANK%;" + args[1]);
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("set")) {
                    if (args[2].equalsIgnoreCase("leader")) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    UUID targetUUID = Bukkit.getPlayerUniqueId(args[1]);
                    if (targetUUID == null) {
                        Lang.PLAYER_DOES_NOT_EXIST.send(player);
                        return false;
                    }
                    Profile target = instance.getProfile(targetUUID);
                    if (!target.isInNation() || !target.getNationId().equals(profile.getNationId())) {
                        Lang.PLAYER_IS_NOT_IN_SAME_NATION_AS_YOU.send(player);
                        return false;
                    }
                    if (n.getRanks().get(args[2]) == null) {
                        Lang.RANK_DOESNT_EXIST.send(player);
                        return false;
                    }
                    if (target.isLeader()) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    Rank rank = Rank.get(n.getRanks().get(args[2]));
                    if (!profile.isStaff() && profile.getCurrentNationRank().getPriority() <= rank.getPriority()) {
                        Lang.CANT_SET_RANK_WITH_HIGHER_OR_EQUAL_PRIORITY.send(player);
                        return false;
                    }
                    target.setNationRank(rank.getName());
                    target.save();
                    Lang.CHANGED_PLAYERS_NATION_RANK.send(player, "%RANK%;" + rank.getName(), "%PLAYER%;" + target.getAsOfflinePlayer().getName());
                } else if (args[0].equalsIgnoreCase("addpermission")) {
                    if (args[1].equalsIgnoreCase("leader")) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    Rank rank = Rank.get(n.getRanks().get(args[1]));
                    if (!Permission.exists(args[2])) {
                        Lang.PERMISSION_DOESNT_EXIST.send(player);
                        return false;
                    }
                    if (!profile.isStaff() && profile.getCurrentNationRank().getPriority() <= rank.getPriority()) {
                        Lang.CANT_SET_RANK_WITH_HIGHER_OR_EQUAL_PRIORITY.send(player);
                        return false;
                    }
                    if (rank.getPermissions().contains(args[2].toUpperCase())) {
                        Lang.RANK_ALREADY_HAS_PERMISSION.send(player);
                        return false;
                    }
                    rank.getPermissions().add(args[2].toUpperCase());
                    n.getRanks().put(rank.getName(), rank.toMap());
                    n.save();
                    Lang.ADDED_PERMISSION_TO_RANK.send(player, "%RANK%;" + rank.getName(), "%PERMISSION%;" + args[2].toUpperCase());
                } else if (args[0].equalsIgnoreCase("removepermission")) {
                    if (args[1].equalsIgnoreCase("leader")) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    Rank rank = Rank.get(n.getRanks().get(args[1]));
                    if (!Permission.exists(args[2])) {
                        Lang.PERMISSION_DOESNT_EXIST.send(player);
                        return false;
                    }
                    if (!profile.isStaff() && profile.getCurrentNationRank().getPriority() <= rank.getPriority()) {
                        Lang.CANT_SET_RANK_WITH_HIGHER_OR_EQUAL_PRIORITY.send(player);
                        return false;
                    }
                    if (!rank.getPermissions().contains(args[2].toUpperCase())) {
                        Lang.RANK_DOES_NOT_HAVE_PERMISSION.send(player);
                        return false;
                    }
                    rank.getPermissions().remove(args[2].toUpperCase());
                    n.getRanks().put(rank.getName(), rank.toMap());
                    n.save();
                    Lang.REMOVED_PERMISSION_FROM_RANK.send(player, "%RANK%;" + rank.getName(), "%PERMISSION%;" + args[2].toUpperCase());
                }
                break;
            case 2:
                if (args[0].equalsIgnoreCase("remove")) {
                    if (n.getRanks().get(args[1]) == null) {
                        Lang.RANK_DOESNT_EXIST.send(player);
                        return false;
                    }
                    Rank rank = Rank.get(n.getRanks().get(args[1]));
                    if (rank.getPriority() >= profile.getCurrentNationRank().getPriority() || rank.getName().equalsIgnoreCase("newbie") || rank.getName().equalsIgnoreCase("member") || rank.getName().equalsIgnoreCase("admin")) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    for (Profile p : n.getProfilesByRank(rank)) {
                        p.setNationRank("admin");
                        p.save();
                    }
                    n.getRanks().remove(args[1]);
                    n.save();
                    Lang.YOU_DELETED_NATION_RANK.send(player, "%RANK%;" + rank.getName());
                }
                break;
            default:
                sendSyntax(sender, "rank");
                break;

        }
        return false;
    }

}
