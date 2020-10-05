package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class foreignPermission implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"fp", "foreignpermission"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> map = new HashMap<>();
        map.put("set", 1);
        map.put("unset", 1);
        for (Permission permission : Permission.values())
            map.put(permission.name(), 2);
        map.put("nation", 3);
        map.put("player", 3);
        return map;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!checkIfPlayer(sender)) return false;
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        Nation host = profile.getCurrentNation();
        if (!Permission.hasNationPermission(profile, Permission.FOREIGN_PERMS)) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        if (!Permission.exists(args[1])) {
            Lang.WRONG_USAGE_NATIONS.send(player, "%USAGE%;/n fp set/unset PERMISSION nation/player NATIONNAME/PLAYERNAME");
            return false;
        }
        Permission permission = Permission.valueOf(args[1].toUpperCase());
        if (!Permission.hasForeignPermission(profile, permission, host)) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        if (args[2].equalsIgnoreCase("nation")) {
            Nation nation = Nation.getByName(args[3]);
            if (nation == null) {
                Lang.NATION_DOESNT_EXIST.send(player);
                return false;
            }
            if (nation.getNationId().equals(host.getNationId())) {
                Lang.TWO_NATIONS_ARE_THE_SAME.send(player);
                return false;
            }
            if (args[0].equalsIgnoreCase("set")) {
                if (Permission.hasForeignPermission(nation, permission, host)) {
                    Lang.X_ALREADY_HAS_PERMISSION.send(player, "%X%;" + nation.getName());
                    return false;
                }
                nation.addForeignPermission(host, permission);
                Lang.ADDED_PERMISSION_TO_X.send(player, "%X%;" + nation.getName(), "%PERMISSION%;" + permission.name());
            } else if (args[0].equalsIgnoreCase("unset")) {
                if (!Permission.hasForeignPermission(nation, permission, host)) {
                    Lang.X_DOESN_NOT_HAVE_PERMISSION.send(player, "%X%;" + nation.getName());
                    return false;
                }
                nation.removeForeignPermission(host, permission);
                Lang.REMOVED_PERMISSION_FROM_X.send(player, "%X%;" + nation.getName(), "%PERMISSION%;" + permission.name());
            }
        } else if (args[2].equalsIgnoreCase("player")) {
            UUID targetUUID = Bukkit.getPlayerUniqueId(args[3]);
            if (targetUUID == null) {
                Lang.PLAYER_DOES_NOT_EXIST.send(player);
                return false;
            }
            Profile target = instance.getProfile(targetUUID);
            if (args[0].equalsIgnoreCase("set")) {
                if (Permission.hasForeignPermission(target, permission, host)) {
                    Lang.X_ALREADY_HAS_PERMISSION.send(player, "%X%;§6" + target.getAsOfflinePlayer().getName());
                    return false;
                }
                target.addForeignPermission(host, permission);
                Lang.ADDED_PERMISSION_TO_X.send(player, "%X%;§6" + target.getAsOfflinePlayer().getName(), "%PERMISSION%;" + permission.name());
            } else if (args[0].equalsIgnoreCase("unset")) {
                if (!Permission.hasForeignPermission(target, permission, host)) {
                    Lang.X_DOESN_NOT_HAVE_PERMISSION.send(player, "%X%;§6" + target.getAsOfflinePlayer().getName());
                    return false;
                }
                target.removeForeignPermission(host, permission);
                Lang.REMOVED_PERMISSION_FROM_X.send(player, "%X%;§6" + target.getAsOfflinePlayer().getName(), "%PERMISSION%;" + permission.name());
            }
        }
        return false;
    }

}
