package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.entities.nation.ranks.Rank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class rankNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"rank", "permissions", "perms"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("create", 1);
        returner.put("remove", 1);
        returner.put("addpermission", 1);
        returner.put("removepermission", 1);
        returner.put("set", 1);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
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
        switch (args.length) {
            case 3:
                if (args[0].equalsIgnoreCase("create")) {
                    if (n.getRanks().get(args[1]) != null) {
                        Lang.RANK_ALREADY_EXISTS.send(player);
                        return false;
                    }
                    n.getRanks().put(args[1], new Rank(args[1], args[2].replace("&", "§"), new ArrayList<>()).toMap());
                    n.save();
                    Lang.RANK_CREATED.send(player, "%RANK%;" + args[1]);
                } else if (args[0].equalsIgnoreCase("set")) {
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

                }
                break;
        }
        return false;
    }

}
