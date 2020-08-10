package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

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
        map.put("BUILD", 2);
        map.put("INTERACT", 2);
        map.put("BANK_DEPOSIT", 2);
        map.put("BANK_WITHDRAW", 2);
        map.put("CLAIM", 2);
        map.put("UNCLAIM", 2);
        return map;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
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
        if (!Permission.hasNationPermission(profile, Permission.FOREIGN_PERMS)) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        if (!Permission.exists(args[1])) {
            Lang.WRONG_USAGE_NATIONS.send(player, "%USAGE%;/n fp set PERMISSION nation/player NATIONNAME/PLAYERNAME");
            return false;
        }
        if (args[0].equalsIgnoreCase("set")) {

        }
        return false;
    }

}
