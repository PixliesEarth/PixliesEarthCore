package eu.pixliesearth.core.custom.commands.subcommands.war;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.commands.WarCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.warsystem.War;
import eu.pixliesearth.warsystem.gulag.Gulag;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class JustifyWarGoalCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "justify";
    }

    @Override
    public Set<String> getCommandAliases() {
        Set<String> returner = new HashSet<>();
        returner.add("justifywargoal");
        returner.add("wargoal");
        return returner;
    }

    @Override
    public String getCommandUsage() {
        return "Justify a war-goal.";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new WarCommand.TabableNation()};
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (parameters.length != 1) {
            Lang.WRONG_USAGE_NATIONS.send(commandSender, "%USAGE%;/war justify <NATION>");
            return false;
        }
        Main instance = Main.getInstance();
        Player player = (Player) commandSender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(commandSender);
            return false;
        }
        if (!Permission.hasNationPermission(profile, Permission.JUSTIFY_WAR_GOAL)) {
            Lang.NO_PERMISSIONS.send(commandSender);
            return false;
        }
        Nation aggressor = profile.getCurrentNation();
        Nation defender = Nation.getByName(parameters[0]);
        if (defender == null) {
            Lang.NATION_DOESNT_EXIST.send(commandSender);
            return false;
        }
        War war = new War(aggressor.getNationId(), defender.getNationId());
        boolean justify = war.justifyWarGoal();
        if (!justify) {
            commandSender.sendMessage(Lang.WAR + "§cWar-goal justification failed. This could be either because you don't have enough PoliticalPower to justify a war-goal, or that you are already justifying a war-goal against this nation.");
            return false;
        } else {
            commandSender.sendMessage(Lang.WAR + "§7War-goal justification §asuccessful§7.");
        }
        return true;
    }

}
