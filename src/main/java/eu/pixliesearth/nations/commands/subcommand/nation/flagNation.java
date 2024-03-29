package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.NationFlag;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class flagNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"flag", "flags"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> map = new HashMap<>();
        for (NationFlag flag : NationFlag.values())
            map.put(flag.name(), 1);
        for (String s : NationManager.names.keySet())
            map.put(ChatColor.AQUA + s, 2);
        return map;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            Profile profile = instance.getProfile(player.getUniqueId());
            if (args.length == 2) {
                Nation nation = Nation.getByName(args[1]);
                if (nation == null) {
                    Lang.NATION_DOESNT_EXIST.send(player);
                    return false;
                }
                if (!Permission.hasForeignPermission(profile, Permission.EDIT_FLAGS, nation)) {
                    Lang.NO_PERMISSIONS.send(sender);
                    return false;
                }
                if (!NationFlag.exists(args[0])) {
                    Lang.X_DOESNT_EXIST.send(player, "%X%;flag");
                    return false;
                }
                NationFlag flag = NationFlag.valueOf(args[0].toUpperCase());
                if (flag.isRequiresStaff() && !profile.isStaff()) {
                    Lang.NO_PERMISSIONS.send(player);
                    return false;
                }
                if (nation.getFlags().contains(flag.name())) {
                    nation.getFlags().remove(flag.name());
                    nation.save();
                    for (Player member : nation.getOnlineMemberSet()) {
                        if (member.getUniqueId() == player.getUniqueId()) continue;
                        Lang.PLAYER_REMOVED_X.send(member, "%PLAYER%;" + player.getDisplayName(), "%X%;flag", "%Y%;" + flag.name());
                    }
                    Lang.PLAYER_REMOVED_X.send(player, "%PLAYER%;" + player.getDisplayName(), "%X%;flag", "%Y%;" + flag.name());
                } else {
                    nation.getFlags().add(flag.name());
                    nation.save();
                    for (Player member : nation.getOnlineMemberSet()) {
                        if (member.getUniqueId() == player.getUniqueId()) continue;
                        Lang.PLAYER_ADDED_X.send(member, "%PLAYER%;" + player.getDisplayName(), "%X%;flag", "%Y%;" + flag.name());
                    }
                    Lang.PLAYER_ADDED_X.send(player, "%PLAYER%;" + player.getDisplayName(), "%X%;flag", "%Y%;" + flag.name());
                }
            } else {
                if (args.length < 1) {
                    Lang.WRONG_USAGE_NATIONS.send(player, "%USAGE&;/n flag <FLAG> [NATION]");
                    return false;
                }
                if (!profile.isInNation()) {
                    Lang.NOT_IN_A_NATION.send(player);
                    return false;
                }
                Nation nation = profile.getCurrentNation();
                if (!NationFlag.exists(args[0])) {
                    Lang.X_DOESNT_EXIST.send(player, "%X%;flag");
                    return false;
                }
                if (!Permission.EDIT_FLAGS.hasPermission(sender)) {
                    Lang.NO_PERMISSIONS.send(sender);
                    return false;
                }
                NationFlag flag = NationFlag.valueOf(args[0].toUpperCase());
                if (flag.isRequiresStaff() && !profile.isStaff()) {
                    Lang.NO_PERMISSIONS.send(player);
                    return false;
                }
                if (nation.getFlags().contains(flag.name())) {
                    nation.getFlags().remove(flag.name());
                    nation.save();
                    for (Player member : nation.getOnlineMemberSet()) {
                        if (member.getUniqueId() == player.getUniqueId()) continue;
                        Lang.PLAYER_REMOVED_X.send(member, "%PLAYER%;" + player.getDisplayName(), "%X%;flag", "%Y%;" + flag.name());
                    }
                    Lang.PLAYER_REMOVED_X.send(player, "%PLAYER%;" + player.getDisplayName(), "%X%;flag", "%Y%;" + flag.name());
                } else {
                    nation.getFlags().add(flag.name());
                    nation.save();
                    for (Player member : nation.getOnlineMemberSet()) {
                        if (member.getUniqueId() == player.getUniqueId()) continue;
                        Lang.PLAYER_ADDED_X.send(member, "%PLAYER%;" + player.getDisplayName(), "%X%;flag", "%Y%;" + flag.name());
                    }
                    Lang.PLAYER_ADDED_X.send(player, "%PLAYER%;" + player.getDisplayName(), "%X%;flag", "%Y%;" + flag.name());
                }
            }
        } else {
            if (args.length < 2) {
                Lang.WRONG_USAGE_NATIONS.send(sender, "%USAGE&;/n flag <FLAG> [NATION]");
                return false;
            }
            Nation nation = Nation.getByName(args[1]);
            if (nation == null) {
                Lang.NATION_DOESNT_EXIST.send(sender);
                return false;
            }
            if (!NationFlag.exists(args[0])) {
                Lang.X_DOESNT_EXIST.send(sender, "%X%;flag");
                return false;
            }
            NationFlag flag = NationFlag.valueOf(args[0].toUpperCase());
            if (nation.getFlags().contains(flag.name())) {
                nation.getFlags().remove(flag.name());
                nation.save();
                for (Player member : nation.getOnlineMemberSet())
                    Lang.PLAYER_REMOVED_X.send(member, "%PLAYER%;" + sender.getName(), "%X%;flag", "%Y%;" + flag.name());
                Lang.PLAYER_REMOVED_X.send(sender, "%PLAYER%;" + sender.getName(), "%X%;flag", "%Y%;" + flag.name());
            } else {
                nation.getFlags().add(flag.name());
                nation.save();
                for (Player member : nation.getOnlineMemberSet())
                    Lang.PLAYER_ADDED_X.send(member, "%PLAYER%;" + sender.getName(), "%X%;flag", "%Y%;" + flag.name());
                Lang.PLAYER_ADDED_X.send(sender, "%PLAYER%;" + sender.getName(), "%X%;flag", "%Y%;" + flag.name());
            }
        }
        return false;
    }

}
