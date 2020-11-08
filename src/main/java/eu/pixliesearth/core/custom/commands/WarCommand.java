package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.warsystem.War;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class WarCommand extends CustomCommand {

    @Override
    public String getName() {
        return "war";
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (args.length != 1) {
            Lang.WRONG_USAGE_NATIONS.send(sender, "%USAGE%;/war <NATION>");
            return false;
        }
        Main instance = Main.getInstance();
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(sender);
            return false;
        }
        if (!Permission.hasNationPermission(profile, Permission.JUSTIFY_WAR_GOAL)) {
            Lang.NO_PERMISSIONS.send(sender);
            return false;
        }
        Nation aggressor = profile.getCurrentNation();
        Nation defender = Nation.getByName(args[0]);
        if (defender == null) {
            Lang.NATION_DOESNT_EXIST.send(sender);
            return false;
        }
        War war = new War(aggressor.getNationId(), defender.getNationId(), Collections.singletonList(aggressor.getNationId()), Collections.singletonList(defender.getNationId()));
        boolean justify = war.justifyWarGoal();
        if (!justify) {
            sender.sendMessage(Lang.WAR + "§cWar-goal justification failed. This could be either because you don't have enough PoliticalPower to justify a war-goal, or that you are already justifying a war-goal against this nation.");
            return false;
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender commandsender, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();

        Map<Integer, Set<String>> argCompletions = new HashMap<>();

        argCompletions.put(0, NationManager.names.keySet());

        if (args.length > 1) {
            if (NationManager.names.get(args[0]) == null)
                return completions;
        }

        for (Map.Entry<Integer, Set<String>> entry : argCompletions.entrySet())
            if (args.length == entry.getKey() + 1)
                StringUtil.copyPartialMatches(args[entry.getKey()], entry.getValue(), completions);

        Collections.sort(completions);

        return completions;
    }

}
