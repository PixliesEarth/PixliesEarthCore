package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class mergeNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"merge"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
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
        if (!checkIfPlayer(sender)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        if (!profile.isLeader()) {
            Lang.YOU_HAVE_TO_BE_LEADER.send(player);
            return false;
        }
        Nation targetNation = Nation.getByName(args[0]);
        if (targetNation == null) {
            Lang.NATION_DOESNT_EXIST.send(player);
            return false;
        }

        return true;
    }

}
