package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class neutralNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"neutral", "unally"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> map = new HashMap<>();
        for (String s : NationManager.names.keySet())
            map.put(s, 1);
        return map;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (!checkIfPlayer(sender)) return false;
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        Nation nation = profile.getCurrentNation();
        Nation target = Nation.getByName(args[0]);
        if (target == null) {
            Lang.NATION_DOESNT_EXIST.send(player);
            return false;
        }
        if (Nation.getRelation(nation.getNationId(), target.getNationId()) != Nation.NationRelation.ALLY) {
            Lang.NOT_ALLIED.send(player);
            return false;
        }
        if (!Permission.hasNationPermission(profile, Permission.MANAGE_RELATIONS)) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        nation.getAllies().remove(target.getNationId());
        target.getAllies().remove(nation.getNationId());
        target.save();
        nation.save();
        Lang.NEUTRALED_NATION.send(player, "%NATION%;" + target.getName());
        return false;
    }

}
